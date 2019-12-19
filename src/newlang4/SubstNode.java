package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class SubstNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = EnumSet.of(LexicalType.NAME);

  private SubstNode(Environment env) {
    super.env = env;
  }

  public static Node isMatch(Environment env, LexicalUnit first) {
    return null;
  }
}
