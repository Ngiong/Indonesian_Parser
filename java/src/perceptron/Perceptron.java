package perceptron;

import com.google.gson.Gson;
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

import java.io.*;
import java.util.*;

public class Perceptron {
  private static final String CLASS_TAG = "[PERCEPTRON]";

  private ParseTreeFactory parseTreeFactory;
  private LearningParameter learningParameter;
  private String treebankFilename;
  private int learningRate;
  private boolean readOnly;

  public Perceptron() { // Read-only Perceptron
    readOnly = true;
  }

  public Perceptron(String treebankPath, int _learningRate) {
    if (_learningRate < 0)
      throw new IllegalArgumentException("Learning Rate must be greater than 0!");

    parseTreeFactory = new ParseTreeFactory();
    learningParameter = new LearningParameter();
    learningRate = _learningRate;
    treebankFilename = treebankPath;
    readOnly = false;
  }

  private static final String TRAIN_TAG = "[TRAIN]";
  public void train(int epochs, String epochSaveFilename) {
    try {
      if (readOnly)
        throw new IllegalAccessException("Read-only Perceptron cannot be used for training.");

      List<Integer> trainingSummary = new ArrayList<>();
      Stack<StackToken> workingMemory;
      Queue<WordToken> wordQueue;

      for (int i = 0; i < epochs; i++) {
        int lineProcessed = 0, wrongPrediction = 0;

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(treebankFilename));
        while ((line = reader.readLine()) != null) {
          ParseTree pt = parseTreeFactory.getParseTree(line, true);
          List<Action> actions = ActionExtractor.getParsingActions(pt, true);
          workingMemory = new Stack<>();
          wordQueue = WordTokenExtractor.getWordQueue(pt);

          ShiftReduceSimulator sim = new ShiftReduceSimulator(workingMemory, wordQueue, actions);
          while (sim.hasNextStep()) {
            Pair<List<Feature>, Action> trainingExample = sim.nextStep();
            List<Feature> extractedFeatures = trainingExample.getKey();
            Action reference = trainingExample.getValue();

            Action predicted = predict(extractedFeatures);
            if (!predicted.equals(reference)) {
              wrongPrediction++; learningParameter.update(extractedFeatures, predicted, reference, learningRate);
            }
          }

          System.out.println("Line Processed: " + ++lineProcessed);
        }
        trainingSummary.add(wrongPrediction);
        reader.close();

        if (epochSaveFilename != null)
          writeToFile(epochSaveFilename + i);
      }
      System.out.println("Summary: " + trainingSummary.toString());
    } catch (Exception e) {
      System.err.println(CLASS_TAG + TRAIN_TAG + e.getMessage());
      e.printStackTrace();
    }
  }

  public Action predict(List<Feature> extractedFeatures) {
    List<Action> allActions = Action.getAllActions();

    int maxScore = Integer.MIN_VALUE;
    Action bestAction = null;

    for (Action action : allActions) {
      int myScore = 0;
      for (Feature feature : extractedFeatures) myScore += learningParameter.calculate(feature, action);
      if (myScore > maxScore ||
          myScore == maxScore && action.getActionType().isGreaterThan(bestAction.getActionType())) {
        maxScore = myScore; bestAction = action;
      }
    }

    return bestAction;
  }

  public int score(List<Feature> features, Action action) {
    int result = 0;
    for (Feature feature : features) result += learningParameter.calculate(feature, action);
    return result;
  }

  private static final String WRITE_TO_FILE_TAG = "[WRITE TO FILE]";
  public void writeToFile(String filename) {
    try {
      Gson gson = new Gson();
      String lpJson = gson.toJson(learningParameter);

      PrintWriter out = new PrintWriter(filename + ".json");
      out.println(lpJson);
      out.close();

    } catch (Exception e) {
      System.err.println(CLASS_TAG + WRITE_TO_FILE_TAG + e.getMessage());
      e.printStackTrace();
    }
  }

  private static final String READ_FROM_FILE_TAG = "[READ FROM FILE]";
  public void readFromFile(String filepath) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filepath));

      Gson gson = new Gson();
      learningParameter = gson.fromJson(reader, LearningParameter.class);

    } catch (Exception e) {
      System.err.println(CLASS_TAG + READ_FROM_FILE_TAG + e.getMessage());
      e.printStackTrace();
    }
  }

  private static final String LOAD_AS_READ_ONLY_TAG = "[LOAD AS READ ONLY]";
  public static Perceptron loadAsReadOnly(String filepath) {
    Perceptron result = new Perceptron();
    result.readFromFile(filepath);
    System.out.println(CLASS_TAG + LOAD_AS_READ_ONLY_TAG + "Perceptron has been loaded successfully.");
    return result;
  }
}
