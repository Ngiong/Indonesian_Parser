package tree;

import datatype.Action;
import datatype.WordToken;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ParseTreeFactory {
  public ParseTree getParseTree(String brackets, boolean usingBinaryNodes) {
    ParseTree pt = new ParseTree(brackets, usingBinaryNodes); pt.makeTree();
    return pt;
  }

  public ParseTree getParseTree(Queue<WordToken> wordQueue, List<Action> actionList) {
    Stack<ParseTree> workingMemory = new Stack<>();
    for (Action action : actionList) {
      switch (action.getActionType()) {
        case SHIFT: {
          WordToken word = wordQueue.remove();
          ParseTree pt = new ParseTree(word.getTag().toString(), word.getWord()); pt.makeTree();
          workingMemory.push(pt); break;
        }
        case REDUCE: {
          String label = action.getLabel().toString();
          ParseTree rightChild = workingMemory.pop();
          ParseTree leftChild = workingMemory.pop();
          ParseTree pt = new ParseTree(label, leftChild, rightChild);
          workingMemory.push(pt); break;
        }
        case UNARY: {
          String label = action.getLabel().toString();
          ParseTree child = workingMemory.pop();
          ParseTree pt = new ParseTree(label, child);
          workingMemory.push(pt); break;
        }
        case FINISH: {
          ParseTree result = workingMemory.pop();
          return result;
        }
      }
    }

    return null;
  }
}
