package beam;

import datatype.*;
import extractor.HeadWordDeterminer;

import java.util.*;

public class ParseState implements Comparable<ParseState> {
  private Stack<StackToken> workingStack;
  private Queue<WordToken> wordQueue;
  private boolean isFinished;
  private int score;
  private List<Action> actions;

  public ParseState(Queue<WordToken> words) {
    workingStack = new Stack<>();
    wordQueue = new LinkedList<>(words);
    isFinished = false;
    score = 0;
    actions = new ArrayList<>();
  }

  ParseState(final ParseState ps, Action action, int actionScore) {
    workingStack = new Stack<>(); workingStack.addAll(ps.workingStack);
    wordQueue = new LinkedList<>(); wordQueue.addAll(ps.wordQueue);
    isFinished = ps.isFinished;
    score = ps.score + actionScore;
    actions = new ArrayList<>(); actions.addAll(ps.actions); actions.add(action);

    switch (action.getActionType()) {
      case SHIFT: processSHIFT(); break;
      case UNARY: processUNARY(action.getLabel()); break;
      case REDUCE: processREDUCE(action.getLabel()); break;
      case FINISH: processFINISH(); break;
    }
  }

  public boolean canDo(Action action) {
    switch (action.getActionType()) {
      case SHIFT: return wordQueue.size() > 0;
      case UNARY: return workingStack.size() > 0 && __getLastActionType() != ActionType.UNARY;
      case REDUCE: return workingStack.size() > 1;
      case FINISH: return wordQueue.size() == 0 && workingStack.size() == 1;
    }
    return false;
  }

  private ActionType __getLastActionType() {
    return actions.size() == 0 ? null : actions.get(actions.size()-1).getActionType();
  }

  private void processSHIFT() throws IllegalStateException {
    if (wordQueue.size() == 0)
      throw new IllegalStateException("Trying to SHIFT while queue size = 0.");

    WordToken front = wordQueue.remove();
    StackToken item = new StackToken(front.getTag(), front);
    workingStack.push(item);
  }

  private void processUNARY(ConstituentLabel target) throws IllegalStateException {
    if (workingStack.size() == 0)
      throw new IllegalStateException("Trying to UNARY while stack size = 0.");

    StackToken top = workingStack.pop();
    StackToken item = new StackToken(target, top.getHeadWord(), top);
    workingStack.push(item);
  }

  private void processREDUCE(ConstituentLabel target) throws IllegalStateException {
    if (workingStack.size() < 2)
      throw new IllegalStateException("Trying to REDUCE while stack size < 2.");

    StackToken rightToken = workingStack.pop(); // right-child
    StackToken leftToken = workingStack.pop(); // left-child

    WordToken headWord = HeadWordDeterminer.determineHead(target, rightToken, leftToken);
    StackToken item = new StackToken(target, headWord, leftToken, rightToken);
    workingStack.push(item);
  }

  private void processFINISH() throws IllegalStateException {
    if (wordQueue.size() > 0)
      throw new IllegalStateException("Parsing finished while queue is not empty.");

    if (workingStack.size() != 1)
      throw new IllegalStateException("Parsing finished while stack size != 1.");

    isFinished = true;
  }

  public Stack<StackToken> getWorkingStack() {
    return workingStack;
  }

  public Queue<WordToken> getWordQueue() {
    return wordQueue;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public int getScore() {
    return score;
  }

  public List<Action> getActions() {
    return actions;
  }

  @Override
  public int compareTo(ParseState o) {
    return score - o.score;
  }
}
