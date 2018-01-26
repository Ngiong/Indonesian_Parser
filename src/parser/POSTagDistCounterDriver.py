from src.parser.POSTagDistCounter import POSTagDistCounter
from sortedcontainers import SortedDict

ptdc = POSTagDistCounter('../../accepted_2.tre')
dist = SortedDict(ptdc.count_distribution())

infile = open('../../output_postag/postag_accepted_2_variation.out', 'w')

def distPrinter(d: SortedDict, f):
    for vocab in d.keys():
        distribution = d[vocab]
        if len(distribution) > 1:
            print(vocab, end='', file=f)
            for pos_tag in distribution:
                print('|', pos_tag, '|', distribution[pos_tag], end='', file=f)
            print(file=f)

distPrinter(dist, infile)