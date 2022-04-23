from seqeval.metrics import accuracy_score

from model import *
from util.datasets import POS_Lable_datasets
from torch.utils.data import DataLoader

import json
# for pretrain task(AST Node tagging)


label_dict_file = 'xxx/label_dict.json' # map node tag to index
labels_file = 'xxx/labels.json' # containing all node tags
# dataset path: containing coverted json datas, describe in the comment of POS_Lable_datasets class
train_path = 'xxxx/train/'
test_path = 'xxxx/test'


label_dict = json.load(open(label_dict_file, 'r', encoding='utf-8'))
labels = json.load(open(labels_file, 'r', encoding='utf-8'))

train_dataset = POS_Lable_datasets(train_path, total_len=conf.seq_len, label_dict=label_dict)
test_dataset = POS_Lable_datasets(test_path, total_len=conf.seq_len, label_dict=label_dict)

training_loader = DataLoader(dataset=train_dataset, batch_size=conf.seq_len, shuffle=True, num_workers=2, drop_last=False)
testing_loader = DataLoader(dataset=test_dataset, batch_size=conf.seq_len, shuffle=True, num_workers=2, drop_last=False)

device = 'cuda'

results = []

def train(epoch, training_loader ,model, optimizer, labels):
    for epc in range(epoch):
        model.train()
        for k, data in enumerate(training_loader, 0):
            model.zero_grad()
            targets = data['tags']
            batchs = targets.shape[0]

            ids = data['ids'].to(device, dtype=torch.long).cuda(non_blocking=True)
            mask = data['mask'].to(device, dtype=torch.long).cuda(non_blocking=True)
            token_type_ids = data['token_type_ids'].to(device, dtype=torch.long).cuda(non_blocking=True)
            targets = targets.to(device, dtype=torch.long).cuda(non_blocking=True)


            if hasattr(torch.cuda, 'empty_cache'):
                torch.cuda.empty_cache()

            emissions = model(ids, mask, token_type_ids)
            if hasattr(torch.cuda, 'empty_cache'):
                torch.cuda.empty_cache()
            preds= torch.argmax(emissions, dim=2).tolist()
            pred_list = []
            tag_list = []

            for pred in preds:
                pred_list.append([labels[i] for i in pred])

            for target in targets.tolist():
                tag_list.append([labels[i] for i in target])

            acc = accuracy_score(preds, targets.tolist())

            emissions = emissions.reshape(batchs * conf.seq_len, -1)
            targets = targets.reshape(batchs * conf.seq_len)
            loss = F.cross_entropy(emissions, targets)

            print('epoch:{} sets:{}/{}, loss:{}, acc:{}'.format(epc, k, len(training_loader), loss.item(), acc))

            loss.backward()
            optimizer.step()
        validation(testing_loader, model, labels)
        torch.save(model.state_dict(), 'model/BertPos/cls_model_params{}.pkl'.format(epc))


def validation(testing_loader, model, labels):
    model.eval()

    outs = []
    tars = []

    with torch.no_grad():
        for _, data in enumerate(testing_loader, 0):
            targets = data['tags']

            ids = data['ids'].to(device, dtype=torch.long).cuda(non_blocking=True)
            mask = data['mask'].to(device, dtype=torch.long).cuda(non_blocking=True)
            token_type_ids = data['token_type_ids'].to(device, dtype=torch.long).cuda(non_blocking=True)
            targets = targets.to(device, dtype=torch.long).cuda(non_blocking=True)

            emissions = model(ids, mask, token_type_ids)
            preds= torch.argmax(emissions, dim=2)

            outs.append(preds.cpu())
            tars.append(targets.cpu())

    output = torch.cat(outs, 0)
    targets = torch.cat(tars, 0)

    pred_list = []
    tag_list = []

    for pred in output:
        pred_list.append([labels[i] for i in pred])

    for target in targets:
        tag_list.append([labels[i] for i in target])

    acc = accuracy_score(output.tolist(), targets.tolist())
    print('test acc:{}'.format(acc))
    results.append(acc)



if __name__ == '__main__':
    tagset_size = len(labels)
    model = BERT_POS(tagset_size)
    model.load_state_dict(torch.load('model/BertPos/cls_model_params14.pkl'))

    model = model.cuda()
    optimizer = torch.optim.SGD(model.parameters(), lr=conf.lr, weight_decay=conf.weight_decay)
    train(conf.num_batch, training_loader, model, optimizer, labels)
    for result in results:
        print(result)