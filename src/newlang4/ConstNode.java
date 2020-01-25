package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class ConstNode extends Node {
  private static final Set<LexicalType> FIRST_SET =
      EnumSet.of(LexicalType.INTVAL, LexicalType.DOUBLEVAL, LexicalType.LITERAL);
  private Value value;

  private ConstNode(LexicalUnit lu, Environment env) throws Exception {
    super(env);
    this.value = lu.getValue();
    switch (lu.getType()) {
      case INTVAL:
        this.type = NodeType.INT_CONSTANT;
        break;
      case DOUBLEVAL:
        this.type = NodeType.DOUBLE_CONSTANT;
        break;
      case LITERAL:
        this.type = NodeType.STRING_CONSTANT;
        break;
      default:
        throw new Exception("Invalid");
    }
  }

  public static boolean isFirst(LexicalUnit lu) {
    return FIRST_SET.contains(lu.getType());
  }

  public static final Node getHandler(Environment env) throws Exception {
    return getHandler(env.getInput().peek(), env);
  }

  public static Node getHandler(LexicalUnit lu, Environment env) throws Exception {
    if (!isFirst(lu)) {
      throw new Exception("Invalid getHandler");
    }
    if (lu != env.getInput().get()) {
      throw new Exception("Invalid");
    }
    return new ConstNode(lu, env);
  }

  @Override
  public Value getValue() throws Exception {
    return super.getValue();
  }

  @Override
  public String toString() {
    return this.value.getSValue();
  }
}
