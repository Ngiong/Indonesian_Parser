package extractor;

import datatype.ConstituentLabel;
import datatype.POSTag;
import datatype.StackToken;
import datatype.WordToken;

import java.util.List;

public class HeadWordDeterminer {
  private static final String[] STANDARD_GOOD_HEAD = { "VBI", "VBT", "VBL", "VBP", "VBE", "NNO", "NNP", "PRN", "ADJ", "PRI", "ADV", "ADK", " NUM", "NEG", "PPO", "CCN", "CSN" };
  private static final String[] STANDARD_BAD_HEAD = { "INT", "PAR", "SYM", "LBR", "RBR", "PRR", "PRK", "KUA", "ART", "NNP", "UNS", "$$$" };
  public static WordToken determineHead(ConstituentLabel target, StackToken rightToken, StackToken leftToken) {
    return rightToken.getHeadWord();
  }
//    switch (target) {
//      case AdjP: case AdjP_: {
//        String[] priority = { "ADJ", "AdjP", "NNO" };
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//      case AdvP: case AdvP_: {
//        String[] priority = { "ADV", "ADK", "AdvP" };
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//      case NP: case NP_: {
//        String[] priority = { "NNO", "NP", "NNP" };
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//      case PP: case PP_: {
//        String[] priority = { "PPO", "PP", "NP", "NNO", "NNP" };
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//      case VP: case VP_: {
//        String[] priority = { "VBI", "VBT", "VBP", "VP", "VBL", "VBE" };
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//      case CP: case CP_: {
//        String[] priority = { "SBAR", "CCN", "CSN", "PRI" };
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//      case RPN: case RPN_: {
//        String[] priority = { "VP", "SBAR", "VBI", "VBT", "VBP" };
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//      case S: case S_:
//      case SBAR: case SBAR_: {
//        String[] priority = { "SBAR", "VP", "VBI", "VBT", "VBP", "NP", "NNO" };
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//      case SQ: case SQ_: {
//        String[] priority = { "PRI", "SBAR", "VP", "VBI", "VBT", "VBP", "NP", "NNO" };
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//      default: {
//        String[] priority = STANDARD_GOOD_HEAD;
//        return getHeadWordByPriority(priority, leftToken, rightToken);
//      }
//    }

//  private static WordToken getHeadWordByPriority(String[] priority, StackToken left, StackToken right) {
//    String leftLabel = left.getLabel().toString(); if (leftLabel.charAt(leftLabel.length()-1) == '_') leftLabel = leftLabel.substring(0, leftLabel.length()-1);
//    String rightLabel = right.getLabel().toString(); if (rightLabel.charAt(rightLabel.length()-1) == '_') rightLabel = rightLabel.substring(0, rightLabel.length()-1);
//    for (String prior : priority) {
//      if (leftLabel.equals(prior)) return left.getHeadWord();
//      else if (rightLabel.equals(prior)) return right.getHeadWord();
//    }
//    for (String bad : STANDARD_BAD_HEAD) {
//      if (leftLabel.equals(bad)) return right.getHeadWord();
//      else if (rightLabel.equals(bad)) return left.getHeadWord();
//    }
//    return left.getHeadWord(); // DEFAULT
//  }
}
