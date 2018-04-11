import argparse


def runEvaluate(parsed_treebank: str, gold_treebank: str):
    from treebank.experiment.PARSEVALEvaluator import PARSEVALEvaluator
    evaluator = PARSEVALEvaluator()
    result = evaluator.batchEvaluate(parsed_treebank, gold_treebank)
    print('Evaluation result: ', result)


def runVisualize(bracket: str, useNLTK: bool):
    if useNLTK:
        from parsetree.ParseTreeVisualizer import ParseTreeVisualizer
        visualizer = ParseTreeVisualizer(bracket)
        visualizer.start()
    else:
        from parsetree.ParseTree import ParseTree
        pt = ParseTree(bracket);
        pt.makeTree()
        pt.printPretty(4, 2)


def runCompare(parsed_treebank: str, gold_treebank: str):
    from treebank.experiment.TreebankVisualizer import TreebankVisualizer
    comparator = TreebankVisualizer(gold_treebank, parsed_treebank)
    comparator.start()


if __name__ == '__main__':
    description_txt = 'Python Constituent Parser Evaluation and Analysis Tools'
    parser = argparse.ArgumentParser(description=description_txt)
    subparsers = parser.add_subparsers(dest='command', help='commands')

    # parseval evaluate
    parseval_parser = subparsers.add_parser('evaluate', help='Evaluate parsed treebank using PARSEVAL evaluation')
    parseval_parser.add_argument('parsed', action='store', help='Parsed treebank filename')
    parseval_parser.add_argument('gold', action='store', help='Reference treebank filename')

    # visualization
    visualization_parser = subparsers.add_parser('visualize', help='Visualize a parse tree')
    visualization_parser.add_argument('bracket', action='store',
                                      help='Parse tree to be visualized (in bracket notation)')
    visualization_parser.add_argument('--nltk', action='store_true', default=False,
                                      help='Visualize using NLTK visualization GUI')

    # compare
    compare_parser = subparsers.add_parser('compare',
                                           help='Compare parsed treebank using reference treebank. [UPPER parse tree = REFERENCE Treebank]')
    compare_parser.add_argument('parsed', action='store', help='Parsed treebank filename')
    compare_parser.add_argument('gold', action='store', help='Reference treebank filename')

    args = parser.parse_args()
    if args.command == 'evaluate':
        runEvaluate(args.parsed, args.gold)
    elif args.command == 'visualize':
        runVisualize(args.bracket, args.nltk)
    elif args.command == 'compare':
        runCompare(args.parsed, args.gold)
