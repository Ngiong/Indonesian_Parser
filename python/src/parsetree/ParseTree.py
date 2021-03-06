from parsetree.WordToken import WordToken


class ParseTree(object):
    def __init__(self, tree_brackets):
        self.BRACKETS = tree_brackets
        # for indexing words (leaf nodes), required for evaluation
        self.BEGIN_NUM = -1
        self.END_NUM = -1

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

    def getHeight(self):
        if self.IS_LEAF: return 0
        else:
            maxHeight = -1
            for child in self.CHILDREN:
                maxHeight = max(maxHeight, child.getHeight())
            return maxHeight + 1;

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

    def dfs(self, process_node):
        process_node(self)
        if not self.IS_LEAF:
            for child in self.CHILDREN:
                child.dfs(process_node)

    def printPretty(self, startIndent: int, incrementIndent: int):
        tmpIndent = ' ' * startIndent
        if self.IS_LEAF:
            print(tmpIndent + '(' + self.NODE_TAG + " " + self.WORD + ")")
        else:
          print(' '*startIndent + '(' + self.NODE_TAG)
          for child in self.CHILDREN: child.printPretty(startIndent+incrementIndent, incrementIndent)
          print(tmpIndent + ')')

    def __getClosingBracketIdx(self, i: int):
        j = i + 1
        bracket_cnt = 1; found = False
        while not found and j < len(self.BRACKETS):
            if self.BRACKETS[j] == '(': bracket_cnt += 1
            elif self.BRACKETS[j] == ')': bracket_cnt -= 1

            if bracket_cnt == 0: found = True
            else: j = j + 1

        return j if found else -1

    def __str__(self):
        result = self.NODE_TAG + " ";
        if (self.IS_LEAF):
            result += self.WORD
        else:
            for child in self.CHILDREN:
                result += "(" + str(child) + ")"
        return result

    __repr__ = __str__