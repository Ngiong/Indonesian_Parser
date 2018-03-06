import os


class FileCombiner(object):
    def __init__(self, input_path, output_path):
        self.INPUT_PATH = input_path
        self.OUTPUT_PATH = output_path
        self.FILE_NAMES = list()

    def listFiles(self):
        file_names = os.listdir(self.INPUT_PATH)
        print('====== COMBINING =====')
        for file_name in file_names:
            self.FILE_NAMES.append(file_name)
            print(file_name)

    def getDocument(self, filename):
        file_open = open(self.INPUT_PATH + filename, 'r')
        result = file_open.read()
        return result

    def combine(self, output_path):
        file_output = open(self.OUTPUT_PATH + output_path, 'a')
        for file_name in self.FILE_NAMES:
            file_output.write(self.getDocument(file_name) + '\n')
