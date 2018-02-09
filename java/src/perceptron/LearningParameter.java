package perceptron;

import datatype.Action;
import feature.Feature;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LearningParameter implements Serializable {
  private static final String CLASS_TAG = "[LEARNING PARAMETER]";
  private Map<String, AtomicInteger> parameter;

  LearningParameter() {
    parameter = new HashMap<>();
  }

  public void update(List<Feature> extractedFeatures, Action predicted, Action reference, int learningRate) {
    for (Feature feature : extractedFeatures) {
      String incrementFeatureAction = feature.featureActionString(reference);
      String punishedFeatureAction = feature.featureActionString(predicted);

      if (!parameter.containsKey(incrementFeatureAction))
        parameter.put(incrementFeatureAction, new AtomicInteger(0));
      if (!parameter.containsKey(punishedFeatureAction))
        parameter.put(punishedFeatureAction, new AtomicInteger(0));

      AtomicInteger incrementValue = parameter.get(incrementFeatureAction);
      incrementValue.addAndGet(learningRate);

      AtomicInteger punishedValue = parameter.get(punishedFeatureAction);
      punishedValue.addAndGet(-learningRate);
    }
  }

  public int calculate(Feature feature, Action action) {
    String featureAction = feature.toString() + "." + action.toString();
    if (parameter.containsKey(featureAction)) return parameter.get(featureAction).get();
    else return 0;
  }
}
