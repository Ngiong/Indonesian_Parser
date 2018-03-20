package beam;

import datatype.POSTag;
import datatype.WordToken;
import org.junit.jupiter.api.Test;
import tree.ParseTree;

import java.util.LinkedList;
import java.util.Queue;

public class MainParserTest {
  @Test
  public void parseTest() {
    MainParser mainParser = new MainParser("models/all_nohead_v2_fixed_0.json", 3);

    Queue<WordToken> wordQueue = new LinkedList<>();

    wordQueue.add(new WordToken("Saya", POSTag.PRN));
    wordQueue.add(new WordToken("duduk", POSTag.VBT));
    wordQueue.add(new WordToken("di", POSTag.PPO));
    wordQueue.add(new WordToken("kursi", POSTag.NNO));
    wordQueue.add(new WordToken("goyang", POSTag.ADJ));
    wordQueue.add(new WordToken(".", POSTag.SYM));

    ParseTree pt = mainParser.parse(wordQueue);
    System.out.println(pt.toString());
  }

  @Test
  public void batchParseTest() {
    MainParser mainParser = new MainParser("models/e03_iob_ioe/acc_IOE_9.json", 16);
    mainParser.batchParse("corpus/acc_PTF_v2_test.tre", "corpus/[RES]IOE_v2_test.tre");
    mainParser.batchParse("corpus/acc_PTF_v2_train.tre", "corpus/[RES]IOE_v2_train.tre");
  }

  @Test
  public void batchParseMultiModelTest() {
    for (int i = 0; i <= 19; i++) {
      String perceptronFileTemplate = "models/e01_postag/acc_PTF_" + Integer.toString(i) + ".json";
      String treebankFilename = "corpus/acc_PTF_v2_train.tre";
      String outputFileTemplate = "corpus/[RES_" + Integer.toString(i) + "]PTF_v2_train.tre";

      MainParser mainParser = new MainParser(perceptronFileTemplate, 16);
      mainParser.batchParse(treebankFilename, outputFileTemplate);
    }
  }

  @Test
  public void batchParseMultiBeamSize() {
    for (int i = 1; i <= 10; i++) {
      String perceptronFileTemplate = "models/e03_iob_ioe/acc_IOE_9.json";
      String treebankFilename = "corpus/acc_PTF_v2_test.tre";
      String outputFileTemplate = "corpus/[RES_" + Integer.toString(i) + "]IOE_BEAM_v2_test.tre";

      MainParser mainParser = new MainParser(perceptronFileTemplate, i);
      mainParser.batchParse(treebankFilename, outputFileTemplate);
    }
  }
}
