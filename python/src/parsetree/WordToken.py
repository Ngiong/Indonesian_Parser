class WordToken(object):
    def __init__(self, word, tag):
        self.WORD = word
        self.TAG = tag

    def __str__(self):
        return self.WORD + "#" + self.TAG

    def __eq__(self, other):
        return self.WORD == other.WORD and self.TAG == other.TAG

    def __hash__(self):
        return hash((self.WORD, self.TAG))

    __repr__ = __str__
