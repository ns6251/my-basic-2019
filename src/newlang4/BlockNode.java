package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class BlockNode extends Node {
  Node body;
  private static final Set<LexicalType> FIRST_SET = EnumSet.of(LexicalType.WHILE, LexicalType.DO);

  public static boolean isFirst(LexicalUnit first) {
    return FIRST_SET.contains(first.getType());
  }
}
