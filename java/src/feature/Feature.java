package feature;

import datatype.Action;

public class Feature {
  private final String featureId;
  private final FeatureTemplate template;

  public Feature(String featureId, FeatureTemplate template) {
    this.featureId = featureId;
    this.template = template;
  }

  public String getFeatureId() {
    return featureId;
  }

  public String toString() { return Integer.toString(template.ordinal()) + "." + featureId; }

  public String featureActionString(Action action) { return this.toString() + "." + action.toString(); }
}
