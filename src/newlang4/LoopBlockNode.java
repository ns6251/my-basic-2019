package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class LoopBlockNode extends Node {
  private static final Set<LexicalType> FIRST_SET = EnumSet.of(LexicalType.WHILE, LexicalType.DO);

  public static boolean isFirst(LexicalUnit lu) {
    return FIRST_SET.contains(lu.getType());
  }

  public static Node getHandler(Environment env) {
    return null;
  }
}
