package perceptron;

import datatype.Action;
import feature.Feature;

import java.io.Serializable;
import java.util.List;

public interface ParameterRepresentation extends Serializable {
  public void update(List<Feature> extractedFeatures, Action predicted, Action reference, int learningRate);

  public int calculate(Feature feature, Action action);

  public void toJSON(String filename);

  public void fromJSON(String filepath);
}
