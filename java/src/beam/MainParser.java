package beam;

import datatype.Action;
import datatype.StackToken;
import datatype.WordToken;
import extractor.WordTokenExtractor;
import feature.Feature;
import feature.FeatureTemplateSet;
import perceptron.v2.PerceptronV2;
import tree.ParseTree;
import tree.ParseTreeFactory;

import java.io.*;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MainParser {
  private static final String CLASS_TAG = "[MAIN PARSER]";
  private Agenda agenda;
  private FeatureTemplateSet featureTemplateSet;
  private PerceptronV2 perceptron;
  private ParseTreeFactory parseTreeFactory;

  public MainParser(String perceptronFile, int beamSize) {
    agenda = new Agenda(beamSize);
    featureTemplateSet = new FeatureTemplateSet(); featureTemplateSet.useAll();
    perceptron = new PerceptronV2(perceptronFile);
    parseTreeFactory = new ParseTreeFactory();
  }

  public ParseTree parse(Queue<WordToken> queue) {
    agenda.push(new ParseState(queue));

    ParseState result = null;
    while (result == null) {
      ParseState top = agenda.pop();
      if (top.isFinished()) result = top;
      else expand(top);
    }

    List<Action> actions = result.getActions();
    ParseTree pt = parseTreeFactory.getParseTree(queue, actions);
    return pt;
  }

  private static final String BATCH_PARSE_TAG = "[BATCH PARSE]";
  public void batchParse(String treebankFilename, String outputFilename) {
    try {
      File outputFile = new File(outputFilename);
      FileOutputStream fos = new FileOutputStream(outputFile);
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

      String line;
      BufferedReader reader = new BufferedReader(new FileReader(treebankFilename));
      while ((line = reader.readLine()) != null) {
        Queue<WordToken> wordQueue = WordTokenExtractor.getWordQueue(line);
        ParseTree parsed = parse(wordQueue);
        bw.write(parsed.toString());
        bw.newLine();
      }
      bw.close();
      reader.close();
      
    } catch (Exception e) {
      System.err.println(CLASS_TAG + BATCH_PARSE_TAG + e.getMessage());
      e.printStackTrace();
    }

  }

  private void expand(ParseState state) {
    Stack<StackToken> workingStack = state.getWorkingStack();
    Queue<WordToken> wordQueue = state.getWordQueue();

    List<Feature> extractedFeatures = featureTemplateSet.extract(workingStack, wordQueue);
    for (Action action : Action.values()) {
      if (state.canDo(action)) {
        int actionScore = perceptron.score(extractedFeatures, action);
        agenda.push(new ParseState(state, action, actionScore));
      }
    }
  }
}
