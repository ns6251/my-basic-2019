package newlang4;

import java.util.HashSet;
import java.util.Set;

public class BlockNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.addAll(IfPrefixNode.getFirstSet());
    firstSet.add(LexicalType.WHILE);
    firstSet.add(LexicalType.DO);
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }
}
