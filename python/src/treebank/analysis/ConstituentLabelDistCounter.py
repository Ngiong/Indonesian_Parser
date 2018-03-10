from utils.LineMachine import LineMachine


class ConstituentLabelDistCounter(object):
    def __init__(self, filepath):
        self.filepath = filepath
        self.line_machine = LineMachine(filepath)
        self.distribution = dict()

    def count_distribution(self):
        infile = open(self.filepath, 'r')
        n_lines = 0
        for line in infile:
            self.__process_line(line)

            n_lines = n_lines + 1
            if n_lines > 5: break

    def __process_line(self, brackets):
        if brackets[0] == '(':
            brackets = brackets[1:len(brackets)-1]
        pt = ParseTree(brackets)
        pt.makeTree()
        pt.printTree()


