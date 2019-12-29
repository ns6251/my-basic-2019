package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class ExprNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet =
      EnumSet.of(
          LexicalType.SUB,
          LexicalType.LP,
          LexicalType.NAME,
          LexicalType.INTVAL,
          LexicalType.DOUBLEVAL,
          LexicalType.LITERAL);

  public static Node isMatch(Environment env, LexicalUnit first) {
    return null;
  }
}
