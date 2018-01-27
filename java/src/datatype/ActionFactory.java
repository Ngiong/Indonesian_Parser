package datatype;

public class ActionFactory {
  public Action getSHIFTAction(WordToken wordToken) {
    return new Action(ActionType.SHIFT, wordToken, null);
  }

  public Action getUNARYAction(ConstituentLabel label) {
    return new Action(ActionType.UNARY, null, label);
  }

  public Action getREDUCEAction(ConstituentLabel label) {
    return new Action(ActionType.REDUCE, null, label);
  }

  public Action getFINISHAction() {
    return new Action(ActionType.FINISH, null, null);
  }
}
