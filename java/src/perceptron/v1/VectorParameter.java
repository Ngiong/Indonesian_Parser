package perceptron.v1;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import datatype.Action;
import feature.Feature;
import perceptron.ParameterRepresentation;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class VectorParameter implements ParameterRepresentation {
  private static final String CLASS_TAG = "[VECTOR PARAMETER]";
  private Map<String, AtomicInteger> parameter;

  VectorParameter() {
    parameter = new HashMap<>();
  }

  VectorParameter(String perceptronFile) {
    parameter = new HashMap<>();
    fromJSON(perceptronFile);
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
    String featureAction = feature.toString() + Feature.DELIMITER + action.toString();
    if (parameter.containsKey(featureAction)) return parameter.get(featureAction).get();
    else return 0;
  }

  private static final String TO_JSON_TAG = "[TO JSON]";
  public void toJSON(String filename) {
    try {
      Gson gson = new Gson();
      FileOutputStream out = new FileOutputStream(filename + ".json");
      JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
      writer.beginObject();
      for (Map.Entry<String, AtomicInteger> entry : parameter.entrySet()) {
        writer.name(entry.getKey()).value(entry.getValue());
      }
      writer.endObject();
      writer.close();

    } catch (Exception e) {
      System.err.println(CLASS_TAG + TO_JSON_TAG + e.getMessage());
      e.printStackTrace();
    }
  }

  private static final String FROM_JSON_TAG = "[FROM JSON]";
  public void fromJSON(String filepath) {
    long begin = System.currentTimeMillis();
    long featureCount = 0;
    try {
      Gson gson = new Gson();
      FileInputStream in = new FileInputStream(filepath);
      JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
      reader.beginObject();
      while (reader.hasNext()) {
        String feature = reader.nextName(); Integer value = reader.nextInt();
        parameter.put(feature, new AtomicInteger(value));
        featureCount++;
      }
      reader.endObject();
      reader.close();

    } catch (Exception e) {
      System.err.println(CLASS_TAG + FROM_JSON_TAG + e.getMessage());
      e.printStackTrace();
    }
    long end = System.currentTimeMillis();
    double time = 0.001 * (end - begin);
    System.out.println(CLASS_TAG + FROM_JSON_TAG + "Perceptron load time: " + time + " s (# Features: " + featureCount + ")");
  }
}
