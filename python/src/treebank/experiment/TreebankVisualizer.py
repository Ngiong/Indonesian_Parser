from tkinter import Tk, Text, INSERT, Label
from nltk import Tree
from nltk.draw.util import CanvasFrame
from nltk.draw import TreeWidget

from treebank.experiment.PARSEVALEvaluator import PARSEVALEvaluator
from parsetree.ParseTree import ParseTree


class TreebankVisualizer(object):
    def __init__(self, gold_treebank: str, parsed_treebank: str):
        with open(gold_treebank) as gold:
            self.GOLD = gold.readlines()
        with open(parsed_treebank) as parsed:
            self.PARSED = parsed.readlines()

        self.GOLD = [x.strip() for x in self.GOLD]
        self.PARSED = [x.strip() for x in self.PARSED]
        self.IDX = 0

        self.MASTER = Tk()
        self.MASTER.bind('<Left>', self.prev)
        self.MASTER.bind('<Right>', self.next)
        self.CANVAS = CanvasFrame(self.MASTER, width=1200, height= 600)
        self.SCORE = Label(self.MASTER, text='')
        self.GOLD_tc = None

    def next(self, event):
        gold_tree = self.GOLD[self.IDX]
        parsed_tree = self.PARSED[self.IDX]

        self.__visualize(gold_tree, parsed_tree)
        self.__updateScoreText(gold_tree, parsed_tree, self.IDX)

        self.IDX += 1


    def prev(self, event):
        self.IDX += -1
        gold_tree = self.GOLD[self.IDX-1]
        parsed_tree = self.PARSED[self.IDX-1]

        self.__visualize(gold_tree, parsed_tree)
        self.__updateScoreText(gold_tree, parsed_tree, self.IDX-1)

    def __visualize(self, gold_tree: str, parsed_tree: str):
        if self.GOLD_tc != None:
            self.CANVAS.destroy_widget(self.GOLD_tc)
            self.CANVAS.destroy_widget(self.PARSED_tc)

        GOLD = Tree.fromstring('(' + gold_tree + ')')
        PARSED = Tree.fromstring('(' + parsed_tree + ')')

        self.GOLD_tc = TreeWidget(self.CANVAS.canvas(), GOLD)
        self.PARSED_tc = TreeWidget(self.CANVAS.canvas(), PARSED)

        self.CANVAS.add_widget(self.GOLD_tc, 0, 0)
        self.CANVAS.add_widget(self.PARSED_tc, 0, self.GOLD_tc.height() + 10)
        self.CANVAS.pack(expand=True)

    def __updateScoreText(self, gold_tree: str, parsed_tree: str, idx: int):
        GOLD = ParseTree(gold_tree); GOLD.makeTree()
        PARSED = ParseTree(parsed_tree); PARSED.makeTree()

        parseval = PARSEVALEvaluator()
        result = parseval.evaluate(PARSED, GOLD)
        self.SCORE.configure(text = '[' + str(idx) + '] ' + str(result))
        self.SCORE.pack(fill='x')

    def start(self):
        gold_tree = self.GOLD[self.IDX]
        parsed_tree = self.PARSED[self.IDX]

        self.__visualize(gold_tree, parsed_tree)
        self.__updateScoreText(gold_tree, parsed_tree, self.IDX)

        self.IDX += 1

        self.MASTER.mainloop()



# t = Tree.fromstring('(A (B (D D))(C (E E)(F F)(G G)))')
# t.draw()
#
# cf = CanvasFrame()
# tc = TreeWidget(cf.canvas(),t)
# cf.add_widget(tc,10,10) # (10,10) offsets
# # cf.print_to_file('tree.ps')
# cf.destroy()