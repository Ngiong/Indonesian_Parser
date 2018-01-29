package feature;

import datatype.Action;

public class Feature {
  private final String featureId;
  private final Action action;

  public Feature(String featureId, Action action) {
    this.featureId = featureId;
    this.action = action;
  }

  public String getFeatureId() {
    return featureId;
  }

  public Action getAction() {
    return action;
  }
}
