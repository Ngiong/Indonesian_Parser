package feature;

public enum FeatureType {
  UNIGRAM(1),
  UNIGRAM_PLUS(1),
  BIGRAM(1),
  TRIGRAM(1);

  FeatureType(int multiplier) {
    this.multiplier = multiplier;
  }

  public int getMultiplier() { return multiplier; }

  private int multiplier;
}
