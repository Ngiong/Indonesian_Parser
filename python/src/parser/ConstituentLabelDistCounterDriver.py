from src.parser.ConstituentLabelDistCounter import ConstituentLabelDistCounter
from sortedcontainers import SortedDict

cldc = ConstituentLabelDistCounter('../../result_without_undip_uin.tre')
ptdc = POSTagDistCounter('../../result_without_undip_uin.tre')
dist = SortedDict(ptdc.count_distribution())

infile = open('../../output_postag/pos_tag_count_result_3.out', 'w')

def distPrinter(d: SortedDict, f):
    for vocab in d.keys():
        distribution = d[vocab]
        # if len(distribution) > 1:
        print(vocab, end='', file=f)
        for pos_tag in distribution:
            print('|', pos_tag, '|', distribution[pos_tag], end='', file=f)
        print(file=f)

distPrinter(dist, infile)