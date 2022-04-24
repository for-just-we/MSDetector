from jsonlines import Reader
import re

data_file = 'xxx/php_test_0.jsonl'
des_file = 'xxx/0/CodeSearch_{}.php'

# final public |protected |private  static
match_str = '^(final )?(public |protected |private )?(static )?'


def code_pre(outstring):
    m = re.compile(match_str, re.S)
    outtmp = re.sub(m, '', outstring)

    outtmp = '<?php\n' + outtmp + '\n?>'
    return outtmp

if __name__ == '__main__':
    fp = open(data_file, 'r', encoding='utf-8')
    reader = Reader(fp)

    for i, item in enumerate(reader):
        phpcode = code_pre(item['original_string'])
        fp = open(des_file.format(i), 'w', encoding='utf-8')
        fp.write(phpcode)
        fp.close()
        print('done:{}'.format(i))
