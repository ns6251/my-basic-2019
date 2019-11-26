package newlang4;

import java.util.HashSet;
import java.util.Set;

public class ExprListNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.addAll(ExprNode.getFirstSet());
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }
}
