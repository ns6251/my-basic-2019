package newlang4;

import java.util.HashSet;
import java.util.Set;

public class ProgramNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.addAll(StmtListNode.getFirstSet());
  }

  public static Node isMatch(Environment env, LexicalUnit first) {
    return null;
  }
}
