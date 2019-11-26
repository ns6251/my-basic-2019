package newlang4;

import java.util.HashSet;
import java.util.Set;

public class VarlistNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.addAll(VarNode.getFirstSet());
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }
}
