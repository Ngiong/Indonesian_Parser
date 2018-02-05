package feature;

import datatype.*;

import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

public enum FeatureTemplate {
  // unigrams
  S0_T_C(FeatureType.UNIGRAM), S0_W_C(FeatureType.UNIGRAM),
  S1_T_C(FeatureType.UNIGRAM), S1_W_C(FeatureType.UNIGRAM),
  S2_T_C(FeatureType.UNIGRAM), S2_W_C(FeatureType.UNIGRAM),
  S3_T_C(FeatureType.UNIGRAM), S3_W_C(FeatureType.UNIGRAM),
  Q0_W_T(FeatureType.UNIGRAM), Q1_W_T(FeatureType.UNIGRAM),
  Q2_W_T(FeatureType.UNIGRAM), Q3_W_T(FeatureType.UNIGRAM),

  S0L_W_C(FeatureType.UNIGRAM_PLUS), S0R_W_C(FeatureType.UNIGRAM_PLUS), S0_U_W_C(FeatureType.UNIGRAM_PLUS),
  S1L_W_C(FeatureType.UNIGRAM_PLUS), S1R_W_C(FeatureType.UNIGRAM_PLUS), S1_U_W_C(FeatureType.UNIGRAM_PLUS),

  // bigram
  S0_W_S1_W(FeatureType.BIGRAM), S0_W_S1_C(FeatureType.BIGRAM), S0_C_S1_W(FeatureType.BIGRAM), S0_C_S1_C(FeatureType.BIGRAM),
  S0_W_Q0_W(FeatureType.BIGRAM), S0_W_Q0_T(FeatureType.BIGRAM), S0_C_Q0_W(FeatureType.BIGRAM), S0_C_Q0_T(FeatureType.BIGRAM),
  Q0_W_Q1_W(FeatureType.BIGRAM), Q0_W_Q1_T(FeatureType.BIGRAM), Q0_T_Q1_W(FeatureType.BIGRAM), Q0_T_Q1_T(FeatureType.BIGRAM),
  S1_W_Q0_W(FeatureType.BIGRAM), S1_W_Q0_T(FeatureType.BIGRAM), S1_C_Q0_W(FeatureType.BIGRAM), S1_C_Q0_T(FeatureType.BIGRAM),

  // trigram
  S0_C_S1_C_S2_C(FeatureType.TRIGRAM), S0_W_S1_C_S2_C(FeatureType.TRIGRAM), S0_C_S1_W_S2_C(FeatureType.TRIGRAM), S0_C_S1_C_S2_W(FeatureType.TRIGRAM),
  S0_C_S1_C_Q0_T(FeatureType.TRIGRAM), S0_W_S1_C_Q0_T(FeatureType.TRIGRAM), S0_C_S1_W_Q0_T(FeatureType.TRIGRAM), S0_C_S1_C_Q0_W(FeatureType.TRIGRAM);

  private FeatureType type;

  FeatureTemplate(FeatureType ft) { type = ft; }

  public FeatureType getType() { return type; }

  public Feature extract(Stack<StackToken> workingStack, Queue<WordToken> wordQueue, Action action) {
    String featureId = null;

    switch (type) {
      case UNIGRAM: case UNIGRAM_PLUS: featureId = extractUNIGRAM(workingStack, wordQueue); break;
      case BIGRAM: featureId = extractBIGRAM(workingStack, wordQueue); break;
      case TRIGRAM: featureId = extractTRIGRAM(workingStack, wordQueue); break;
    }

    return new Feature(featureId, action);
  }

  private String extractUNIGRAM(Stack<StackToken> workingStack, Queue<WordToken> wordQueue) {
    String template = this.toString();
    int idx = Character.getNumericValue(template.charAt(1));
    String result = null;

    String[] templateTokens = template.split("_");
    String first = null, second = null;

    if (templateTokens[0].contains("S")) {
      StackToken token = null;
      if (workingStack.size() >= idx + 1) {
        token = workingStack.get(workingStack.size()-1-idx);
      }

      if (type == FeatureType.UNIGRAM_PLUS && token != null) {
        if (token.getPrevs() == null) token = null;
        else {
          char move = template.charAt(2);
          switch (move) {
            case 'L': case 'U': token = token.getPrevs()[0]; break;
            case 'R': token = (token.getPrevs().length == 2) ? token.getPrevs()[1] : null; break;
          }
        }
      }

      if (token == null) result = templateTokens[0] + ".NULL";
      else {
        first = (templateTokens[1].equals("T") ? token.getHeadWord().getTag().toString() : token.getHeadWord().getWord());
        second = (token.getLabel() == null) ? token.getHeadWord().getTag().toString() : token.getLabel().toString();
        result = templateTokens[0] + "." + first + "." + second;
      }

    } else { // "Q"
      WordToken token = null;
      if (wordQueue.size() >= idx + 1) {
        Iterator<WordToken> it = wordQueue.iterator();
        for (int i = 0; i < idx+1; i++) token = it.next();
      }
      if (token == null) result = templateTokens[0] + ".NULL";
      else result = templateTokens[0] + "." + token.getWord() + "." + token.getTag();
    }

    return result;
  }

  private String extractBIGRAM(Stack<StackToken> workingStack, Queue<WordToken> wordQueue) {
    return null;
  }

  private String extractTRIGRAM(Stack<StackToken> workingStack, Queue<WordToken> wordQueue) {
    return null;
  }
}


//    if (template.charAt(0) == 'S') {
//      StackToken token = null;
//      if (workingStack.size() >= idx + 1) token = workingStack.get(workingStack.size()-1-idx);
//      else if (type == FeatureType.UNIGRAM_PLUS) return template.substring(0, 3) + ".NULL";
//      else return template.substring(0, 2) + ".NULL";
//
//      if (type == FeatureType.UNIGRAM_PLUS && token.getPrevs() != null) {
//        char move = template.charAt(2);
//        switch (move) {
//          case 'L': case 'U': token = token.getPrevs()[0]; break;
//          case 'R': token = (token.getPrevs().length == 2) ? token.getPrevs()[1] : null; break;
//        }
//        WordToken word = token.getHeadWord(); Label label = token.getLabel();
//        if (label == null) label = word.getTag();
//        result = template.substring(0, 3) + "." + word.getWord() + "." + label.toString();
//
//      } else if (type == FeatureType.UNIGRAM_PLUS && token.getPrevs() == null) {
//        result = template.substring(0, 3) + ".NULL";
//
//      } else { // type == UNIGRAM
//        WordToken word = token.getHeadWord(); Label label = token.getLabel();
//        if (label == null) label = word.getTag();
//        result = template.substring(0, 3) + "." + word.getWord() + "." + label.toString();
//      }
//
//    } else if (template.charAt(0) == 'Q') {
//      WordToken token = null;
//      if (wordQueue.size() < idx + 1) token = null;
//      else { // size >= idx + 1
//        Iterator<WordToken> it = wordQueue.iterator();
//        for (int i = 0; i < idx+1; i++) token = it.next();
//      }
//
//      if (token == null) result = template.substring(0, 2) + ".NULL";
//      else result = template.substring(0, 2) + "." + token.getWord() + "." + token.getTag();
//    }