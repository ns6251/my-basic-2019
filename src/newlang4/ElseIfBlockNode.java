package newlang4;

import java.util.HashSet;
import java.util.Set;

public class ElseIfBlockNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }
}
