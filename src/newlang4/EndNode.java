package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class EndNode extends Node {
  private static final Set<LexicalType> FIRST_SET = EnumSet.of(LexicalType.END);

  private EndNode(Environment env) {
    super(NodeType.END, env);
  }

  public static boolean isFirst(LexicalUnit lu) {
    return FIRST_SET.contains(lu.getType());
  }

  public static Node getHandler(Environment env) throws Exception {
    env.getInput().get();
    return new EndNode(env);
  }

  @Override
  public String toString() {
    return "END";
  }

  @Override
  public Value getValue() throws Exception {
    return new ValueImpl("END", ValueType.STRING);
  }
}
