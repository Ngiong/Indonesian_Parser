package perceptron;

import org.junit.jupiter.api.Test;
import perceptron.v1.Perceptron;
import perceptron.v2.PerceptronV2;

public class PerceptronTest {
  @Test
  public void perceptronTrainTest() {
    PerceptronV2 p = new PerceptronV2("corpus/acc_PTF_v2_train.tre", 1);
//    p.fromJSON("models/e01_postag/acc_PTF_9.json");
    p.train(10);
    p.toJSON("models/e03_iob_ioe/acc_IOE_9");
  }
}
