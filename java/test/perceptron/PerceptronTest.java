package perceptron;

import org.junit.jupiter.api.Test;

public class PerceptronTest {
  @Test
  public void perceptronTrainTest() {
    Perceptron p = new Perceptron("sample_treebank.tre", 1);
    p.readFromFile("perceptron-0.json"); p.train(1);

    Perceptron q = new Perceptron("sample_treebank.tre", 1);
    q.train(2);

  }

  @Test
  public void perceptronPredictTest() {

  }
}
