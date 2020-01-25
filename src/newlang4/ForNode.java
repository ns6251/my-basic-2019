package newlang4;

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
    if (env.getInput().get().getType() != LexicalType.FOR) throw new Exception();

    init = SubstNode.getHandler(env);
    init.parse();

    if (env.getInput().get().getType() != LexicalType.TO) throw new Exception();

    if (!env.getInput().expect(LexicalType.INTVAL)) throw new Exception();
    limit = ConstNode.getHandler(env);

    if (env.getInput().get().getType() != LexicalType.NL) throw new Exception();
    skipNL();

    if (!StmtListNode.isFirst(env.getInput().peek())) throw new Exception();
    stmts = StmtListNode.getHandler(env);
    stmts.parse();

    if (env.getInput().get().getType() != LexicalType.NEXT) throw new Exception();

    if (!VariableNode.isFirst(env.getInput().peek())) throw new Exception();
    ctrlVar = VariableNode.getHandler(env);

    if (ctrlVar != ((SubstNode) init).getLeftver())
      throw new Exception("does not match control variable in FOR");

    return true;
  }

  private final void skipNL() throws Exception {
    while (env.getInput().expect(LexicalType.NL)) env.getInput().get();
  }

  @Override
  public Value getValue() throws Exception {
    return super.getValue();
  }

  @Override
  public String toString() {
    String ret = "FOR " + init.toString() + " TO " + limit.toString() + " (\n";
    ret += stmts.toString();
    ret += "\n)\n";
    return ret;
  }
}
