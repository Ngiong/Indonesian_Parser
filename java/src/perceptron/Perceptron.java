package perceptron;

import datatype.Action;
import datatype.StackToken;
import datatype.WordToken;
import extractor.ActionExtractor;
import extractor.ShiftReduceSimulator;
import extractor.WordTokenExtractor;
import feature.Feature;
import javafx.util.Pair;
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
  private int learningRate;
  private int epochs;

  private Set<String> featureCounter;

  public Perceptron(String filepath, int learningRate, int epochs) {
    parseTreeFactory = new ParseTreeFactory();
    featureCounter = new HashSet<>();
    this.learningRate = learningRate; this.epochs = epochs;

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
        Pair<List<Feature>, Action> trainingExample = sim.nextStep();
        List<Feature> extractedFeatures = trainingExample.getKey();
        Action reference = trainingExample.getValue();

        Action predicted = predict(extractedFeatures);
        if (!predicted.equals(reference)) __updateLearningParameter(extractedFeatures, predicted, reference);
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

  private void __updateLearningParameter(List<Feature> extractedFeatures, Action predicted, Action reference) {
    for (Feature feature : extractedFeatures) {
      String incrementFeatureAction = feature.featureActionString(reference);
      String punishedFeatureAction = feature.featureActionString(predicted);

      featureCounter.add(incrementFeatureAction);
      featureCounter.add(punishedFeatureAction);
      // Update and Punish
//      if (learningParameter.containsKey(incrementFeatureAction)) {
//
//      }
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
