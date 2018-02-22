package feature;

import datatype.Action;
import datatype.StackToken;
import datatype.WordToken;

import java.util.*;

public class FeatureTemplateSet {
  private Set<FeatureTemplate> templates;

  public FeatureTemplateSet() {
    templates = new HashSet<>();
  }

  public FeatureTemplateSet use(FeatureTemplate t) { templates.add(t); return this; }

  public FeatureTemplateSet useAll() {
    FeatureTemplate[] fts = FeatureTemplate.class.getEnumConstants();
    for (FeatureTemplate ft : fts) {
      templates.add(ft);
//      switch (ft.getType()) { case UNIGRAM: case UNIGRAM_PLUS: templates.add(ft); }
    }
    return this;
  }

  public List<Feature> extract(Stack<StackToken> stack, Queue<WordToken> wordQueue) {
    List<Feature> result = new ArrayList<>();
    for (FeatureTemplate t : templates) {
      Feature f = t.extract(stack, wordQueue);
      result.add(f);
    }
    return result;
  }
}
