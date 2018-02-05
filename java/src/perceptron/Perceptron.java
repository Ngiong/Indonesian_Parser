package perceptron;

import datatype.Action;
import datatype.StackToken;
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
import java.util.*;

public class Perceptron {
  private final String CLASS_TAG = "[PERCEPTRON]";

  private ParseTreeFactory parseTreeFactory;
  private Map<String, Integer> learningParameter;
  private BufferedReader reader;
  private File treebankFile;

  private Stack<StackToken> workingMemory;
  private Queue<WordToken> wordQueue;

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
      workingMemory = new Stack<>(); wordQueue = WordTokenExtractor.getWordQueue(pt);

      ShiftReduceSimulator sim = new ShiftReduceSimulator(workingMemory, wordQueue, actions);
      while (sim.hasNextStep()) {
        List<Feature> extractedFeatures = sim.nextStep();
        Action prediction = predict(extractedFeatures);
      }
    }
  }

  public Action predict(List<Feature> extractedFeatures) {
    List<Action> allActions = Action.getAllActions();

    int maxScore = 0;
    Action bestAction = allActions.get(0);

    for (Action action : allActions) {
      int myScore = 0;
      for (Feature feature : extractedFeatures) myScore += __calculateActionScore(feature, action);
      if (myScore > maxScore) { maxScore = myScore; bestAction = action; }
    }

    return bestAction;
  }

  private int __calculateActionScore(Feature feature, Action action) {
    String featureAction = feature.toString() + "." + action.toString();
    if (learningParameter.containsKey(featureAction)) return learningParameter.get(feature.toString());
    else return 0;
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
