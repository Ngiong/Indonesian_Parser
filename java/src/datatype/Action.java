package datatype;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Action {
  private ActionType actionType;
  private ConstituentLabel label;
  private static Map<Pair<ActionType, ConstituentLabel>, Action> allActions = __initAvailableActions();

  private Action(ActionType actionType, ConstituentLabel label) {
    this.actionType = actionType;
    this.label = label;
  }

  public String toString() {
    return actionType + (label != null ? "-" + label : "");
  }

  public boolean equals(Action a) { return actionType == a.actionType &&  label == a.label; }

  public ActionType getActionType() {
    return actionType;
  }

  public ConstituentLabel getLabel() {
    return label;
  }

  public static List<Action> getAllActions() {
    return new ArrayList<>(allActions.values());
  }

  public static Action get(ActionType actionType, ConstituentLabel label) {
    return allActions.get(new Pair(actionType, label));
  }

  private static Map<Pair<ActionType, ConstituentLabel>, Action> __initAvailableActions() {
    Map<Pair<ActionType, ConstituentLabel>, Action> result = new HashMap<>();

    // SHIFT & FINISH Action
    result.put(new Pair(ActionType.SHIFT, null), new Action(ActionType.SHIFT, null));
    result.put(new Pair(ActionType.FINISH, null), new Action(ActionType.FINISH, null));

    // REDUCE & UNARY Action
    for (ConstituentLabel label : ConstituentLabel.values()) {
      result.put(new Pair(ActionType.REDUCE, label), new Action(ActionType.REDUCE, label));
      result.put(new Pair(ActionType.UNARY, label), new Action(ActionType.UNARY, label));
    }

    return result;
  }
};
