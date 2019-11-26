package newlang4;

import java.util.HashSet;
import java.util.Set;

public class SubstNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.addAll(LeftvarNode.getFirstSet());
  }

  private SubstNode(Environment env) {
    super.env = env;
  }

  public static Node isMatch(Environment env, LexicalUnit first) {
    return null;
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }
}
