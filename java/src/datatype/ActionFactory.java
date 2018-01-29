package datatype;

public class ActionFactory {
  public Action getSHIFTAction() {
    return new Action(ActionType.SHIFT, null);
  }

  public Action getUNARYAction(ConstituentLabel label) {
    return new Action(ActionType.UNARY, label);
  }

  public Action getREDUCEAction(ConstituentLabel label) {
    return new Action(ActionType.REDUCE, label);
  }

  public Action getFINISHAction() {
    return new Action(ActionType.FINISH, null);
  }
}
