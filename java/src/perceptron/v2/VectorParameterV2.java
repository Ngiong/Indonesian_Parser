package perceptron.v2;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import datatype.Action;
import feature.Feature;
import perceptron.ParameterRepresentation;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class VectorParameterV2 implements ParameterRepresentation {
  private static final String CLASS_TAG = "[LEARNING PARAMETER V2]";
  private Map<String, AtomicInteger> parameter;
  private Map<String, Integer> featureIndexes;

  VectorParameterV2() {
    parameter = new HashMap<>();
    featureIndexes = new HashMap<>();
  }

  VectorParameterV2(String perceptronFile) {
    parameter = new HashMap<>();
    featureIndexes = new HashMap<>();
    fromJSON(perceptronFile);
  }

  private String __compressFeature(Feature feature) {
    String[] tokens = feature.toString().split(Feature.DELIMITER);
    for (int i = 1; i < tokens.length; i++) {
      if (!featureIndexes.containsKey(tokens[i])) {
        Integer index = featureIndexes.size(); featureIndexes.put(tokens[i], index);
        tokens[i] = index.toString();
      } else {
        tokens[i] = featureIndexes.get(tokens[i]).toString();
      }
    }
    return String.join(Feature.DELIMITER, tokens);
  }

  public void update(List<Feature> extractedFeatures, Action predicted, Action reference, int learningRate) {
    for (Feature feature : extractedFeatures) {
      String compressedFeature = __compressFeature(feature);

      String incrementFeatureAction = compressedFeature + Feature.DELIMITER + reference.ordinal();
      String punishedFeatureAction = compressedFeature + Feature.DELIMITER + predicted.ordinal();

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
    int multiplier = feature.getTemplate().getType().getMultiplier();
    String featureAction = __compressFeature(feature) + Feature.DELIMITER + action.ordinal();
    if (parameter.containsKey(featureAction)) return multiplier * parameter.get(featureAction).get();
    else return 0;
  }

  private static final String TO_JSON_TAG = "[TO JSON]";
  public void toJSON(String filename) {
    try {
      Gson gson = new Gson();
      FileOutputStream out = new FileOutputStream(filename + ".json");
      JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
      writer.beginObject();

      writer.name("featureIndexes");
      writer.beginObject();
      for (Map.Entry<String, Integer> entry : featureIndexes.entrySet()) {
        writer.name(entry.getKey()).value(entry.getValue());
      }
      writer.endObject();

      writer.name("parameter");
      writer.beginObject();
      for (Map.Entry<String, AtomicInteger> entry : parameter.entrySet()) {
        writer.name(entry.getKey()).value(entry.getValue());
      }
      writer.endObject();

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
        String name = reader.nextName();
        if (name.equals("featureIndexes")) {
          reader.beginObject();
          while (reader.hasNext()) {
            String feature = reader.nextName(); Integer indexValue = reader.nextInt();
            featureIndexes.put(feature, indexValue);
          }
          reader.endObject();
        } else if (name.equals("parameter")) {
          reader.beginObject();
          while (reader.hasNext()) {
            String feature = reader.nextName(); Integer value = reader.nextInt();
            parameter.put(feature, new AtomicInteger(value));
            featureCount++;
          }
          reader.endObject();
        } else {
          reader.skipValue();
        }
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
