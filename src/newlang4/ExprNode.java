package newlang4;

import java.util.HashSet;
import java.util.Set;

public class ExprNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.add(LexicalType.SUB);
    firstSet.add(LexicalType.LP);
    firstSet.add(LexicalType.NAME);
    firstSet.add(LexicalType.INTVAL);
    firstSet.add(LexicalType.DOUBLEVAL);
    firstSet.add(LexicalType.LITERAL);
    firstSet.addAll(CallFuncNode.getFirstSet());
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }

  public static Node isMatch(Environment env, LexicalUnit first) {
    return null;
  }
}
