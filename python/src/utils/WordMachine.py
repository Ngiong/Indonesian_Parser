from utils.CharMachine import CharMachine


class WordMachine(object):
    BLANK_SPACE = ' '

    def __init__(self, str):
        self.char_machine = CharMachine(str)

    def has_remaining(self):
        return self.char_machine.has_remaining()

    def next_word(self):
        word = ''
        cm = self.char_machine
        cm.ignore_spaces()

        while cm.has_remaining() and cm.peek() != ')':
            c = cm.next_char()
            word = word + c
            if c == '(':
                word = ''
                cm.ignore_spaces()

        if cm.has_remaining() and cm.peek() == ')':
            cm.next_char()
            cm.ignore_spaces()

        if cm.has_remaining() and len(word) == 0:
            return self.next_word()
        else:
            return word
