package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class SubstNode extends Node {
  private Node child;
  private static Set<LexicalType> firstSet = EnumSet.of(LexicalType.NAME);

  private SubstNode(Environment env) {
    super(env);
  }
}
