package newlang5;

import java.util.EnumSet;
import java.util.Set;

public class ProgramNode extends Node {
  private static final Set<LexicalType> FIRST_SET =
      EnumSet.of(
          LexicalType.NAME,
          LexicalType.IF,
          LexicalType.DO,
          LexicalType.WHILE,
          LexicalType.FOR,
          LexicalType.END,
          LexicalType.NL);

  private ProgramNode(Environment env) {
    super(NodeType.PROGRAM, env);
  }

  public static boolean isFirst(LexicalUnit lu) {
    return FIRST_SET.contains(lu.getType());
  }

  public static Node getHandler(Environment env) {
    return StmtListNode.getHandler(env);
  }

  @Override
  public boolean parse() throws Exception {
    return false;
  }

  @Override
  public String toString() {
    return "program";
  }
}
