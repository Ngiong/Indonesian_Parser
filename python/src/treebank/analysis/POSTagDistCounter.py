from utils.LineMachine import LineMachine
from utils.WordMachine import WordMachine


class POSTagDistCounter(object):
    def __init__(self, filepath):
        self.filepath = filepath
        self.line_machine = LineMachine(filepath)
        self.distribution = dict()

    def count_distribution(self):
        infile = open(self.filepath, 'r')
        pos_tags = set()
        n_lines = 0
        for line in infile:
            lm = WordMachine(line)
            self.__process_line(lm)

            n_lines = n_lines + 1
            # if n_lines > 5: break

        return self.distribution

    def __process_line(self, leaf_machine: WordMachine):
        while leaf_machine.has_remaining():
            leaf_node = leaf_machine.next_word()
            if len(leaf_node) <= 1: continue

            leaf_node = leaf_node.split()
            if len(leaf_node) < 2:
                print(leaf_node)
                continue

            vocabulary = leaf_node[1]
            pos_tag = leaf_node[0]
            if vocabulary in self.distribution:
                if pos_tag in self.distribution[vocabulary]:
                    self.distribution[vocabulary][pos_tag] += 1
                else:
                    self.distribution[vocabulary][pos_tag] = 1

            else : # OOV
                self.distribution[vocabulary] = dict()
                self.distribution[vocabulary][pos_tag] = 1

