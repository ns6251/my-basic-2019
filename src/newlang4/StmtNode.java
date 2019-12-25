package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class StmtNode extends Node {
  private Node child;
  private static final Set<LexicalType> firstSet = EnumSet.of(LexicalType.FOR, LexicalType.END);

  private StmtNode(Environment env) {
    super.env = env;
    super.type = NodeType.STMT;
  }

  public static boolean isFirst(LexicalUnit lu) {
    return firstSet.contains(lu.getType());
  }

  public static Node getHandler(LexicalUnit first, Environment env) {
    return new StmtNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    LexicalUnit lu = env.getInput().peek();
    if (EndNode.isFirst(lu)) {
      env.getInput().get();
      child = EndNode.getHandler(lu, env);
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return "stmt(" + child.toString() + ")";
  }
}
