package datatype;

public class Action {
  private ActionType actionType;
  private WordToken shiftWordToken;
  private ConstituentLabel label;

  Action(ActionType actionType, WordToken shiftWordToken, ConstituentLabel label) {
    this.actionType = actionType;
    this.shiftWordToken = shiftWordToken;
    this.label = label;
  }

  public String toString() {
    return actionType + (shiftWordToken != null ? " " + shiftWordToken : "") + (label != null ? " " + label : "");
  }
};
