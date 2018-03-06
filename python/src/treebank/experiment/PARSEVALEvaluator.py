from parsetree.ParseTree import ParseTree

class PARSEVALResult(object):
    def __init__(self, prec: float, recall: float):
        self.precision = prec
        self.recall = recall
        self.f1_score = 2.0 * (prec * recall) / (prec + recall)

    def __str__(self):
        p = '{0:.2f}'.format(self.precision)
        r = '{0:.2f}'.format(self.recall)
        f1 = '{0:.2f}'.format(self.f1_score)
        return "(P: " + p + ", R: " + r  + ", F1: " + f1 + ")"

    __repr__ = __str__

class PARSEVALConstituentTokens(object):
    def __init__(self, constituent: str, begin: int, end: int):
        self.constituent = constituent
        self.begin = begin
        self.end = end

    def __str__(self):
        return self.constituent + "(" + str(self.begin) + "," + str(self.end) + ")"

    __repr__ = __str__

    def __hash__(self):
        return hash((self.constituent, self.begin, self.end))

    def __eq__(self, other):
        return self.constituent == other.constituent and self.begin == other.begin and self.end == other.end

class PARSEVALEvaluator(object):
    def evaluate(self, parsed: ParseTree, gold: ParseTree) -> PARSEVALResult:
        parsed_constituents = self.getConstituents(parsed)
        gold_constituents = self.getConstituents(gold)

        correct_constituents = parsed_constituents.intersection(gold_constituents)
        cnt_correct = len(correct_constituents)

        precision = cnt_correct / len(parsed_constituents)
        recall = cnt_correct / len(gold_constituents)

        return PARSEVALResult(precision, recall)

    def getConstituents(self, pt: ParseTree) -> set:
        result = set()

        # Numbering words (leaf nodes)
        def numberingLeaves(pt: ParseTree):
            if pt.IS_LEAF:
                pt.BEGIN_NUM = numberingLeaves.leaf_num
                pt.END_NUM = numberingLeaves.leaf_num
                numberingLeaves.leaf_num += 1
        numberingLeaves.leaf_num = 0
        pt.dfs(numberingLeaves)

        # Numbering constituents (non-leaf nodes)
        def getBegin(pt: ParseTree) -> int:
            pt.BEGIN_NUM = getBegin(pt.CHILDREN[0]) if pt.BEGIN_NUM == -1 else pt.BEGIN_NUM
            return pt.BEGIN_NUM
        def getEnd(pt: ParseTree) -> int:
            pt.END_NUM = getEnd(pt.CHILDREN[len(pt.CHILDREN)-1]) if pt.END_NUM == -1 else pt.END_NUM
            return pt.END_NUM
        pt.dfs(getBegin); pt.dfs(getEnd)

        # Extract constituents
        def extractConstituent(pt: ParseTree):
            if not pt.IS_LEAF:
                result.add(PARSEVALConstituentTokens(pt.NODE_TAG, pt.BEGIN_NUM, pt.END_NUM))
        pt.dfs(extractConstituent)

        return result
