import fileinput

FILE_TO_SEARCH = '../../input_fixer/ALT-Corpus-Tagged-Id-009.tre'
TEXT_TO_SEARCH = '(PRR yang)'
TEXT_TO_REPLACE = '(NUM yang)'

with fileinput.FileInput(FILE_TO_SEARCH, inplace=True, backup='.bak') as file:
    for line in file:
        print(line.replace(TEXT_TO_SEARCH, TEXT_TO_REPLACE), end='')