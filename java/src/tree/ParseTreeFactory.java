package tree;

public class ParseTreeFactory {
  public ParseTree getParseTree(String brackets, boolean usingBinaryNodes) {
    ParseTree pt = new ParseTree(brackets, usingBinaryNodes); pt.makeTree();
    return pt;
  }
}
