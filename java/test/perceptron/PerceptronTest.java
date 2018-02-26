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
}
