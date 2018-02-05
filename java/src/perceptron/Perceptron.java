package perceptron;

import datatype.Action;
import datatype.WordToken;
import extractor.ActionExtractor;
import extractor.ShiftReduceSimulator;
import extractor.WordTokenExtractor;
import feature.Feature;
import tree.ParseTree;
import tree.ParseTreeFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Perceptron {
  private final String CLASS_TAG = "[PERCEPTRON]";

  private ParseTreeFactory parseTreeFactory;
  private Map<String, Integer> learningParameter;
  private BufferedReader reader;
  private File treebankFile;

  public Perceptron(String filepath) {
    parseTreeFactory = new ParseTreeFactory();
    try {
      treebankFile = new File(filepath);
      reader = new BufferedReader(new FileReader(treebankFile));
    } catch (Exception e) {
      System.err.println(CLASS_TAG + e.getMessage());
      e.printStackTrace();
    }
  }

  public void train() throws IOException {
    String line;
    while ((line = reader.readLine()) != null) {
      ParseTree pt = parseTreeFactory.getParseTree(line, true);
      List<Action> actions = ActionExtractor.getParsingActions(pt);
      Queue<WordToken> wordQueue = WordTokenExtractor.getWordQueue(pt);

      ShiftReduceSimulator sim = new ShiftReduceSimulator(wordQueue, actions);
      while (sim.hasNextStep()) {
        List<Feature> extractedFeatures = sim.nextStep();

      }
      /*
      ParseTree pt = parseTreeFactory.getParseTree(VALID_1, true);
    List<Action> actions = ActionExtractor.getParsingActions(pt);

    Queue<WordToken> wordQueue = ParseTree.getWordQueue(pt);
    ShiftReduceSimulator sim = new ShiftReduceSimulator(wordQueue, actions);
    sim.run();
       */
    }
  }

  @Override
  public void finalize() {
    try {
      reader.close();
    } catch (IOException e) {
      System.err.println(CLASS_TAG + e.getMessage());
      e.printStackTrace();
    }
  }
}
