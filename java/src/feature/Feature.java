package feature;

import datatype.Action;

public class Feature {
  private final String featureId;

  public Feature(String featureId) {
    this.featureId = featureId;
  }

  public String getFeatureId() {
    return featureId;
  }

  public String toString() { return featureId; }
}
