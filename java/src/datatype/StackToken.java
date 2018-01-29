package datatype;

public final class StackToken {
  private final WordToken headWord;
  private final Label label;
  private StackToken[] prevs;

  public StackToken(Label label, WordToken headWord, StackToken... prevs) {
    this.headWord = headWord;
    this.label = label;
    if (prevs.length == 0) this.prevs = null;
    else this.prevs = prevs;
  }

  public WordToken getHeadWord() {
    return headWord;
  }

  public Label getLabel() {
    return label;
  }

  public StackToken[] getPrevs() {
    return prevs;
  }
}
