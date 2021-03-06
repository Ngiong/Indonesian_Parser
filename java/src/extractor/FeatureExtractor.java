package extractor;

import datatype.Action;
import datatype.ConstituentLabel;
import datatype.StackToken;
import datatype.WordToken;
import feature.Feature;
import feature.FeatureTemplateSet;
import javafx.util.Pair;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class FeatureExtractor {
  private Stack<StackToken> workingStack;
  private Queue<WordToken> wordQueue;
  private Iterator<Action> actionIt;
  private FeatureTemplateSet featureTemplateSet;

  public FeatureExtractor(Stack<StackToken> workingStack, Queue<WordToken> wordQueue, List<Action> actions) {
    this.workingStack = workingStack;
    this.wordQueue = wordQueue;
    this.actionIt = actions.iterator();
    this.featureTemplateSet = new FeatureTemplateSet(); featureTemplateSet.useAll();
  }

  public boolean hasNextStep() { return actionIt.hasNext(); }

  public Pair<List<Feature>, Action> nextStep() {
    Action action = actionIt.next();
    List<Feature> extractedFeatures = featureTemplateSet.extract(workingStack, wordQueue);

    switch (action.getActionType()) {
      case SHIFT: processSHIFT(); break;
      case UNARY: processUNARY(action.getLabel()); break;
      case REDUCE: processREDUCE(action.getLabel()); break;
      case FINISH: processFINISH(); break;
    }

    return new Pair(extractedFeatures, action);
  }

  private void processSHIFT() {
    WordToken front = wordQueue.remove();
    StackToken item = new StackToken(front.getTag(), front);
    workingStack.push(item);
  }

  private void processUNARY(ConstituentLabel target) {
    StackToken top = workingStack.pop();
    StackToken item = new StackToken(target, top.getHeadWord(), top);
    workingStack.push(item);
  }

  private void processREDUCE(ConstituentLabel target) {
    StackToken rightToken = workingStack.pop(); // right-child
    StackToken leftToken = workingStack.pop(); // left-child

    WordToken headWord = HeadWordDeterminer.determineHead(target, rightToken, leftToken);
    StackToken item = new StackToken(target, headWord, leftToken, rightToken);
    workingStack.push(item);
  }

  private void processFINISH() { /** DO NOTHING */ }
}
