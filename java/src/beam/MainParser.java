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
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MainParser {
  private static final String CLASS_TAG = "[MAIN PARSER]";
  private final int BEAM_SIZE;
  private final int N_BEST_PARSE;
  private FeatureTemplateSet featureTemplateSet;
  private PerceptronV2 perceptron;
  private ParseTreeFactory parseTreeFactory;

  public MainParser(String perceptronFile, int beamSize, int nBestParse) {
    BEAM_SIZE = beamSize; N_BEST_PARSE = nBestParse;
    featureTemplateSet = new FeatureTemplateSet(); featureTemplateSet.useAll();
    perceptron = new PerceptronV2(perceptronFile);
    parseTreeFactory = new ParseTreeFactory();
  }

  public ParseTree parse(Queue<WordToken> queue) {
    Agenda agenda = new Agenda(BEAM_SIZE);
    agenda.push(new ParseState(queue));

    List<ParseState> result = new ArrayList<>();
    while (result.size() < N_BEST_PARSE && !agenda.empty()) {
      ParseState top = agenda.pop();
      if (top.isFinished()) result.add(top);
      else agenda.pushAll(expand(top));
    }

    ParseState bestState = result.get(0);
    for (ParseState state : result)
      if (state.getScore() > bestState.getScore()) bestState = state;

    List<Action> actions = bestState.getActions();
    ParseTree pt = parseTreeFactory.getParseTree(queue, actions);
    pt.debinarize();
    return pt;
  }

  private static final String BATCH_PARSE_TAG = "[BATCH PARSE]";
  public void batchParse(String treebankFilename, String outputFilename) {
    try {
      File outputFile = new File(outputFilename);
      FileOutputStream fos = new FileOutputStream(outputFile);
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

      String line; int idx = 0;
      BufferedReader reader = new BufferedReader(new FileReader(treebankFilename));
      while ((line = reader.readLine()) != null) {
        Queue<WordToken> wordQueue = WordTokenExtractor.getWordQueue(line);
        ParseTree parsed = parse(wordQueue);
        bw.write(parsed.toString());
        bw.newLine();
        System.out.println("Parsed " + ++idx + " sentences.");
      }
      bw.close();
      reader.close();
      
    } catch (Exception e) {
      System.err.println(CLASS_TAG + BATCH_PARSE_TAG + e.getMessage());
      e.printStackTrace();
    }

  }

  private List<ParseState> expand(ParseState state) {
    List<ParseState> result = new ArrayList<>();
    Stack<StackToken> workingStack = state.getWorkingStack();
    Queue<WordToken> wordQueue = state.getWordQueue();

    List<Feature> extractedFeatures = featureTemplateSet.extract(workingStack, wordQueue);
    for (Action action : Action.values()) {
      if (state.canDo(action)) {
        int actionScore = perceptron.score(extractedFeatures, action);
        result.add(new ParseState(state, action, actionScore));
      }
    }
    return result;
  }
}
