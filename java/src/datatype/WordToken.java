package datatype;

public class WordToken {
  private final String word;
  private final POSTag tag;

  public WordToken(String word, POSTag tag) {
    this.word = word;
    this.tag = tag;
  }

  public String toString() {
    return word + "#" + tag;
  }

  public String getWord() {
    return word;
  }

  public POSTag getTag() {
    return tag;
  }
}
