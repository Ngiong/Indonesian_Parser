package datatype;

public enum ActionType {
  SHIFT(4),
  REDUCE(3),
  UNARY(2),
  FINISH(1);

  private int priority;

  ActionType(int priority) { this.priority = priority; }

  public boolean isGreaterThan(ActionType a) { return priority > a.priority; }
}
