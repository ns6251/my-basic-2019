package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class ProgramNode extends Node {
  private Node body;
  private static Set<LexicalType> firstSet =
      EnumSet.of(
          LexicalType.NAME,
          LexicalType.IF,
          LexicalType.DO,
          LexicalType.WHILE,
          LexicalType.FOR,
          LexicalType.FORALL,
          LexicalType.INTVAL,
          LexicalType.DOUBLEVAL,
          LexicalType.BOOLVAL,
          LexicalType.LITERAL,
          LexicalType.END);

  private ProgramNode(Environment env) {
    super.env = env;
    super.type = NodeType.PROGRAM;
  }

  public static boolean isFirst(LexicalUnit lu) {
    return firstSet.contains(lu.getType());
  }

  public static Node getHandler(LexicalUnit first, Environment env) {
    return new ProgramNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    LexicalUnit first = this.env.getInput().peek();
    if (StmtListNode.isFirst(first)) {
      this.body = StmtListNode.getHandler(first, this.env);
    }
    return false;
  }
}
