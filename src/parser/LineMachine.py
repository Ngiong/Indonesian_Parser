class LineMachine(object):
    def __init__(self, filepath):
        self.file = open(filepath, 'r')

    def next_line(self):
        return self.file.readline()

    def terminate(self):
        self.file.close()
