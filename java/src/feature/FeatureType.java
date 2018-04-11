package feature;

public enum FeatureType {
  UNIGRAM(1),
  UNIGRAM_PLUS(1),
  BIGRAM(2),
  TRIGRAM(3);

  FeatureType(int multiplier) {
    this.multiplier = multiplier;
  }

  public int getMultiplier() { return multiplier; }

  private int multiplier;
}
