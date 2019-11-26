package newlang4;

import java.util.HashSet;
import java.util.Set;

public class StmtListNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.addAll(StmtNode.getFirstSet());
    firstSet.addAll(BlockNode.getFirstSet());
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }
}
