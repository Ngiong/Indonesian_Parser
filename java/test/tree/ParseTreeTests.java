package tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParseTreeTests {
  private static final String VALID_1 = "S (SBAR (NP (NP (NP (NNO Pembuat)(NNO PC))(CCN dan)(NP (NNO kompetitor)(NP (NNP Apple)(NNP Computer))))(NNP Dell_,_Inc))(VP (VBT menyatakan)(CP (CCN bahwa)(VP (VBL adalah)(AdjP (VBT menarik)(VP (VBT mengirimkan)(NP (NNO komputer)(RPN (PRR yang)(VP (VBT menjalankan)(NP (NP (NNP Mac)(NNO OS)(NUM X))(NP (NNO milik)(NNP Apple)))))))))))(SYM .))(SBAR (NP (NNP Michael_Dell)(SYM ,)(NP (NP (NNO pendiri)(CCN dan)(NNO chairman))(NP (NNP Dell)(NNP Computer)))(SYM ,))(VP (VP (VBT membuat)(NNO komentar))(CP (CCN ketika)(VP (VBI berbicara)(PP (PPO dengan)(NP (NNP David_Kirkpatrick)(PP (PPO dari)(NP (NNO majalah)(NNP Fortuner)))))))))(SYM .)";
  private static final String VALID_2 = "A (B Halo)(C (X X)(X X))(D (X X)(X X))(E (X X)(X X)(X X)(X X)(X X)(X X)(X X)(X X))";
  private static final String INVALID_1 = "S (SBAR (NP (NNP Bush))(VP (VBI bertemu)()(PP (PPO dengan)(NP (NNO al-Maliki)))))(PP (PPO di)(NP (NP (NNO zona)(ADJ hijau))(RPN (PRR yang)(VP (VBP terbentengi)(AdjP (ADV sangat)(ADJ kuat))))))(PP (PPO di)(NP (NP (NNO sebuah)(NNO istana))(RPN (PRR yang)(AdvP (ADK pernah)(VP (VBP digunakan)(PP (PPO oleh)(NNP Saddam_Husein)))))))(SYM .))";
  private static final ParseTreeFactory parseTreeFactory = new ParseTreeFactory();

  @Test
  public void testTreeChecker() {
    ParseTree parseTree;
    parseTree  = parseTreeFactory.getParseTree(VALID_1, false);
    boolean valid = ParseTreeChecker.isValidTree(parseTree);
    assertTrue(valid);

    parseTree = parseTreeFactory.getParseTree(INVALID_1, false);
    boolean invalid = ParseTreeChecker.isValidTree(parseTree);
    assertFalse(invalid);
  }

  @Test
  public void testToString() {
    ParseTree parseTree;
    parseTree = parseTreeFactory.getParseTree(VALID_1, false);
    assertTrue(parseTree.toString().contains(VALID_1));
  }

  @Test
  public void testBinaryParseTree() {
    ParseTree parseTree;
    parseTree = parseTreeFactory.getParseTree(VALID_2, true);
    System.out.println(parseTree.toString());
    assertTrue(ParseTreeChecker.isBinaryTree(parseTree));
  }
}
