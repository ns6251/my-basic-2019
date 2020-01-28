package newlang5;

public class ForNode extends Node {
  private Node init;
  private Node limit;
  private Node stmts;
  private Node ctrlVar;

  private ForNode(Environment env) {
    super(NodeType.FOR_STMT, env);
  }

  public static Node getHandler(Environment env) {
    return new ForNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    if (env.getInput().get().getType() != LexicalType.FOR)
      throw new SyntaxException("FOR is required at the beginning of for statement");

    init = SubstNode.getHandler(env);
    init.parse();

    if (env.getInput().get().getType() != LexicalType.TO)
      throw new SyntaxException("TO is required at the end of cond in for statement");

    if (!env.getInput().expect(LexicalType.INTVAL))
      throw new SyntaxException("value of continuation conditional must be int in for statement");
    limit = ConstNode.getHandler(env);

    if (env.getInput().get().getType() != LexicalType.NL)
      throw new SyntaxException("required at least a newline");
    skipNL();

    if (!StmtListNode.isFirst(env.getInput().peek()))
      throw new SyntaxException("required at least a statement in for block");
    stmts = StmtListNode.getHandler(env);
    stmts.parse();

    if (env.getInput().get().getType() != LexicalType.NEXT)
      throw new SyntaxException("NEXT is required at the end of for block");

    if (!VariableNode.isFirst(env.getInput().peek()))
      throw new SyntaxException("variable is required at the end of for block");
    ctrlVar = VariableNode.getHandler(env);

    if (ctrlVar != ((SubstNode) init).getLeftver())
      throw new SyntaxException("does not match control variable in FOR");

    return true;
  }

  private final void skipNL() throws Exception {
    while (env.getInput().expect(LexicalType.NL)) env.getInput().get();
  }

  @Override
  public Value getValue() throws Exception {
    for (init.getValue();
        ctrlVar.getValue().getIValue() <= limit.getValue().getIValue();
        updateCtrlVer()) {
      stmts.getValue();
    }
    return null;
  }

  private void updateCtrlVer() {
    int i = ((VariableNode) ctrlVar).getValue().getIValue() + 1;
    ((VariableNode) ctrlVar).setValue(new ValueImpl(i));
  }

  @Override
  public String toString() {
    String ret = "FOR (" + init.toString() + " TO " + limit.toString() + ") {\n";
    ret += stmts.toString();
    ret += "\n}";
    return ret;
  }
}
