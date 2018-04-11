from tkinter import Tk

from nltk import Tree


class ParseTreeVisualizer(object):
    def __init__(self, bracket: str):
        self.PARSE_TREE = Tree.fromstring('(' + bracket + ')')

    def start(self):
        self.PARSE_TREE.draw()
