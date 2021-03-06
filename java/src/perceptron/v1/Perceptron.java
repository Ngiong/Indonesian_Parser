package perceptron.v1;

import datatype.Action;
import datatype.StackToken;
import datatype.WordToken;
import extractor.ActionExtractor;
import extractor.FeatureExtractor;
import extractor.WordTokenExtractor;
import feature.Feature;
import javafx.util.Pair;
import perceptron.ParameterRepresentation;
import tree.ParseTree;
import tree.ParseTreeFactory;

import java.io.*;
import java.util.*;

public class Perceptron {
  private static final String CLASS_TAG = "[PERCEPTRON]";

  private ParseTreeFactory parseTreeFactory;
  private ParameterRepresentation vectorParameter;
  private String treebankFilename;
  private int learningRate;
  private boolean readOnly;

  public Perceptron(String perceptronFilepath) { // Read-only Perceptron
    readOnly = true;
    vectorParameter = new VectorParameter(perceptronFilepath);
  }

  public Perceptron(String treebankPath, int _learningRate) {
    if (_learningRate < 0)
      throw new IllegalArgumentException("Learning Rate must be greater than 0!");

    parseTreeFactory = new ParseTreeFactory();
    vectorParameter = new VectorParameter();
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

          FeatureExtractor sim = new FeatureExtractor(workingMemory, wordQueue, actions);
          while (sim.hasNextStep()) {
            Pair<List<Feature>, Action> trainingExample = sim.nextStep();
            List<Feature> extractedFeatures = trainingExample.getKey();
            Action reference = trainingExample.getValue();

            Action predicted = predict(extractedFeatures);
            if (!predicted.equals(reference)) {
              wrongPrediction++; vectorParameter.update(extractedFeatures, predicted, reference, learningRate);
            }
          }

          System.out.println("[" + i + "] " + ++lineProcessed);
        }
        trainingSummary.add(wrongPrediction);
        reader.close();

        if (epochSaveFilename != null)
          vectorParameter.toJSON(epochSaveFilename + i);
      }
      System.out.println("Summary: " + trainingSummary.toString());
    } catch (Exception e) {
      System.err.println(CLASS_TAG + TRAIN_TAG + e.getMessage());
      e.printStackTrace();
    }
  }

  public Action predict(List<Feature> extractedFeatures) {
    int maxScore = Integer.MIN_VALUE;
    Action bestAction = null;

    for (Action action : Action.values()) {
      int myScore = 0;
      for (Feature feature : extractedFeatures) myScore += vectorParameter.calculate(feature, action);
      if (myScore > maxScore || myScore == maxScore && action.ordinal() < bestAction.ordinal()) {
        maxScore = myScore; bestAction = action;
      }
    }

    return bestAction;
  }

  public int score(List<Feature> features, Action action) {
    int result = 0;
    for (Feature feature : features) result += vectorParameter.calculate(feature, action);
    return result;
  }

  public void fromJSON(String filepath) { vectorParameter.fromJSON(filepath); }
  public void toJSON(String filename) { vectorParameter.toJSON(filename); }
}

//  private static final String SERIALIZE_TO_FILE_TAG = "[SERIALIZE TO FILE]";
//  public void serializeToFile(String filename) {
//    try {
//      FileOutputStream fos = new FileOutputStream(filename + ".ser");
//      ObjectOutputStream oos = new ObjectOutputStream(fos);
//      oos.writeObject(vectorParameter);
//      oos.close();
//    } catch (Exception e) {
//      System.err.println(CLASS_TAG + SERIALIZE_TO_FILE_TAG + e.getMessage());
//      e.printStackTrace();
//    }
//  }
//
//  private static final String DESERIALIZE_FROM_FILE_TAG = "[DESERIALIZE FROM FILE]";
//  public void deserializeFromFile(String filepath) {
//    try {
//      FileInputStream fis = new FileInputStream(filepath);
//      ObjectInputStream ois = new ObjectInputStream(fis);
//      vectorParameter = (VectorParameter) ois.readObject();
//      ois.close();
//    } catch (Exception e) {
//      System.err.println(CLASS_TAG + DESERIALIZE_FROM_FILE_TAG + e.getMessage());
//      e.printStackTrace();
//    }
//  }
