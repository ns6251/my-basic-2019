package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class CondNode extends Node {
  private static final Set<LexicalType> COMP_OP_SET =
      EnumSet.of(
          LexicalType.EQ,
          LexicalType.GT,
          LexicalType.LT,
          LexicalType.GE,
          LexicalType.LE,
          LexicalType.NE);

  private Node leftExpr;
  private Node rightExpr;
  private LexicalType operator;

  private CondNode(Environment env) {
    super(NodeType.IF_BLOCK, env);
  }

  public static final boolean isFirst(LexicalUnit first) {
    return ExprNode.isFirst(first);
  }

  public static Node getHandler(Environment env) {
    return new CondNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    if (!ExprNode.isFirst(env.getInput().peek())) throw new Exception("Invalid");
    leftExpr = ExprNode.getHandler(env);
    leftExpr.parse();

    operator = env.getInput().get().getType();
    if (!COMP_OP_SET.contains(operator)) throw new Exception("Syntax exception");

    if (!ExprNode.isFirst(env.getInput().peek())) throw new Exception("Invalid");
    rightExpr = ExprNode.getHandler(env);
    rightExpr.parse();

    return true;
  }

  @Override
  public Value getValue() throws Exception {
    // TODO 自動生成されたメソッド・スタブ
    return super.getValue();
  }

  @Override
  public String toString() {
    return leftExpr.toString() + " " + operator.toString() + " " + rightExpr.toString();
  }
}
