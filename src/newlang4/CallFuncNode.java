package newlang4;

public class CallFuncNode extends Node {

  private String funcName;
  private Node exprList;

  /* false: this is call_sub
   * true:  call_func; exists return value
   */
  private boolean isFunc;

  private CallFuncNode(Environment env) {
    super(NodeType.FUNCTION_CALL, env);
  }

  public static Node getHandler(Environment env) throws Exception {
    if (env.getInput().expect(LexicalType.NAME)) {
      LexicalUnit lu = env.getInput().peek(2);
      if (lu.getType() == LexicalType.LP || ExprListNode.isFirst(lu)) return new CallFuncNode(env);
    }
    throw new Exception("Invalid");
  }

  @Override
  public boolean parse() throws Exception {
    LexicalUnit lu = env.getInput().get();
    if (lu.getType() != LexicalType.NAME) throw new Exception("Invalid");
    this.funcName = lu.getValue().getSValue();

    if (env.getInput().expect(LexicalType.LP)) {
      env.getInput().get();
      this.isFunc = true;
    }

    if (!ExprListNode.isFirst(env.getInput().peek())) throw new Exception("syntax exception");
    exprList = ExprListNode.getHandler(env);
    exprList.parse();

    if (this.isFunc)
      if (env.getInput().get().getType() != LexicalType.RP)
        throw new Exception("expr_list has not closed by ')'");

    return true;
  }

  @Override
  public Value getValue() throws Exception {
    return super.getValue();
  }

  @Override
  public String toString() {
    String ret = funcName + "(\n" + exprList.toString() + "\n)";
    return ret;
  }
}
