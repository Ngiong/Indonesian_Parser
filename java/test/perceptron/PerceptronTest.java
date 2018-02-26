package perceptron;

import org.junit.jupiter.api.Test;

public class PerceptronTest {
  @Test
  public void perceptronTrainTest() {
    Perceptron p = new Perceptron("corpus/treebank.tre", 1);
//    p.train(5, "models/unigram_");
    p.train(1, null);
  }

  @Test
  public void perceptronSerializeTest() {
    Perceptron p = new Perceptron("corpus/sample.tre", 1);
    p.train(3, null);
    p.serializeToFile("models/sample_1");
  }

  @Test
  public void perceptronLoadBenchmarkTest() {
    long begin = System.currentTimeMillis();
    Perceptron p = new Perceptron();
    p.readFromJSONFile("models/unigram_4_copy.json");
    long end = System.currentTimeMillis();

    System.out.println("Deserializing JSON: " + Double.toString((end - begin) * 0.001) + " s");

    long begin2 = System.currentTimeMillis();
    Perceptron q = new Perceptron();
    p.deserializeFromFile("models/unigram_4_copy.ser");
    long end2 = System.currentTimeMillis();

    System.out.println("Deserializing SER: " + Double.toString((end2 - begin2) * 0.001) + " s");

    /**
     * Deserializing JSON: 9.85 s
     * Deserializing SER: 56.427 s
     */
  }
}
