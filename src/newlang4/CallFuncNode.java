package newlang4;

public class CallFuncNode extends Node {

  private CallFuncNode(Environment env) {
    super(NodeType.FUNCTION_CALL, env);
  }

  public static Node getHandler(Environment env) {
    return null;
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
