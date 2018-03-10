from parsetree.ParseTree import ParseTree

class SplitTestInitializer(object):
    def __init__(self, treebank_path: str):
        self.treebank = open(treebank_path, 'r')
        self.vocabularies = set()

    def separate(self, output_train: str, output_test: str, test_instances = -1):
        train = open(output_train, 'w')
        test = open(output_test, 'w')

        count_test = 0
        for line in self.treebank:
            pt = ParseTree(line); pt.makeTree()
            wordTokens = pt.getWordQueue()
            if self.hasOOV(wordTokens):
                print(line, file=train, end='')
                for token in wordTokens:
                    self.vocabularies.add(token)
            else:
                print(line, file=test, end='')
                count_test += 1
                if test_instances == count_test: break

    def hasOOV(self, wordTokens: list) -> bool:
        for token in wordTokens:
            if token not in self.vocabularies:
                return True

        return False
