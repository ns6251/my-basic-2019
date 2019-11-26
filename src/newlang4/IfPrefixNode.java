package newlang4;

import java.util.HashSet;
import java.util.Set;

public class IfPrefixNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.add(LexicalType.IF);
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }
}
