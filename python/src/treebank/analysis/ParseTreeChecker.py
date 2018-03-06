from src.parser.LineMachine import LineMachine
from src.parser.ParseTree import ParseTree

import const

class ParseTreeChecker(object):
    def __init__(self, filepath, accepted_filename, rejected_filename):
        self.filepath = filepath
        self.line_machine = LineMachine(filepath)
        self.accepted = open(accepted_filename, 'w')
        self.rejected = open(rejected_filename, 'w')
        self.count_rejected = 0

    def execute_checker(self):
        infile = open(self.filepath, 'r')

        n_lines = 0
        for line in infile:
            self.__process_line(line)
            n_lines = n_lines + 1
            print('[', n_lines, ']', sep='')

    def __process_line(self, brackets):
        if brackets[0] == '(':
            brackets = brackets[1:len(brackets)-1]

        pt = ParseTree(brackets)
        try:
            pt.makeTree()
            check_result = ParseTreeChecker.__checkTree(pt)

            if check_result: print(brackets, file=self.accepted)
            else:
                self.count_rejected += 1
                print('[SAD] Found REJECTED:', self.count_rejected)
                print(brackets, file=self.rejected)

        except ValueError as err:
            self.count_rejected += 1
            print('[OH NO] Found EXCEPTION:', self.count_rejected)
            print(brackets, file=self.rejected)
            print('REASON: ', err, file=self.rejected)

    @staticmethod
    def __checkTree(self, parseTree):
        if parseTree.IS_LEAF:
            return parseTree.NODE_TAG in const.POS_TAGS
        else:
            result = parseTree.NODE_TAG in const.CONSTITUENTS
            if result:
                for child in parseTree.CHILDREN:
                    check_child = child.checkTree()
                    if not check_child:
                        raise ValueError(child.BRACKETS)
                        return False
                return True
            else:
                return False