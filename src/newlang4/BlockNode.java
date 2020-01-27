package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class BlockNode extends Node {
  private static final Set<LexicalType> FIRST_SET =
      EnumSet.of(LexicalType.IF, LexicalType.WHILE, LexicalType.DO);

  public static boolean isFirst(LexicalUnit first) {
    return FIRST_SET.contains(first.getType());
  }

  public static Node getHandler(Environment env) throws Exception {
    LexicalUnit lu = env.getInput().peek();
    if (IfBlockNode.isFirst(lu)) {
      return IfBlockNode.getHandler(env);
    }
    if (LoopBlockNode.isFirst(lu)) {
      return LoopBlockNode.getHandler(env);
    }
    throw new Exception();
  }

  @Override
  public Value getValue() throws Exception {
    return super.getValue();
  }

  @Override
  public String toString() {
    return "Block";
  }
}
