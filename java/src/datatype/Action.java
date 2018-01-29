package datatype;

public class Action {
  private ActionType actionType;
  private ConstituentLabel label;

  Action(ActionType actionType, ConstituentLabel label) {
    this.actionType = actionType;
    this.label = label;
  }

  public String toString() {
    return actionType + (label != null ? " " + label : "");
  }

  public ActionType getActionType() {
    return actionType;
  }

  public ConstituentLabel getLabel() {
    return label;
  }
};
