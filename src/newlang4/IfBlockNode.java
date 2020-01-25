package newlang4;

public class IfBlockNode extends Node {

  private static class NodePair {
    Node cond, stmt;
  }

  public IfBlockNode(Environment env) {
    super(NodeType.IF_BLOCK, env);
  }

  public static final boolean isFirst(LexicalUnit lu) {
    return lu.getType() == LexicalType.IF;
  }

  public static Node getHandler(Environment env) throws Exception {
    if (!isFirst(env.getInput().peek())) throw new Exception("Ivalid");
    return new IfBlockNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    if (env.getInput().get().getType() != LexicalType.IF) throw new Exception("Invalid");

    if (!CondNode.isFirst(env.getInput().peek())) throw new Exception("Syntax exception");

    return true;
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
