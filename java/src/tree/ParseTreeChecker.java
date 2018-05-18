package tree;

import datatype.ConstituentLabel;
import datatype.POSTag;

import java.util.*;
import java.util.stream.Collectors;

public class ParseTreeChecker {
  private static final List<String> PT = Arrays.stream(POSTag.values()).map(Object::toString).collect(Collectors.toList());
  private static final List<String> CL = Arrays.stream(ConstituentLabel.values()).map(Object::toString).collect(Collectors.toList());
  private static final Set<String> POS_TAGS = new HashSet<>(PT);
  private static final Set<String> CONSTITUENT_LABELS = new HashSet<>(CL);

  public static boolean isValidTree(ParseTree pt) {
    if (pt.isLeaf()) return POS_TAGS.contains(pt.getNodeTag());
    else {
      boolean valid = true;
      if (CONSTITUENT_LABELS.contains(pt.getNodeTag())) {
        Iterator<ParseTree> child = pt.getChildren().iterator();
        while (child.hasNext() && valid) valid &= isValidTree(child.next());
      } else {
        valid = false;
      }
      if (!valid) System.out.println(pt.toString());
      return valid;
    }
  }

  public static boolean isBinaryTree(ParseTree pt) {
    if (pt.isLeaf()) return true;
    else {
      boolean valid = pt.getChildren().size() <= 2;
      Iterator<ParseTree> child = pt.getChildren().iterator();
      while (child.hasNext() && valid) valid &= isBinaryTree(child.next());

      return valid;
    }
  }
}
