package beam;

import datatype.Action;
import datatype.StackToken;
import datatype.WordToken;
import feature.Feature;
import feature.FeatureTemplateSet;
import perceptron.Perceptron;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MainParser {
  private Agenda agenda;
  private FeatureTemplateSet featureTemplateSet;
  private Perceptron perceptron;

  public MainParser(String perceptronFile, int beamSize) {
    agenda = new Agenda(beamSize);
    featureTemplateSet = new FeatureTemplateSet(); featureTemplateSet.useAll();
    perceptron = Perceptron.loadAsReadOnly(perceptronFile);
  }

  public void parse(Queue<WordToken> queue) { // TODO : Return ParseTree
    agenda.push(new ParseState(queue));

    ParseState result = null;
    while (result == null) {
      ParseState top = agenda.pop();
      if (top.isFinished()) result = top;
      else expand(top);
    }
    System.out.println("COMPLETED");

    List<Action> actions = result.getActions();
    for (Action action : actions) System.out.println(action);
  }

  private void expand(ParseState state) {
    Stack<StackToken> workingStack = state.getWorkingStack();
    Queue<WordToken> wordQueue = state.getWordQueue();

    List<Feature> extractedFeatures = featureTemplateSet.extract(workingStack, wordQueue);
    List<Action> allActions = Action.getAllActions();
    for (Action action : allActions) {
      if (state.canDo(action)) {
        int actionScore = perceptron.score(extractedFeatures, action);
        agenda.push(new ParseState(state, action, actionScore));
      }
    }
  }
}
