package perceptron;

import org.junit.jupiter.api.Test;
import perceptron.v1.Perceptron;
import perceptron.v2.PerceptronV2;

public class PerceptronTest {
  @Test
  public void perceptronTrainTest() {
//    Perceptron p = new Perceptron("corpus/treebank.tre", 1);
//    p.train(1, "models/all_nohead_fixed_");

//    PerceptronV2 q = new PerceptronV2("corpus/treebank.tre", 1);
//    q.train(1, "models/all_nohead_v2_fixed_");

    Perceptron p = new Perceptron("models/all_nohead_fixed_0.json");
    PerceptronV2 q = new PerceptronV2("models/all_nohead_v2_fixed_0.json");
  }

  @Test
  public void perceptronSerializeTest() {
    PerceptronV2 q = new PerceptronV2("corpus/sample_treebank.tre", 1);
//    q.fromJSON("test_v2.json");
    q.train(1, null);
    q.toJSON("test_v2");

    Perceptron p = new Perceptron("corpus/sample_treebank.tre", 1);
//    p.fromJSON("test_v1.json");
    p.train(1, null);
    p.toJSON("test_v1");


//    p.toJSON("models/test_serialize_1_v2");
//    p.train(1, null);
////    p.toJSON("test_serialize_2");
//
//    Perceptron q = new Perceptron("corpus/sample_treebank.tre", 1);
//    q.fromJSON("models/test_serialize_1_v2.json");
//    q.train(1, null);
  }

  @Test
  public void perceptronLoadBenchmarkTest() {
//    long begin = System.currentTimeMillis();
//    Perceptron p = new Perceptron();
//    p.fromJSON("models/unigram_4_copy.json");
//    long end = System.currentTimeMillis();
//
//    System.out.println("Deserializing JSON: " + Double.toString((end - begin) * 0.001) + " s");
//
//    long begin2 = System.currentTimeMillis();
//    Perceptron q = new Perceptron();
//    p.deserializeFromFile("models/unigram_4_copy.ser");
//    long end2 = System.currentTimeMillis();
//
//    System.out.println("Deserializing SER: " + Double.toString((end2 - begin2) * 0.001) + " s");

    /**
     * Deserializing JSON: 9.85 s
     * Deserializing SER: 56.427 s
     */
  }
}
