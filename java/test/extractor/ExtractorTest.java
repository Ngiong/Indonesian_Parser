package extractor;

import datatype.Action;
import datatype.StackToken;
import datatype.WordToken;
import org.junit.jupiter.api.Test;
import tree.ParseTree;
import tree.ParseTreeFactory;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ExtractorTest {
  private static final String VALID_1 = "S (SBAR (NP (NP (NP (NNO Pembuat)(NNO PC))(CCN dan)(NP (NNO kompetitor)(NP (NNP Apple)(NNP Computer))))(NNP Dell_,_Inc))(VP (VBT menyatakan)(CP (CCN bahwa)(VP (VBL adalah)(AdjP (VBT menarik)(VP (VBT mengirimkan)(NP (NNO komputer)(RPN (PRR yang)(VP (VBT menjalankan)(NP (NP (NNP Mac)(NNO OS)(NUM X))(NP (NNO milik)(NNP Apple)))))))))))(SYM .))(SBAR (NP (NNP Michael_Dell)(SYM ,)(NP (NP (NNO pendiri)(CCN dan)(NNO chairman))(NP (NNP Dell)(NNP Computer)))(SYM ,))(VP (VP (VBT membuat)(NNO komentar))(CP (CCN ketika)(VP (VBI berbicara)(PP (PPO dengan)(NP (NNP David_Kirkpatrick)(PP (PPO dari)(NP (NNO majalah)(NNP Fortuner)))))))))(SYM .)";
  private static final ParseTreeFactory parseTreeFactory = new ParseTreeFactory();

  @Test
  public void getParsingActionTest() {
    ParseTree pt = parseTreeFactory.getParseTree(VALID_1, true);
    List<Action> actions = ActionExtractor.getParsingActions(pt);
    for (Action action : actions) {
      System.out.println(action);
    }
  }

  @Test
  public void shiftReduceSimTest() {
    ParseTree pt = parseTreeFactory.getParseTree(VALID_1, true);
    List<Action> actions = ActionExtractor.getParsingActions(pt);
    Stack<StackToken> workingMemory = new Stack<>();
    Queue<WordToken> wordQueue = WordTokenExtractor.getWordQueue(pt);

    ShiftReduceSimulator sim = new ShiftReduceSimulator(workingMemory, wordQueue, actions);
    while (sim.hasNextStep()) {
      sim.nextStep();
    }
  }
}
