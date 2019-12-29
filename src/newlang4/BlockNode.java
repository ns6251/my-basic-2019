package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class BlockNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = EnumSet.of(LexicalType.WHILE, LexicalType.DO);

  public static Node isMatch(Environment env, LexicalUnit first) {
    return null;
  }
}
