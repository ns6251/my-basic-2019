package newlang5;

public class EndNode extends Node {
  private EndNode(Environment env) {
    super(NodeType.END, env);
  }

  public static Node getHandler(Environment env) throws Exception {
    if (env.getInput().get().getType() != LexicalType.END) throw new Exception("Invalid");
    return new EndNode(env);
  }

  @Override
  public String toString() {
    return "END";
  }

  @Override
  public Value getValue() throws Exception {
    System.exit(0);
    return null;
  }
}
