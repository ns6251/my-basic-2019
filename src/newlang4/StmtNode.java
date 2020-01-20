package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class StmtNode extends Node {
  private static final Set<LexicalType> FIRST_SET =
      EnumSet.of(LexicalType.NAME, LexicalType.FOR, LexicalType.END);

  private StmtNode(Environment env) {
    super(NodeType.STMT, env);
  }

  public static boolean isFirst(LexicalUnit lu) {
    return FIRST_SET.contains(lu.getType());
  }

  public static Node getHandler(Environment env) throws Exception {
    if (env.getInput().expect(LexicalType.NAME)) {
      LexicalType lt = env.getInput().peek(2).getType();
      if (lt == LexicalType.EQ) return SubstNode.getHandler(env);
      if (ExprListNode.isFirst(lt)) return CallFuncNode.getHandler(env);
      throw new Exception("syntax exception");
    }

    if (env.getInput().expect(LexicalType.FOR)) return ForNode.getHandler(env);

    if (env.getInput().expect(LexicalType.END)) return EndNode.getHandler(env);

    throw new Exception("syntax exception");
  }

  @Override
  public boolean parse() throws Exception {
    throw new Exception();
  }

  @Override
  public String toString() {
    return "stmt";
  }
}
