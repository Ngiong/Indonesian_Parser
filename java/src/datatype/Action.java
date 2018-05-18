package datatype;

public enum Action {
//  Versi Korpus INACL
//  SHIFT  (ActionType.SHIFT, null),
//  FINISH (ActionType.FINISH, null),
//
//  REDUCE_AdjP (ActionType.REDUCE, ConstituentLabel.AdjP),
//  REDUCE_AdvP (ActionType.REDUCE, ConstituentLabel.AdvP),
//  REDUCE_NP (ActionType.REDUCE, ConstituentLabel.NP),
//  REDUCE_PP (ActionType.REDUCE, ConstituentLabel.PP),
//  REDUCE_VP (ActionType.REDUCE, ConstituentLabel.VP),
//  REDUCE_CP (ActionType.REDUCE, ConstituentLabel.CP),
//  REDUCE_RPN (ActionType.REDUCE, ConstituentLabel.RPN),
//  REDUCE_S (ActionType.REDUCE, ConstituentLabel.S),
//  REDUCE_SQ (ActionType.REDUCE, ConstituentLabel.SQ),
//  REDUCE_SBAR (ActionType.REDUCE, ConstituentLabel.SBAR),
//  REDUCE_AdjP_ (ActionType.REDUCE, ConstituentLabel.AdjP_),
//  REDUCE_AdvP_ (ActionType.REDUCE, ConstituentLabel.AdvP_),
//  REDUCE_NP_ (ActionType.REDUCE, ConstituentLabel.NP_),
//  REDUCE_PP_ (ActionType.REDUCE, ConstituentLabel.PP_),
//  REDUCE_VP_ (ActionType.REDUCE, ConstituentLabel.VP_),
//  REDUCE_CP_ (ActionType.REDUCE, ConstituentLabel.CP_),
//  REDUCE_RPN_ (ActionType.REDUCE, ConstituentLabel.RPN_),
//  REDUCE_S_ (ActionType.REDUCE, ConstituentLabel.S_),
//  REDUCE_SQ_ (ActionType.REDUCE, ConstituentLabel.SQ_),
//  REDUCE_SBAR_ (ActionType.REDUCE, ConstituentLabel.SBAR_),
//
//  UNARY_AdjP (ActionType.UNARY, ConstituentLabel.AdjP),
//  UNARY_AdvP (ActionType.UNARY, ConstituentLabel.AdvP),
//  UNARY_NP (ActionType.UNARY, ConstituentLabel.NP),
//  UNARY_PP (ActionType.UNARY, ConstituentLabel.PP),
//  UNARY_VP (ActionType.UNARY, ConstituentLabel.VP),
//  UNARY_CP (ActionType.UNARY, ConstituentLabel.CP),
//  UNARY_RPN (ActionType.UNARY, ConstituentLabel.RPN),
//  UNARY_S (ActionType.UNARY, ConstituentLabel.S),
//  UNARY_SQ (ActionType.UNARY, ConstituentLabel.SQ),
//  UNARY_SBAR (ActionType.UNARY, ConstituentLabel.SBAR);

//  Versi IDN-Treebank
  SHIFT  (ActionType.SHIFT, null),
  FINISH (ActionType.FINISH, null),

  REDUCE_ROOT (ActionType.REDUCE, ConstituentLabel.ROOT),
  REDUCE_FRAG (ActionType.REDUCE, ConstituentLabel.FRAG),
  REDUCE_WHNP (ActionType.REDUCE, ConstituentLabel.WHNP),
  REDUCE_INTJ (ActionType.REDUCE, ConstituentLabel.INTJ),
  REDUCE_WHADVP (ActionType.REDUCE, ConstituentLabel.WHADVP),
  REDUCE_LRB (ActionType.REDUCE, ConstituentLabel.LRB),
  REDUCE_RRB (ActionType.REDUCE, ConstituentLabel.RRB),
  REDUCE_CONJP (ActionType.REDUCE, ConstituentLabel.CONJP),
  REDUCE_X (ActionType.REDUCE, ConstituentLabel.X),
  REDUCE_S (ActionType.REDUCE, ConstituentLabel.S),
  REDUCE_SQ (ActionType.REDUCE, ConstituentLabel.SQ),
  REDUCE_SBAR (ActionType.REDUCE, ConstituentLabel.SBAR),
  REDUCE_SBARQ (ActionType.REDUCE, ConstituentLabel.SBARQ),
  REDUCE_SINV (ActionType.REDUCE, ConstituentLabel.SINV),
  REDUCE_ADJP (ActionType.REDUCE, ConstituentLabel.ADJP),
  REDUCE_PP (ActionType.REDUCE, ConstituentLabel.PP),
  REDUCE_ADVP (ActionType.REDUCE, ConstituentLabel.ADVP),
  REDUCE_NP (ActionType.REDUCE, ConstituentLabel.NP),
  REDUCE_VP (ActionType.REDUCE, ConstituentLabel.VP),
  REDUCE_PRN (ActionType.REDUCE, ConstituentLabel.PRN),
  REDUCE_UCP (ActionType.REDUCE, ConstituentLabel.UCP),
  REDUCE_NAC (ActionType.REDUCE, ConstituentLabel.NAC),
  REDUCE_QP (ActionType.REDUCE, ConstituentLabel.QP),
  REDUCE_S_ (ActionType.REDUCE, ConstituentLabel.S_),
  REDUCE_SQ_ (ActionType.REDUCE, ConstituentLabel.SQ_),
  REDUCE_SBAR_ (ActionType.REDUCE, ConstituentLabel.SBAR_),
  REDUCE_SBARQ_ (ActionType.REDUCE, ConstituentLabel.SBARQ_),
  REDUCE_SINV_ (ActionType.REDUCE, ConstituentLabel.SINV_),
  REDUCE_ADJP_ (ActionType.REDUCE, ConstituentLabel.ADJP_),
  REDUCE_PP_ (ActionType.REDUCE, ConstituentLabel.PP_),
  REDUCE_ADVP_ (ActionType.REDUCE, ConstituentLabel.ADVP_),
  REDUCE_NP_ (ActionType.REDUCE, ConstituentLabel.NP_),
  REDUCE_VP_ (ActionType.REDUCE, ConstituentLabel.VP_),
  REDUCE_PRN_ (ActionType.REDUCE, ConstituentLabel.PRN_),
  REDUCE_UCP_ (ActionType.REDUCE, ConstituentLabel.UCP_),
  REDUCE_NAC_ (ActionType.REDUCE, ConstituentLabel.NAC_),
  REDUCE_QP_ (ActionType.REDUCE, ConstituentLabel.QP_),

  UNARY_ROOT (ActionType.UNARY, ConstituentLabel.ROOT),
  UNARY_FRAG (ActionType.UNARY, ConstituentLabel.FRAG),
  UNARY_WHNP (ActionType.UNARY, ConstituentLabel.WHNP),
  UNARY_INTJ (ActionType.UNARY, ConstituentLabel.INTJ),
  UNARY_WHADVP (ActionType.UNARY, ConstituentLabel.WHADVP),
  UNARY_LRB (ActionType.UNARY, ConstituentLabel.LRB),
  UNARY_RRB (ActionType.UNARY, ConstituentLabel.RRB),
  UNARY_CONJP (ActionType.UNARY, ConstituentLabel.CONJP),
  UNARY_X (ActionType.UNARY, ConstituentLabel.X),
  UNARY_S (ActionType.UNARY, ConstituentLabel.S),
  UNARY_SQ (ActionType.UNARY, ConstituentLabel.SQ),
  UNARY_SBAR (ActionType.UNARY, ConstituentLabel.SBAR),
  UNARY_SBARQ (ActionType.UNARY, ConstituentLabel.SBARQ),
  UNARY_SINV (ActionType.UNARY, ConstituentLabel.SINV),
  UNARY_ADJP (ActionType.UNARY, ConstituentLabel.ADJP),
  UNARY_PP (ActionType.UNARY, ConstituentLabel.PP),
  UNARY_ADVP (ActionType.UNARY, ConstituentLabel.ADVP),
  UNARY_NP (ActionType.UNARY, ConstituentLabel.NP),
  UNARY_VP (ActionType.UNARY, ConstituentLabel.VP),
  UNARY_PRN (ActionType.UNARY, ConstituentLabel.PRN),
  UNARY_UCP (ActionType.UNARY, ConstituentLabel.UCP),
  UNARY_NAC (ActionType.UNARY, ConstituentLabel.NAC),
  UNARY_QP (ActionType.UNARY, ConstituentLabel.QP);

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
