package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class LoopBlockNode extends Node {
  private Node cond;
  private Node stmtList;
  private boolean isUntil;
  private boolean isCondFirst;

  private static final Set<LexicalType> FIRST_SET = EnumSet.of(LexicalType.WHILE, LexicalType.DO);

  public LoopBlockNode(Environment env) {
    super(NodeType.LOOP_BLOCK, env);
  }

  public static boolean isFirst(LexicalUnit lu) {
    return FIRST_SET.contains(lu.getType());
  }

  public static Node getHandler(Environment env) throws Exception {
    if (!isFirst(env.getInput().peek()))
      throw new SyntaxException("Does not find token which WHILE or DO");
    return new LoopBlockNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    switch (env.getInput().get().getType()) {
      case WHILE:
        this.parseWhile(LexicalType.WEND);
        break;
      case DO:
        this.parseDo();
        break;
      default:
        throw new SyntaxException();
    }
    return true;
  }

  private void parseDo() throws Exception {
    switch (env.getInput().peek().getType()) {
      case UNTIL:
        this.isUntil = true;
      case WHILE:
        env.getInput().get(); // skip a until or while token
        this.parseWhile(LexicalType.LOOP);
        break;
      case NL:
        this.parseLoopWhile();
        break;
      default:
        throw new SyntaxException();
    }
  }

  private void parseLoopWhile() throws Exception {
    this.skipNL();
    if (!StmtListNode.isFirst(env.getInput().peek()))
      throw new SyntaxException("Does not find statements in the while block");
    this.stmtList = StmtListNode.getHandler(env);
    this.stmtList.parse();

    if (env.getInput().get().getType() != LexicalType.LOOP)
      throw new SyntaxException("While block does not end with LOOP token");

    switch (env.getInput().get().getType()) {
      case UNTIL:
        this.isUntil = true;
      case WHILE:
        break;
      default:
        throw new SyntaxException("Does not find a WHILE or UNTIL after LOOP");
    }

    if (!CondNode.isFirst(env.getInput().peek()))
      throw new SyntaxException("Does not find a conditional expression of the while statement");
    this.cond = CondNode.getHandler(env);
    this.cond.parse();
    this.skipNL();
  }

  private void parseWhile(LexicalType endToken) throws Exception {
    this.isCondFirst = true;

    if (!CondNode.isFirst(env.getInput().peek()))
      throw new SyntaxException("Does not find a conditional expression of the while statement");
    this.cond = CondNode.getHandler(env);
    this.cond.parse();
    this.skipNL();

    if (!StmtListNode.isFirst(env.getInput().peek()))
      throw new SyntaxException("Does not find statements in the while block");
    this.stmtList = StmtListNode.getHandler(env);
    this.stmtList.parse();

    if (env.getInput().get().getType() != endToken)
      throw new SyntaxException("While block does not end with " + endToken.toString() + " token");
    this.skipNL();
  }

  private final void skipNL() throws Exception {
    if (!env.getInput().expect(LexicalType.NL))
      throw new SyntaxException("Requires at least one NL");
    while (env.getInput().expect(LexicalType.NL)) env.getInput().get();
  }

  @Override
  public Value getValue() throws Exception {
    // TODO 自動生成されたメソッド・スタブ
    return super.getValue();
  }

  @Override
  public String toString() {
    String ret = "";

    if (this.isCondFirst) {
      ret += ((isUntil) ? "until (" : "while (") + this.cond.toString() + ") {\n";
      ret += this.stmtList.toString() + "\n}";
    } else {
      ret += "do {\n";
      ret += this.stmtList.toString() + "\n} ";
      ret += ((isUntil) ? "until (" : "while (") + this.cond.toString() + ")";
    }

    return ret;
  }
}
