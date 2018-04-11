package perceptron;

import org.junit.jupiter.api.Test;
import perceptron.v1.Perceptron;
import perceptron.v2.PerceptronV2;
import tree.ParseTreeFactory;

public class PerceptronTest {
  @Test
  public void perceptronTrainTest() {
    PerceptronV2 p = new PerceptronV2("corpus/full_PTF_v2_train.tre", 1);
    p.fromJSON("models/full/FULL_0.json");
    for (int i = 1; i < 9; i++) {
      p.train(1);
      p.toJSON("models/full/FULL_" + Integer.toString(i));
    }
  }

  @Test
  public void perceptronScoreTest() {
    PerceptronV2 p = new PerceptronV2("corpus/full_PTF_v2_train.tre", 1);
    p.fromJSON("models/e03_iob_ioe/acc_IOE_9.json");
    String tree1 = "S (SBAR (PRN Saya)(VP (VBT makan)(NNO nasi)))(SYM .)";
    String tree2 = "S (SBAR (PRN Saya)(CP (VBT makan)(NNO nasi)))(SYM .)";
    String tree3 = "S (SQ (PRN Saya)(VP (VBT makan)(NNO nasi)))(SYM .)";

    ParseTreeFactory ptf = new ParseTreeFactory();
    System.out.println("Tree1 score: " + p.score(ptf.getParseTree(tree1, true)));
    System.out.println("Tree2 score: " + p.score(ptf.getParseTree(tree2, true)));
    System.out.println("Tree3 score: " + p.score(ptf.getParseTree(tree3, true)));
  }
}
