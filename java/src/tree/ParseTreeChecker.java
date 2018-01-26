package tree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ParseTreeChecker {
  private static final String[] PT = {
    "NNP", "VBI", "VBT", "NUM", "PPO", "NNO", "SYM", "CCN", "PRN", "ADV", "ADJ", "NEG", "ADK", "PRR", "VBL", "VBP", "ART",
    "PRK", "KUA", "$$$", "UNS", "LBR", "RBR", "VBE", "PRI", "PAR", "INT", "CSN"
  };

  private static final String[] CL = {
    "AdjP", "AdvP", "NP", "PP", "VP", "CP", "RPN", "S", "SQ", "SBAR"
  };

  private static final Set<String> POS_TAGS = new HashSet<>(Arrays.asList(PT));
  private static final Set<String> CONSTITUENT_LABELS = new HashSet<>(Arrays.asList(CL));

  public static final boolean isValidTree(ParseTree pt) {
    if (pt.isLeaf()) return POS_TAGS.contains(pt.getNodeTag());
    else {
      boolean valid = true;
      if (CONSTITUENT_LABELS.contains(pt.getNodeTag())) {
        Iterator<ParseTree> child = pt.getChildren().iterator();
        while (child.hasNext() && valid) valid &= isValidTree(child.next());
      } else {
        valid = false;
      }

      return valid;
    }
  }

  public static final boolean isBinaryTree(ParseTree pt) {
    if (pt.isLeaf()) return true;
    else {
      boolean valid = pt.getChildren().size() <= 2;
      Iterator<ParseTree> child = pt.getChildren().iterator();
      while (child.hasNext() && valid) valid &= isBinaryTree(child.next());

      return valid;
    }
  }
}
