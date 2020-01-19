package newlang4;

public class ForNode extends Node {

  private ForNode(Environment env) {
    super(NodeType.FOR_STMT, env);
  }

  public static Node getHandler(Environment env) {
    return new ForNode(env);
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
