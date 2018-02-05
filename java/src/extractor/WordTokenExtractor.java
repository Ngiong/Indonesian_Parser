package extractor;

import datatype.POSTag;
import datatype.WordToken;
import tree.ParseTree;

import java.util.LinkedList;
import java.util.Queue;

public class WordTokenExtractor {
  public static Queue<WordToken> getWordQueue(ParseTree pt) {
    if (pt.isLeaf()) {
      Queue<WordToken> result = new LinkedList<>();
      WordToken token = new WordToken(pt.getWord(), POSTag.valueOf(pt.getNodeTag()));
      result.add(token);
      return result;
    } else if (pt.getChildren().size() == 1) {
      return getWordQueue(pt.getChildren().get(0));
    } else {
      Queue<WordToken> first = getWordQueue(pt.getChildren().get(0));
      Queue<WordToken> second = getWordQueue(pt.getChildren().get(1));
      first.addAll(second);
      return first;
    }
  }
}
