package extractor;

import datatype.*;
import tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionExtractor {
  private static final ActionFactory actionFactory = new ActionFactory();

  public static List<Action> getParsingActions(ParseTree tree) {
    List<Action> result = new ArrayList<>();
    if (tree.isLeaf()) {
      WordToken wordToken = new WordToken(tree.getWord(), POSTag.valueOf(tree.getNodeTag()));
      result.add(actionFactory.getSHIFTAction(wordToken));
    } else {
      List<List<Action>> childActions = new ArrayList<>();
      for (ParseTree child : tree.getChildren()) childActions.add(getParsingActions(child));
      result = childActions.stream().flatMap(List::stream).collect(Collectors.toList());

      if (tree.getChildren().size() == 1)
        result.add(actionFactory.getUNARYAction(ConstituentLabel.valueOf(tree.getNodeTag())));
      else
        result.add(actionFactory.getREDUCEAction(ConstituentLabel.valueOf(tree.getNodeTag())));
    }

    return result;
  }
}
