from src.parser.LineMachine import LineMachine
from src.parser.ParseTree import ParseTree

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
            if n_lines == 506:
                print('CARE')

    def __process_line(self, brackets):
        if brackets[0] == '(':
            brackets = brackets[1:len(brackets)-1]

        pt = ParseTree(brackets)
        try:
            pt.makeTree()
            check_result = pt.checkTree()

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


ptc = ParseTreeChecker(
    '../../output_combiner/new_combined_2.tre',
    '../../output_checker/accepted_2.tre',
    '../../output_checker/rejected_with_reason_2.tre'
)
ptc.execute_checker()