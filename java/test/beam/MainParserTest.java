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
    MainParser mainParser = new MainParser("models/e01_postag/acc_PTF_19.json", 8, 1);

    Queue<WordToken> wordQueue = new LinkedList<>();

    wordQueue.add(new WordToken("\"", POSTag.SYM));
    wordQueue.add(new WordToken("Saya", POSTag.PRN));
//    wordQueue.add(new WordToken("makan", POSTag.VBT));
//    wordQueue.add(new WordToken("nasi", POSTag.NNO));
    wordQueue.add(new WordToken(",", POSTag.SYM));
    wordQueue.add(new WordToken("\"", POSTag.SYM));
    wordQueue.add(new WordToken("kata", POSTag.VBI));
    wordQueue.add(new WordToken("guru", POSTag.NNO));
    wordQueue.add(new WordToken(".", POSTag.SYM));

    ParseTree pt = mainParser.parse(wordQueue);
    System.out.println(pt.toString());
  }

  @Test
  public void batchParseTest() {
    MainParser mainParser = new MainParser("models/acc_PTF_v3_9.json", 8, 1);
    mainParser.batchParse("corpus/acc_PTF_v2_test.tre", "corpus/result_noncli.tre");
//    mainParser.batchParse("corpus/acc_PTF_v2_train.tre", "corpus/[RES]PTF_v3_train.tre");
  }

  @Test
  public void batchParseMultiModelTest() {
    for (int i = 0; i <= 19; i++) {
      String perceptronFileTemplate = "models/e01_postag/acc_PTF_" + Integer.toString(i) + ".json";
      String treebankFilename = "corpus/acc_PTF_v2_train.tre";
      String outputFileTemplate = "corpus/[RES_" + Integer.toString(i) + "]PTF_v2_train.tre";

      MainParser mainParser = new MainParser(perceptronFileTemplate, 16, 1);
      mainParser.batchParse(treebankFilename, outputFileTemplate);
    }
  }

  @Test
  public void batchParseMultiBeamSize() {
    for (int i = 1; i <= 5; i++) {
      String perceptronFileTemplate = "models/e03_iob_ioe/acc_IOE_9.json";
      String treebankFilename = "corpus/acc_PTF_v2_test.tre";
      String outputFileTemplate = "corpus/[RES_" + Integer.toString(i) + "]IOE_BEAM_v2_test.tre";

      MainParser mainParser = new MainParser(perceptronFileTemplate, i, 1);
      mainParser.batchParse(treebankFilename, outputFileTemplate);
    }
  }
}
