package tree;

import java.util.*;
import java.util.stream.Collectors;

public class ParseTree {
  private final String BRACKETS;
  private final boolean USING_BINARY_NODES;
  private boolean IS_LEAF;
  private String NODE_TAG, WORD;
  private List<ParseTree> CHILDREN;

  ParseTree(String nodeTag, String word) {
    this(nodeTag + " " + word, true);
  }

  ParseTree(String label, ParseTree... children) {
    StringJoiner sj = new StringJoiner(")(","(", ")");
    for (ParseTree child : children) sj.add(child.toString());
    BRACKETS = sj.toString();
    USING_BINARY_NODES = true;
    IS_LEAF = false;
    NODE_TAG = label;
    CHILDREN = Arrays.stream(children).collect(Collectors.toList());
  }

  ParseTree(String _brackets, boolean _binaryNodes) {
    BRACKETS = _brackets;
    USING_BINARY_NODES = _binaryNodes;
    IS_LEAF = __isLeaf(_brackets);
    CHILDREN = IS_LEAF ? null : new ArrayList<>();
  }

  void makeTree() {
    String[] tmp = BRACKETS.split("\\s+");

    if (BRACKETS.indexOf('(') == -1) { // MAKE LEAF
      IS_LEAF = true;
      NODE_TAG = tmp[0];
      WORD = tmp[1];
      CHILDREN = null;

    } else { // MAKE TREE
      IS_LEAF = false;
      NODE_TAG = tmp[0];

      int j;
      for (int i = 0; i < BRACKETS.length(); i++) {
        if (BRACKETS.charAt(i) == '(') {
          j = __getClosingBracketIdx(i); String brackets = BRACKETS.substring(i+1, j);
          ParseTree child = new ParseTree(brackets, USING_BINARY_NODES);
          child.makeTree();
          CHILDREN.add(child);
          i = j;
        }
      }

      if (USING_BINARY_NODES && CHILDREN.size() > 2) __binarize();
    }
  }

  @Override
  public String toString() {
    String result = NODE_TAG + " ";
    if (IS_LEAF) result += WORD;
    else {
      for (ParseTree child : CHILDREN)
        result += "(" + child.toString() + ")";
    }
    return result;
  }

  private void __binarize() {
    int numOfChildren = CHILDREN.size();
    String intermediateTag = NODE_TAG + "_";

    // Last TWO Children
    ParseTree bottom = new ParseTree(BRACKETS, USING_BINARY_NODES);
    bottom.addChildren(CHILDREN.get(numOfChildren-2), CHILDREN.get(numOfChildren-1));
    bottom.NODE_TAG = intermediateTag;

    // Intermediate Stage
    ParseTree current;
    for (int i = numOfChildren-3; i >= 1; i--) {
      current = new ParseTree(BRACKETS, USING_BINARY_NODES);
      current.addChildren(CHILDREN.get(i), bottom);
      current.NODE_TAG = intermediateTag;

      bottom = current;
    }

    // First Children
    List<ParseTree> tmpChildren = new ArrayList<>();
    tmpChildren.add(CHILDREN.get(0)); tmpChildren.add(bottom);
    // NODE_TAG = NODE_TAG
    CHILDREN = tmpChildren;
  }

  private int __getClosingBracketIdx(int i) {
    int j = i + 1;
    int bracket_cnt = 1;
    boolean found = false;

    while (!found && j < BRACKETS.length()) {
      if (BRACKETS.charAt(j) == '(') bracket_cnt++;
      else if (BRACKETS.charAt(j) == ')') bracket_cnt--;

      if (bracket_cnt == 0) found = true;
      else j++;
    }

    return found ? j : -1;
  }

  private boolean __isLeaf(String brackets) {
    return brackets.chars().filter(x -> x == '(').count() == 0;
  }

  public boolean isLeaf() {
    return IS_LEAF;
  }

  public String getNodeTag() {
    return NODE_TAG;
  }

  public String getWord() {
    return WORD;
  }

  public List<ParseTree> getChildren() {
    return CHILDREN;
  }

  public void addChildren(ParseTree... parseTrees) {
    for (int i = 0; i < parseTrees.length; i++) CHILDREN.add(parseTrees[i]);
  }
}
