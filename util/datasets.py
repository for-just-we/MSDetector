import torch
import os
import json

from util.config import conf

from torch.utils.data import Dataset
from transformers import RobertaTokenizer


# dataset used in training mode (webshell detection task)
# when training a model, we first parse all PHP files into json files(corresponding sequence representation)
# the json is
#   "tokenSequence" -> tokenSequence of the PHP file
#   "stringSequence" -> stringLiterals of the PHP file
#   "tags" -> node tags of the PHP file
class PhpDataset(Dataset):
    def __init__(self, path):
        self.black_file_list = os.listdir(path + 'webshell/')
        self.white_file_list = os.listdir(path + 'normal/')
        self.black = [path + 'webshell/' + i for i in self.black_file_list]
        self.white = [path + 'normal/' + i for i in self.white_file_list]

        self.tokenizer = RobertaTokenizer.from_pretrained("microsoft/codebert-base")
        self.df = self.black + self.white

    def __getitem__(self, item):
        try:
            rf = open(self.df[item], 'r', encoding='utf-8', errors='ignore')
            raw_data = json.load(rf)
        finally:
            rf.close()

        data = raw_data["tokenSequence"] + ["</s>"] + raw_data["stringSequence"]
        inputs = self.tokenizer.encode_plus(
            data,
            None,
            add_special_tokens=True,
            max_length=conf.seq_len,
            padding='max_length',
            return_token_type_ids=True,
            truncation=True,
        )

        ids = inputs['input_ids']
        mask = inputs['attention_mask']
        token_type_ids = inputs["token_type_ids"]


        return {
            'ids': torch.tensor(ids, dtype=torch.long),
            'mask': torch.tensor(mask, dtype=torch.long),
            'token_type_ids': torch.tensor(token_type_ids, dtype=torch.long),
            'targets': torch.tensor(1 if self.df[item].split('/')[-2] == 'webshell' else 0)
        }


    def __len__(self):
        return len(self.df)


# BIO mode
def prepare_sequence(seq, to_idx, max_seq_num):
    if (len(seq) >= max_seq_num - 2):
        seq = ['START'] + seq[: max_seq_num - 2] + ['END']
    else:
        seq = ['START'] + seq + ['END'] + ['O'] * (max_seq_num - len(seq) - 2)
    idx = [to_idx[w] for w in seq]
    return torch.tensor(idx, dtype=torch.long)

# dataset used when pretrain a model in Node tagging task, to save time we convert labelled json to another json file
# for example, if a raw json is
#  {
#    "tokenSequence": T1, T2, T3, T4, ....
#    "stringSequence":  S1, S2
#    "nodeTags": N1, N2, N3, N4, ...
#  }
# The RobertTokenizer may tokenize a token Ti into several subtoken Ti1,..., Tij, so we need to convert tag sequences,
# and save converted json data into a new json file to save time when pretraining( avoid duplication of efforts)
# converted json file is(example):  we remove key stringSequence because we don't need it in pretrain task
# {
#    "tokenSequence": T11, T12, T2, T31, T32, T33, T4, ...
#    "nodeTags: B-N1, I-N1, B-N2, B-N3, I-N3, I-N3, B-N4, ...
# }
class POS_Lable_datasets(Dataset):
    def __init__(self, path, total_len, label_dict):
        self.file_list = os.listdir(path)
        self.df = [path + i for i in self.file_list]
        self.tokenizer = RobertaTokenizer.from_pretrained("microsoft/codebert-base")
        self.total_len = total_len
        self.label_dict = label_dict # label_dict is to map node tag to a index,

    def __len__(self):
        return len(self.df)

    def __getitem__(self, item):
        fp = open(self.df[item], 'r', encoding='utf-8', errors='ignore')
        raw_datas = json.load(fp)
        tokens = raw_datas["tokenSequence"]
        inputs_token = self.tokenizer.encode_plus(
            tokens,
            None,
            add_special_tokens=True,
            max_length=self.total_len,
            padding='max_length',
            return_token_type_ids=True,
            truncation=True,
        )

        ids = inputs_token['input_ids']
        mask = inputs_token['attention_mask']
        token_type_ids = inputs_token["token_type_ids"]

        return {
            'ids': torch.tensor(ids, dtype=torch.long),
            'mask': torch.tensor(mask, dtype=torch.long),
            'token_type_ids': torch.tensor(token_type_ids, dtype=torch.long),
            'tags': prepare_sequence(seq=raw_datas["nodeTags"], to_idx=self.label_dict, max_seq_num=self.total_len)
        }
