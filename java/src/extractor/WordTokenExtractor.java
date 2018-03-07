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

  public static Queue<WordToken> getWordQueue(String brackets) {
    Queue<WordToken> result = new LinkedList<>();

    boolean foundOpening = false;
    int lastOpening = 0;
    for (int i = 0; i < brackets.length(); i++) {
      char c = brackets.charAt(i);
      if (c == ')' && foundOpening) {
        String[] tokens = brackets.substring(lastOpening + 1, i).split("\\s+");
        result.add(new WordToken(tokens[1], POSTag.valueOf(tokens[0])));
        foundOpening = false;
      } else if (c == '(') {
        lastOpening = i;
        foundOpening = true;
      }
    }

    return result;
  }
}
