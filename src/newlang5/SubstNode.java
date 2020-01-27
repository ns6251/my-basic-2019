package newlang5;

public class SubstNode extends Node {
  private Node leftver;
  private Node expr;

  private SubstNode(Environment env) {
    super(NodeType.ASSIGN_STMT, env);
  }

  public static Node getHandler(Environment env) {
    return new SubstNode(env);
  }

  public Node getLeftver() {
    return leftver;
  }

  @Override
  public boolean parse() throws Exception {
    LexicalUnit lu = env.getInput().peek();
    if (!VariableNode.isFirst(lu)) {
      throw new Exception("Invalid token");
    }
    leftver = VariableNode.getHandler(lu.getType(), env);

    if (env.getInput().get().getType() != LexicalType.EQ) {
      throw new Exception("Syntax exception: Not found \"=\" token");
    }

    if (!ExprNode.isFirst(env.getInput().peek())) {
      throw new Exception(
          "Syntax exception: Right side of Assignment statment cannot evaluate as expression");
    }
    expr = ExprNode.getHandler(env);
    return expr.parse();
  }

  @Override
  public String toString() {
    return (leftver.toString() + "[" + expr.toString() + "]");
  }
}
