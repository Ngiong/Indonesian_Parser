POS_TAGS = [
    'NNP', 'VBI', 'VBT', 'NUM', 'PPO', 'NNO', 'SYM', 'CCN', 'PRN', 'ADV', 'ADJ', 'NEG', 'ADK', 'PRR', 'VBL', 'VBP', 'ART',
    'PRK', 'KUA', '$$$', 'UNS', 'LBR', 'RBR', 'VBE', 'PRI', 'PAR', 'INT', 'CSN'
]

PHRASE_LABELS = [
    'AdjP', 'AdvP', 'NP', 'PP', 'VP', 'CP', 'RPN', 'S', 'SQ', 'SBAR'
]

class ParseTree(object):
    def __init__(self, tree_brackets):
        self.BRACKETS = tree_brackets

    def makeTree(self):
        try:
            tmp = self.BRACKETS.split()

            if '(' not in self.BRACKETS: # MAKE LEAF
                self.IS_LEAF = True
                self.NODE_TAG = tmp[0]
                self.WORD = tmp[1]
                self.CHILDREN = None

            else: # MAKE TREE
                self.IS_LEAF = False
                self.NODE_TAG = tmp[0]
                # print('#', self.BRACKETS, self.NODE_TAG)

                children = list(); i = 0
                while i < len(self.BRACKETS):
                    if self.BRACKETS[i] == '(':
                        j = self.__getClosingBracketIdx(i)
                        tmp = ParseTree(self.BRACKETS[i+1:j])
                        tmp.makeTree(); children.append(tmp)
                        i = j
                    i = i + 1
                self.CHILDREN = children
        except:
            raise ValueError(self.BRACKETS)

    def printTree(self):
        print(' (', end='')
        print(self.NODE_TAG, end='')
        if self.IS_LEAF:
            print('', self.WORD, end='')
        if self.CHILDREN is not None:
            for child in self.CHILDREN:
                child.printTree()
        print(')', end='')

    def checkTree(self):
        if self.IS_LEAF:
            return self.NODE_TAG in POS_TAGS
        else:
            result = self.NODE_TAG in PHRASE_LABELS
            if result:
                for child in self.CHILDREN:
                    check_child = child.checkTree()
                    if not check_child:
                        raise ValueError(child.BRACKETS)
                        return False
                return True
            else:
                return False

    def __getClosingBracketIdx(self, i: int):
        j = i + 1
        bracket_cnt = 1; found = False
        while not found and j < len(self.BRACKETS):
            if self.BRACKETS[j] == '(': bracket_cnt += 1
            elif self.BRACKETS[j] == ')': bracket_cnt -= 1

            if bracket_cnt == 0: found = True
            else: j = j + 1

        return j if found else -1