package extractor;

import datatype.Action;
import datatype.ConstituentLabel;
import datatype.StackToken;
import datatype.WordToken;
import feature.Feature;
import feature.FeatureTemplateSet;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ShiftReduceSimulator {
  private Stack<StackToken> workingStack;
  private Queue<WordToken> wordQueue;
  private List<Action> actions;
  private Iterator<Action> actionIt;
  private FeatureTemplateSet featureTemplateSet;

  public ShiftReduceSimulator(Queue<WordToken> wordQueue, List<Action> actions) {
    this.workingStack = new Stack<>();
    this.wordQueue = wordQueue;
    this.actions = actions; this.actionIt = actions.iterator();
    this.featureTemplateSet = new FeatureTemplateSet(); featureTemplateSet.useAll();
  }

//  public void run() {
//    for (Action action : actions) {
//      List<Feature> extractedFeatures = featureTemplateSet.extract(workingStack, wordQueue, action);
//      for (Feature f : extractedFeatures) System.out.println(f);
//
//      switch (action.getActionType()) {
//        case SHIFT: processSHIFT(); break;
//        case UNARY: processUNARY(action.getLabel()); break;
//        case REDUCE: processREDUCE(action.getLabel()); break;
//        case FINISH: processFINISH(); break;
//      }
//    }
//  }

  public boolean hasNextStep() { return actionIt.hasNext(); }

  public List<Feature> nextStep() {
    Action action = actionIt.next();
    List<Feature> extractedFeatures = featureTemplateSet.extract(workingStack, wordQueue, action);

    switch (action.getActionType()) {
      case SHIFT: processSHIFT(); break;
      case UNARY: processUNARY(action.getLabel()); break;
      case REDUCE: processREDUCE(action.getLabel()); break;
      case FINISH: processFINISH(); break;
    }

    return extractedFeatures;
  }

  private void processSHIFT() {
    WordToken front = wordQueue.remove();
    StackToken item = new StackToken(null, front);
    workingStack.push(item);
  }

  private void processUNARY(ConstituentLabel target) {
    StackToken top = workingStack.pop();
    StackToken item = new StackToken(target, top.getHeadWord(), top);
    workingStack.push(item);
  }

  private void processREDUCE(ConstituentLabel target) {
    StackToken first = workingStack.pop(); // right-child
    StackToken second = workingStack.pop(); // left-child

    WordToken headWord = HeadWordDeterminer.determineHead(first, second);
    StackToken item = new StackToken(target, headWord, second, first);
    workingStack.push(item);
  }

  private void processFINISH() { } // TODO
}
