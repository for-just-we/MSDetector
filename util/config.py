import torch

class Config:
    def __init__(self):
        self.seq_len = 512
        self.batch_size = 64
        self.num_batch = 32
        self.lr = 0.01
        self.weight_decay = 1e-4
        self.device = "cuda" if torch.cuda.is_available() else "cpu"


conf = Config()