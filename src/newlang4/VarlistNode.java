package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class VarlistNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = EnumSet.of();

  public static Node isMatch(Environment env, LexicalUnit first) {
    return null;
  }
}
