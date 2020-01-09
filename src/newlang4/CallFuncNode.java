package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class CallFuncNode extends Node {
  private static Set<LexicalType> FIRST_SET = EnumSet.of(LexicalType.NAME);

  private CallFuncNode(Environment env) {
    super(NodeType.FUNCTION_CALL, env);
  }

  public static boolean isFirst(LexicalUnit lu) {
    return FIRST_SET.contains(lu.getType());
  }

  @Override
  public boolean parse() throws Exception {
    return super.parse();
  }

  @Override
  public Value getValue() throws Exception {
    return super.getValue();
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
