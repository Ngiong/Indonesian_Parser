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

  public Feature extract(Stack<StackToken> workingStack, Queue<WordToken> wordQueue) {
    String featureId = null;

    switch (type) {
      case UNIGRAM: case UNIGRAM_PLUS: featureId = extractUNIGRAM(workingStack, wordQueue); break;
      case BIGRAM: featureId = extractBIGRAM(workingStack, wordQueue); break;
      case TRIGRAM: featureId = extractTRIGRAM(workingStack, wordQueue); break;
    }

    return new Feature(featureId.toUpperCase());
  }

  private String extractUNIGRAM(Stack<StackToken> workingStack, Queue<WordToken> wordQueue) {
    return extractUNIGRAM(workingStack, wordQueue, this.toString());
  }

  private String extractUNIGRAM(Stack<StackToken> workingStack, Queue<WordToken> wordQueue, String unigramTemplate) {
    if (unigramTemplate.charAt(0) == 'S') return __extractFromStack(workingStack, unigramTemplate);
    else return __extractFromQueue(wordQueue, unigramTemplate);
  }

  private String extractBIGRAM(Stack<StackToken> workingStack, Queue<WordToken> wordQueue) {
    String template = this.toString();
    String[] templateTokens = template.split("_");

    String first = extractUNIGRAM(workingStack, wordQueue, templateTokens[0] + "_" + templateTokens[1]);
    String second = extractUNIGRAM(workingStack, wordQueue, templateTokens[2] + "_" + templateTokens[3]);
    return first + "." + second;
  }

  private String extractTRIGRAM(Stack<StackToken> workingStack, Queue<WordToken> wordQueue) {
    String template = this.toString();
    String[] templateTokens = template.split("_");

    String first = extractUNIGRAM(workingStack, wordQueue, templateTokens[0] + "_" + templateTokens[1]);
    String second = extractUNIGRAM(workingStack, wordQueue, templateTokens[2] + "_" + templateTokens[3]);
    String third = extractUNIGRAM(workingStack, wordQueue, templateTokens[4] + "_" + templateTokens[5]);
    return first + "." + second + "." + third;
  }

  private String __extractFromStack(Stack<StackToken> workingStack, String template) {
    String[] templateTokens = template.split("_");
    int idx = Character.getNumericValue(template.charAt(1));
    String result = null;

    StackToken token = null;
    if (workingStack.size() >= idx + 1) // stack size >= template yg diminta (bisa)
      token = workingStack.get(workingStack.size()-1-idx);

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
      result = templateTokens[0];
      for (int i = 1; i < templateTokens.length; i++) {
        switch (templateTokens[i]) {
          case "T": result += "." + token.getHeadWord().getTag().toString(); break;
          case "W": result += "." + token.getHeadWord().getWord(); break;
          case "C":
            if (token.getLabel() == null) result += "." + token.getHeadWord().getTag().toString();
            else result += token.getLabel().toString(); break;
        }
      }
    }
    return result;
  }

  private String __extractFromQueue(Queue<WordToken> wordQueue, String template) {
    String[] templateTokens = template.split("_");
    int idx = Character.getNumericValue(template.charAt(1));
    String result = null;

    WordToken token = null;
    if (wordQueue.size() >= idx + 1) { // queue size >= template yang diminta (bisa)
      Iterator<WordToken> it = wordQueue.iterator();
      for (int i = 0; i < idx+1; i++) token = it.next();
    }
    if (token == null) result = templateTokens[0] + ".NULL";
    else {
      result = templateTokens[0];
      for (int i = 1; i < templateTokens.length; i++) {
        switch (templateTokens[i]) {
          case "W": result += "." + token.getWord(); break;
          case "T": result += "." + token.getTag(); break;
        }
      }
    }
    return result;
  }
}