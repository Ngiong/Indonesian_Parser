class CharMachine(object):
    BLANK_SPACE = ' '

    def __init__(self, str):
        self.str = str
        self.idx = 0

    def has_remaining(self):
        return self.idx < len(self.str)

    def next_char(self):
        if self.idx == len(self.str):
            raise IndexError('Unable to get next char.')
        c = self.str[self.idx]
        self.idx = self.idx + 1
        return c

    def peek(self):
        return self.str[self.idx]

    def ignore_spaces(self):
        while self.has_remaining() and self.str[self.idx] == CharMachine.BLANK_SPACE:
            self.idx = self.idx + 1
