from parsetree.WordToken import WordToken


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

    def __str__(self):
        result = self.NODE_TAG + " ";
        if (self.IS_LEAF):
            result += self.WORD
        else:
            for child in self.CHILDREN:
                result += "(" + str(child) + ")"
        return result

    __repr__ = __str__

    def __getClosingBracketIdx(self, i: int):
        j = i + 1
        bracket_cnt = 1; found = False
        while not found and j < len(self.BRACKETS):
            if self.BRACKETS[j] == '(': bracket_cnt += 1
            elif self.BRACKETS[j] == ')': bracket_cnt -= 1

            if bracket_cnt == 0: found = True
            else: j = j + 1

        return j if found else -1

    def getWordQueue(self):
        if (self.IS_LEAF):
            result = list()
            result.append(WordToken(self.WORD, self.NODE_TAG))
            return result
        else:
            result = list()
            for child in self.CHILDREN:
                tmp = child.getWordQueue()
                result.extend(tmp)
            return result