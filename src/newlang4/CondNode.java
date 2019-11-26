package newlang4;

import java.util.HashSet;
import java.util.Set;

public class CondNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.addAll(ExprNode.getFirstSet());
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }

  public static Node isMatch(Environment env, LexicalUnit first) {
    return null;
  }
}
