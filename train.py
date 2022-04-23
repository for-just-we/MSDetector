from util.datasets import PhpDataset
from model import *

import numpy as np
from torch.utils.data import DataLoader
from sklearn import metrics

import torch

# those paths containing json datas generated by preprocess module
train_path = 'xxx/train/'
test_path = 'xxx/test/'

def label_smoothing(inputs, epsilon=0.1):
    '''Applies label smoothing. See 5.4 and https://arxiv.org/abs/1512.00567.
    inputs: 3d tensor. [N, T, V], where V is the number of vocabulary.
    epsilon: Smoothing rate.
    For example,
    ```
    import tensorflow as tf
    inputs = tf.convert_to_tensor([[[0, 0, 1],
       [0, 1, 0],
       [1, 0, 0]],
      [[1, 0, 0],
       [1, 0, 0],
       [0, 1, 0]]], tf.float32)
    outputs = label_smoothing(inputs)
    with tf.Session() as sess:
        print(sess.run([outputs]))
    >>
    [array([[[ 0.03333334,  0.03333334,  0.93333334],
        [ 0.03333334,  0.93333334,  0.03333334],
        [ 0.93333334,  0.03333334,  0.03333334]],
       [[ 0.93333334,  0.03333334,  0.03333334],
        [ 0.93333334,  0.03333334,  0.03333334],
        [ 0.03333334,  0.93333334,  0.03333334]]], dtype=float32)]
    ```
    '''
    # V = inputs.size().as_list()[-1]  # number of channels
    return ((1 - epsilon) * inputs) + (epsilon / 2)


def loss_fn(outputs, targets):
    return torch.nn.BCEWithLogitsLoss()(outputs, targets)

def train(epoch, training_loader, model):
    model.train()
    for i in range(epoch):
        for _, data in enumerate(training_loader, 0):
            targets = data['targets']
            targets = targets.view(-1, 1)
            targets = torch.LongTensor(targets)
            targets = torch.zeros(targets.shape[0], 2).scatter_(1, targets, 1)

            ids = data['ids'].to(conf.device, dtype=torch.long).cuda(non_blocking=True)
            mask = data['mask'].to(conf.device, dtype=torch.long).cuda(non_blocking=True)
            token_type_ids = data['token_type_ids'].to(conf.device, dtype=torch.long).cuda(non_blocking=True)
            targets = targets.to(conf.device, dtype=torch.float).cuda(non_blocking=True)
            targets = label_smoothing(targets)
            # print(targets)
            if hasattr(torch.cuda, 'empty_cache'):
                torch.cuda.empty_cache()

            outputs = model(ids, mask, token_type_ids)

            if hasattr(torch.cuda, 'empty_cache'):
                torch.cuda.empty_cache()
            optimizer.zero_grad()
            # print(outputs)
            loss = loss_fn(outputs, targets)

            pred_choice = outputs.max(1)[1]
            targets = targets.max(1)[1]
            # print(pred_choice, targets)
            correct = pred_choice.eq(targets).cpu().sum()
            print('[%d: %d/%d] train loss: %f accuracy: %f' % (
            i, _, num_batch, loss.item(), correct.item() / float(conf.batch_size)))


            loss.backward()
            optimizer.step()
            #scheduler.step()
            #torch.save(model.module.state_dict(), '%s/cls_model_%d.pth' % ('model', epoch))

def validation(testing_loader, model):
    model.eval()

    outs = []
    tars = []

    with torch.no_grad():
        for _, data in enumerate(testing_loader, 0):
            targets = data['targets']
            targets = targets.view(-1, 1)
            targets = torch.LongTensor(targets)
            targets = torch.zeros(targets.shape[0], 2).scatter_(1, targets, 1)

            ids = data['ids'].to(conf.device, dtype=torch.long)
            mask = data['mask'].to(conf.device, dtype=torch.long)
            token_type_ids = data['token_type_ids'].to(conf.device, dtype=torch.long)
            targets = targets.to(conf.device, dtype=torch.float)

            outputs = model(ids, mask, token_type_ids)
            outputs = outputs.max(1)[1]
            targets = targets.max(1)[1]

            outs.append(outputs.cpu())
            tars.append(targets.cpu())

    output = torch.cat(outs, 0)
    target = torch.cat(tars, 0).numpy()
    output = np.array(output) >= 0.5

    print(metrics.confusion_matrix(target, output))
    print(metrics.accuracy_score(target, output))
    print(metrics.f1_score(target, output))
    print(metrics.recall_score(target, output))
    print(metrics.precision_score(target, output))



if __name__ == '__main__':
    train_dataset = PhpDataset(train_path)
    test_dataset = PhpDataset(test_path)

    training_loader = DataLoader(dataset=train_dataset, batch_size=conf.batch_size, shuffle=True, num_workers=2, drop_last=False)
    testing_loader = DataLoader(dataset=test_dataset, batch_size=conf.batch_size, shuffle=True, num_workers=2, drop_last=False)

    model = BERTClassifier()
    # load pretrained model
    pretrain_pos_path = 'xxx/cls_model_params39.pkl'
    ckpt = torch.load(pretrain_pos_path)

    pretrain_params = {}
    for k,v in ckpt.items():
        if 'fc' not in k:
            pretrain_params[k] = v
    #
    transformer_state_dict = model.state_dict()
    transformer_state_dict.update(pretrain_params)
    model.load_state_dict(transformer_state_dict)
    #model.cuda()
    model = torch.nn.DataParallel(model.cuda())

    num_batch = len(train_dataset) / conf.batch_size
    optimizer = torch.optim.Adam(model.parameters(), lr=1e-05, betas=(0.9, 0.999))

    train(15, training_loader, model)
    validation(testing_loader, model)