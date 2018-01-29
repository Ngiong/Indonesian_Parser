package extractor;

import datatype.Action;
import datatype.ConstituentLabel;
import datatype.StackToken;
import datatype.WordToken;
import feature.Feature;
import feature.FeatureTemplate;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ShiftReduceSimulator {
  private Stack<StackToken> workingStack;
  private Queue<WordToken> wordQueue;
  private List<Action> actions;

  public ShiftReduceSimulator(Queue<WordToken> wordQueue, List<Action> actions) {
    this.workingStack = new Stack<>();
    this.wordQueue = wordQueue;
    this.actions = actions;
  }

  public void run() {
    for (Action action : actions) {
      FeatureTemplate t = FeatureTemplate.Q0_W_T;
      Feature f = t.extract(workingStack, wordQueue, action);
      System.out.println(f);

      switch (action.getActionType()) {
        case SHIFT: processSHIFT(); break;
        case UNARY: processUNARY(action.getLabel()); break;
        case REDUCE: processREDUCE(action.getLabel()); break;
        case FINISH: processFINISH(); break;
      }
    }
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
