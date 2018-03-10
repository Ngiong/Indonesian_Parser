package perceptron;

import org.junit.jupiter.api.Test;
import perceptron.v1.Perceptron;
import perceptron.v2.PerceptronV2;

public class PerceptronTest {
  @Test
  public void perceptronTrainTest() {
    PerceptronV2 p = new PerceptronV2("corpus/accepted_2_ptf_train.tre", 1);
    p.train(20, "models/e01_postag/accepted_2_ptf_");
  }
}
