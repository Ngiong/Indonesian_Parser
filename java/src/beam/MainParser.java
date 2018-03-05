package beam;

import datatype.Action;
import datatype.StackToken;
import datatype.WordToken;
import feature.Feature;
import feature.FeatureTemplateSet;
import perceptron.v1.Perceptron;
import perceptron.v2.PerceptronV2;
import tree.ParseTree;
import tree.ParseTreeFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MainParser {
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
