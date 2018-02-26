package beam;

import datatype.POSTag;
import datatype.WordToken;
import org.junit.jupiter.api.Test;
import perceptron.Perceptron;

import java.util.LinkedList;
import java.util.Queue;

public class MainParserTest {
  @Test
  public void parseTest() {
    MainParser mainParser = new MainParser("models/unigram_4.json", 1);

    Queue<WordToken> wordQueue = new LinkedList<>();

    wordQueue.add(new WordToken("Saya", POSTag.PRN));
    wordQueue.add(new WordToken("memasak", POSTag.VBT));
    wordQueue.add(new WordToken("nasi", POSTag.NNO));
    wordQueue.add(new WordToken(".", POSTag.SYM));

//    wordQueue.add(new WordToken("Saya", POSTag.PRN));
//    wordQueue.add(new WordToken("makan", POSTag.VBT));
//    wordQueue.add(new WordToken("nasi", POSTag.NNO));
//    wordQueue.add(new WordToken("goreng", POSTag.VBT));
//    wordQueue.add(new WordToken("di", POSTag.PPO));
//    wordQueue.add(new WordToken("warung", POSTag.NNO));
//    wordQueue.add(new WordToken("depan", POSTag.NNO));
//    wordQueue.add(new WordToken("kemarin", POSTag.NNO));
//    wordQueue.add(new WordToken("malam", POSTag.NNO));
//    wordQueue.add(new WordToken(".", POSTag.SYM));

//    wordQueue.add(new WordToken("Mahasiswa", POSTag.NNO));
//    wordQueue.add(new WordToken("dalam", POSTag.PPO));
//    wordQueue.add(new WordToken("kelas", POSTag.NNO));
//    wordQueue.add(new WordToken("itu", POSTag.ART));
//    wordQueue.add(new WordToken("gemar", POSTag.VBT));
//    wordQueue.add(new WordToken("membaca", POSTag.VBT));
//    wordQueue.add(new WordToken(".", POSTag.SYM));


    mainParser.parse(wordQueue);
  }
}
