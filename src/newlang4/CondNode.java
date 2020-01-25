package newlang4;

public class CondNode extends Node {
  private Node leftExpr;
  private Node rightExpr;
  private LexicalType operator;

  private CondNode(Environment env) {
    super(NodeType.IF_BLOCK, env);
  }

  public static boolean isFirst(LexicalUnit first) {
    return ExprNode.isFirst(first);
  }

  public static Node getHandler(Environment env) {
    return new CondNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    // TODO 自動生成されたメソッド・スタブ
    return super.parse();
  }

  @Override
  public Value getValue() throws Exception {
    // TODO 自動生成されたメソッド・スタブ
    return super.getValue();
  }

  @Override
  public String toString() {
    // TODO 自動生成されたメソッド・スタブ
    return super.toString();
  }
}
