package datatype;

public enum Action {
  SHIFT  (ActionType.SHIFT, null),
  FINISH (ActionType.FINISH, null),

  REDUCE_AdjP (ActionType.REDUCE, ConstituentLabel.AdjP),
  REDUCE_AdvP (ActionType.REDUCE, ConstituentLabel.AdvP),
  REDUCE_NP (ActionType.REDUCE, ConstituentLabel.NP),
  REDUCE_PP (ActionType.REDUCE, ConstituentLabel.PP),
  REDUCE_VP (ActionType.REDUCE, ConstituentLabel.VP),
  REDUCE_CP (ActionType.REDUCE, ConstituentLabel.CP),
  REDUCE_RPN (ActionType.REDUCE, ConstituentLabel.RPN),
  REDUCE_S (ActionType.REDUCE, ConstituentLabel.S),
  REDUCE_SQ (ActionType.REDUCE, ConstituentLabel.SQ),
  REDUCE_SBAR (ActionType.REDUCE, ConstituentLabel.SBAR),
  REDUCE_AdjP_ (ActionType.REDUCE, ConstituentLabel.AdjP_),
  REDUCE_AdvP_ (ActionType.REDUCE, ConstituentLabel.AdvP_),
  REDUCE_NP_ (ActionType.REDUCE, ConstituentLabel.NP_),
  REDUCE_PP_ (ActionType.REDUCE, ConstituentLabel.PP_),
  REDUCE_VP_ (ActionType.REDUCE, ConstituentLabel.VP_),
  REDUCE_CP_ (ActionType.REDUCE, ConstituentLabel.CP_),
  REDUCE_RPN_ (ActionType.REDUCE, ConstituentLabel.RPN_),
  REDUCE_S_ (ActionType.REDUCE, ConstituentLabel.S_),
  REDUCE_SQ_ (ActionType.REDUCE, ConstituentLabel.SQ_),
  REDUCE_SBAR_ (ActionType.REDUCE, ConstituentLabel.SBAR_),

  UNARY_AdjP (ActionType.UNARY, ConstituentLabel.AdjP),
  UNARY_AdvP (ActionType.UNARY, ConstituentLabel.AdvP),
  UNARY_NP (ActionType.UNARY, ConstituentLabel.NP),
  UNARY_PP (ActionType.UNARY, ConstituentLabel.PP),
  UNARY_VP (ActionType.UNARY, ConstituentLabel.VP),
  UNARY_CP (ActionType.UNARY, ConstituentLabel.CP),
  UNARY_RPN (ActionType.UNARY, ConstituentLabel.RPN),
  UNARY_S (ActionType.UNARY, ConstituentLabel.S),
  UNARY_SQ (ActionType.UNARY, ConstituentLabel.SQ),
  UNARY_SBAR (ActionType.UNARY, ConstituentLabel.SBAR),
  UNARY_AdjP_ (ActionType.UNARY, ConstituentLabel.AdjP_),
  UNARY_AdvP_ (ActionType.UNARY, ConstituentLabel.AdvP_),
  UNARY_NP_ (ActionType.UNARY, ConstituentLabel.NP_),
  UNARY_PP_ (ActionType.UNARY, ConstituentLabel.PP_),
  UNARY_VP_ (ActionType.UNARY, ConstituentLabel.VP_),
  UNARY_CP_ (ActionType.UNARY, ConstituentLabel.CP_),
  UNARY_RPN_ (ActionType.UNARY, ConstituentLabel.RPN_),
  UNARY_S_ (ActionType.UNARY, ConstituentLabel.S_),
  UNARY_SQ_ (ActionType.UNARY, ConstituentLabel.SQ_),
  UNARY_SBAR_ (ActionType.UNARY, ConstituentLabel.SBAR_);

  private final ActionType actionType;
  private final ConstituentLabel label;

  Action(ActionType actionType, ConstituentLabel label) {
    this.actionType = actionType; this.label = label;
  }

  public ActionType getActionType() {
    return actionType;
  }

  public ConstituentLabel getLabel() {
    return label;
  }

  public static Action get(ActionType actionType, ConstituentLabel label) {
    for (Action action : Action.values()) {
      if (action.actionType == actionType && action.label == label) return action;
    }
    return null;
  }

  public String toString() {
    return actionType + (label != null ? "-" + label : "");
  }
}
