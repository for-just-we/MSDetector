# MSDetector
Code for TASE 2022 paper- MSDetector: A Static Php Webshell Detection System based on Deep-Learning


The developing environment

- PreProcessModule: 
  * jdk 11
  * maven 3.6.2
  * IDEA

- DeepLearningModule:
  * python 3.7
  * pytorch 1.7.1
  * scikit-learn 0.24.1
  * transformers 4.0.1
  * pycharm


There is another project which use CodeBert to detect webshell: [CodeBERT-based-webshell-detection](https://github.com/lyccol/CodeBERT-based-webshell-detection)

We provide some testcases in dictionary phpProcessor/src/test/files.

# Datasets

We first download datas from sources below:

|  Type   | Url  |
|  ----  | ----  |
| Webshell  | https://github.com/tanjiti/WebshellSample |
| Webshell  | https://github.com/JohnTroony/PHP-Webshell |
| Webshell  | https://github.com/learnstartup/4tweb |
| Normal  | https://github.com/johnshen/PHPcms |
| Normal  | https://github.com/WordPress |
| Normal  | https://github.com/phpmyadmin/phpmyadmin |
| Normal  | https://github.com/smarty-php/smarty |
| Normal  | https://github.com/yiisoft/yii |

Then we perform data clean and data augment manually


Unfortunately our dataset has been used in another project with another institute(our first party, also the charger of that project), they require us not to open-source datasets.


# MSDetector

## PreProcessModule

The PreProcessModule is implemented in Java, the main class is `Main`, and the class to parse PHP file is `generator/ScriptParser`. We utilized Java Antlr API, at first we intend to implement the module in python, but it seems that there are some bugs in python-antlr API for PHP. 

The PreProcess run in following steps:

- Simplify and compress original ANTLR AST for PHP file

- Traverse AST to generate token sequence, string sequence and node tag sequence.

The output of PreProcessModule for a given PHP file is a json string, which is like:

```json
{
    "tokenSequence": [xxx, xxx, ...],
    "stringSequence": [xxx,...],
    "tags": [xxx, xxx, ...]
}
```

The json string could be easily parsed by python json api which is useful for next step.

### Simplify AST

The Antlr AST is complex and large, we mainly consider 

- Symbolizing VarName, FunctionName and ClassName in PHP file

- Extracting string literals from PHP file

So, we do not need to parse the complex Antlr AST, the first step of PreProcessModule is to simplify AST, which contains two steps:

- We first convert the Antlr AST to our self-defined class `SimpleNode`, which is done by [ConversionUtil.ConversionUtil](https://github.com/for-just-we/MSDetector/blob/master/phpProcessor/src/main/java/util/ConversionUtil.java) 

- The next step is to compress AST, the two images below gives an example, the purpose of compressing is to eliminate nodes in AST which contain only one child, after simplifing, it would be easier for us to traverse.

![origin AST](example.png)

![simplified AST](examplee.png)


### Traverse AST

Traversing AST is done by [ScriptParser.parseSimpleTree](https://github.com/for-just-we/MSDetector/blob/master/phpProcessor/src/main/java/generator/ScriptParser.java#L76), we use IDEA Antlr plugin to visualise origin Antlr AST to debug our programs.

During traversing AST, we focus on the nodes containing variable names, function names and class names and constants(`int`, `float`, `string`, `bool`).

- variable name: this is the most easy case, if the AST node type is `VarName`, then this is a variable name, in our program, if a var name not in global variable table(`$_GET`, `$_POST`, etc, `$this`), then the variable name would be symbolized.

- function names: it is complex than variable names, the function name appears in following cases:
   * function declaration(`FunctionDecl`) and class inner class declaration(`classStatement`).
   * function calls: direct function call(`funcCall`), class static inner function call, object member function call, and call by variable name.

- class names: class declaration(we treat class, trait and interface as class) and new expression, class static inner function call.

Those rules may not be complete but sufficient to our task. It is worth noting that we generate token sequence, string literals and tag sequence at the same time, regardless of pretrain stage and train stage.

### String parser

when encounting string literals, we implement a string parser to determine the type of the string literal, which is [StringParser](https://github.com/for-just-we/MSDetector/blob/master/phpProcessor/src/main/java/util/StringParser.java). The rationale is key words matching, which could introduce errors some times but in most cases correct, we utilize entropy algorithm to determine whether that is a encrypted string, but it works well on long encrypted string but is inefficient in short encrypted string, we still define some keywords to detect encrypted string.

It would be more efficient to implement string parser by machine learning, but the key issue is that it consume many time to label a string dataset.


## DeepLearningModule

Our method utilize [CodeBert](https://github.com/microsoft/CodeBERT) model, we simply add a linear lay on top of CodeBert to classify vectorized PHP file.

The model are defined in [model.py](https://github.com/for-just-we/MSDetector/blob/master/model.py).

- When pretraining, we use `BERT_POS` class, and save all parameters to a pkl file after pretraining.

- When training and detecting, we use `BERTClassifier` class, in training stage, we first load pretrained parameters and load that belong to CodeBert model to `BERTClassifier` model.


During pretraining(and training, detecting stages), a token in token sequence may be tokenized into several subtokens, so in pretraining stage, the corresponding tag should be expanded too. We use BIO mode, for example:

- if token `t1`(tag `n1`) is tokenized to `[t11, t12, t13]`, then its tag would be expanded to `B-n1, B-n2, B-n3`.

- if token `t1` remains unchanged after tokenization, its tag would still be expanded to `B-n1`.
