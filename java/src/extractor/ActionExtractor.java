package extractor;

import datatype.*;
import tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionExtractor {
  public static List<Action> getParsingActions(ParseTree tree, boolean isRoot) {
    List<Action> result = new ArrayList<>();
    if (tree.isLeaf()) {
      result.add(Action.SHIFT);
    } else {
      List<List<Action>> childActions = new ArrayList<>();
      for (ParseTree child : tree.getChildren()) childActions.add(getParsingActions(child, false));
      result = childActions.stream().flatMap(List::stream).collect(Collectors.toList());

      if (tree.getChildren().size() == 1)
        result.add(Action.get(ActionType.UNARY, ConstituentLabel.valueOf(tree.getNodeTag())));
      else
        result.add(Action.get(ActionType.REDUCE, ConstituentLabel.valueOf(tree.getNodeTag())));
    }

    if (isRoot) result.add(Action.FINISH);
    return result;
  }
}
