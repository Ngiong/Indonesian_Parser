import beam.MainParser;
import datatype.POSTag;
import datatype.WordToken;
import org.apache.commons.cli.*;
import perceptron.v2.PerceptronV2;
import tree.ParseTree;
import tree.ParseTreeChecker;
import tree.ParseTreeFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class MainParserCLI {
  private static final int DEFAULT_START_INDENT = 4;
  private static final int DEFAULT_INCREMENT_INDENT = 2;
  private static final String CLASS_TAG = "[MAIN PARSER CLI]";
  private String[] args = null;
  private Options options = new Options();

  public MainParserCLI(String[] args) {
    this.args = args;
    // Train
    options.addOption("t", "train", true, "[TRAIN] Specified treebank for training");
    options.addOption("e", "epochs", true, "[TRAIN] Number or training iteration");
    options.addOption("m", "model", true, "[ALL] Output perceptron filename for training OR Specified perceptron for parsing");
    // Parse
    options.addOption("p", "parse", true, "[PARSE] Specified treebank to be parsed");
    options.addOption("b", "beam", true, "[PARSE] Size of beam for parsing. Default 8");
    options.addOption("n", "nbest", true, "[PARSE] Size of N-best parse for parsing. Default 1");
    options.addOption("o", "output", true, "[PARSE] Result treebank filename");
    // Input
    options.addOption("i", "input", true, "[PARSE] Parse input-specified sentence");
    // Score
    options.addOption("s", "score", true, "[SCORE] Specified parse tree (in bracket notation)");
  }

  public void parse() {
    CommandLineParser parser = new DefaultParser();

    CommandLine cmd = null;
    try {
      cmd = parser.parse(options, args);

      if (cmd.hasOption("t") && cmd.hasOption("e") && cmd.hasOption("m")) { // Train
        String trainTreebank = cmd.getOptionValue("t");
        int epochs = Integer.parseInt(cmd.getOptionValue("e"));
        String modelFilename = cmd.getOptionValue("m");
        runTrain(trainTreebank, epochs, modelFilename);

      } else if (cmd.hasOption("p") && cmd.hasOption("m") && cmd.hasOption("o")) { // Parse
        String testTreebank = cmd.getOptionValue("p");
        String modelFilename = cmd.getOptionValue("m");
        String outputTreebank = cmd.getOptionValue("o");
        int beamSize = cmd.hasOption("b") ? Integer.parseInt(cmd.getOptionValue("b")) : 8;
        int nBest = cmd.hasOption("n") ? Integer.parseInt(cmd.getOptionValue("n")) : 1;
        runParse(testTreebank, modelFilename, beamSize, nBest, outputTreebank);

      } else if (cmd.hasOption("i") && cmd.hasOption("m")) {
        String sentence = cmd.getOptionValue("i");
        String modelFilename = cmd.getOptionValue("m");
        int beamSize = cmd.hasOption("b") ? Integer.parseInt(cmd.getOptionValue("b")) : 8;
        int nBest = cmd.hasOption("n") ? Integer.parseInt(cmd.getOptionValue("n")) : 1;
        runInputParse(sentence, modelFilename, beamSize, nBest);

      } else if (cmd.hasOption("s") && cmd.hasOption("m")) {
        String brackets = cmd.getOptionValue("s");
        String modelFilename = cmd.getOptionValue("m");
        runScoreParseTree(brackets, modelFilename);

      } else {
        help();
      }

    } catch (ParseException e) {
      help();
    }
  }

  private void help() {
    HelpFormatter formater = new HelpFormatter();
    formater.setOptionComparator(new Comparator<Option>() {
      @Override
      public int compare(Option o1, Option o2) {
        return o1.getDescription().compareTo(o2.getDescription());
      }
    });

    String POSTags = Arrays.stream(POSTag.values()).map(POSTag::name).collect(Collectors.joining(", "));

    formater.printHelp("\n" +
      "    [TRAIN] -t -e -m\n" +
      "    [PARSE] -p -m -o (-b?) (-n?)\n" +
      "    [PARSE] -i \"...\" -m (-b?) (-n?)\n" +
      "    [SCORE] -s \"...\" -m",

      "\nIndonesian Shift-Reduce Parser using Beam-Search (Zhu et al., 2013)\n" +
        "For input sentence, use: \n" +
        "         \"|word_1#tag_1| |word_2#tag_2| ... |word_n#tag_n|\"\n" +
        "Example: \"Saya#PRN makan#VBT nasi#NNO .#SYM\"\n" +
        "\nOptions:\n",
      options,
      "\nAvailable POS tags: {" + POSTags + "}"
    );
    System.exit(0);
  }

  private void runTrain(String trainTreebank, int epochs, String outputModelFilename) {
    PerceptronV2 p = new PerceptronV2(trainTreebank, 1);
    for (int i = 0; i < epochs; i++) {
      p.train(1);
      p.toJSON(outputModelFilename + "_" + Integer.toString(i));
    }
  }

  private void runParse(String testTreebank, String modelFilename, int beamSize, int nBest, String outputTreebank) {
    MainParser mainParser = new MainParser(modelFilename, beamSize, nBest);
    mainParser.batchParse(testTreebank, outputTreebank);
  }

  private static final String RUN_INPUT_PARSE_TAG = "[RUN INPUT PARSE]";
  private void runInputParse(String sentence, String modelFilename, int beamSize, int nBest) {
    try {
      Queue<WordToken> wordQueue = new LinkedList<>();

      String[] wordTokens = sentence.split("\\s+");
      for (String wordToken : wordTokens) {
        String[] tmp = wordToken.split("#");
        if (tmp.length != 2) throw new ParseException("Incorrect word token: " + wordToken);

        String word = tmp[0], tag = tmp[1];
        boolean validTag = Arrays.stream(POSTag.values()).filter(x -> x.name().equals(tag)).findAny().isPresent();

        if (!validTag) throw new ParseException("Unknown POSTag: " + wordToken);
        else wordQueue.add(new WordToken(word, POSTag.valueOf(tag)));
      }

      MainParser mainParser = new MainParser(modelFilename, beamSize, nBest);
      long startTime = System.currentTimeMillis();
      ParseTree pt = mainParser.parse(wordQueue);
      long endTime = System.currentTimeMillis();
      double time = 0.001 * (endTime - startTime);
      System.out.println("Parsing Time  : " + time + " s.");
      System.out.println("Parser Output :\n");
      pt.printPretty(DEFAULT_START_INDENT, DEFAULT_INCREMENT_INDENT);

    } catch (ParseException e) {
      System.out.println(CLASS_TAG + RUN_INPUT_PARSE_TAG + e.getMessage());
    }
  }

  private static final String RUN_SCORE_PARSE_TREE_TAG = "[RUN SCORE PARSE TREE]";
  private void runScoreParseTree(String brackets, String modelFilename) {
    try {
      PerceptronV2 p = new PerceptronV2(modelFilename);
      ParseTreeFactory ptf = new ParseTreeFactory();

      ParseTree pt = ptf.getParseTree(brackets, true);
      if (!ParseTreeChecker.isValidTree(pt)) throw new ParseException("Invalid tree/bracket notation.");

      pt.printPretty(DEFAULT_START_INDENT, DEFAULT_INCREMENT_INDENT);
      System.out.println("Parse tree score: " + p.score(pt));

    } catch (ParseException e) {
      System.out.println(CLASS_TAG + RUN_SCORE_PARSE_TREE_TAG + e.getMessage());
    }

  }

  public static void main(String args[]) {
    new MainParserCLI(args).parse();
  }
}