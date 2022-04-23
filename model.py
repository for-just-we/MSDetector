import torch
import torch.nn as nn
import torch.nn.functional as F
from transformers import RobertaModel

from util.config import conf


class BERTClassifier(torch.nn.Module):
    def __init__(self):
        super(BERTClassifier, self).__init__()
        self.transformer = RobertaModel.from_pretrained("microsoft/codebert-base")
        self.drop = torch.nn.Dropout(0.1)
        self.fc = torch.nn.Linear(768, 1)

    def forward(self, ids, mask, token_type_ids):
        o = self.transformer(ids, attention_mask=mask, token_type_ids=token_type_ids)
        output_2 = self.drop(o['pooler_output'])
        output_2 = output_2.view(-1, 768)
        output = torch.sigmoid(self.fc(output_2))
        return output


##POS
class BERT_POS(torch.nn.Module):
    def __init__(self, tagset_size):
        super(BERT_POS, self).__init__()
        self.transformer = RobertaModel.from_pretrained("microsoft/codebert-base").cuda()
        self.dropout = nn.Dropout(0.1)
        self.fc = nn.Linear(768, tagset_size)

    def forward(self, ids, mask, token_type_ids):
        outputs = self.transformer(input_ids= ids, token_type_ids=token_type_ids, attention_mask=mask)
        last_encoder_layer = self.dropout(outputs['last_hidden_state']) # [batch, seq, 768]
        emissions = F.softmax(self.fc(last_encoder_layer), dim=2) # [batch, seq, tag_size]
        return emissions
