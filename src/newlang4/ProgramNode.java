package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class ProgramNode extends Node {
  private Node child;
  private static final Set<LexicalType> firstSet =
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
    return firstSet.contains(lu.getType());
  }

  public static Node getHandler(LexicalUnit first, Environment env) {
    return new ProgramNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    LexicalUnit first = this.env.getInput().peek();
    if (StmtListNode.isFirst(first)) {
      this.child = StmtListNode.getHandler(first, this.env);
      return this.child.parse();
    }
    return false;
  }

  @Override
  public String toString() {
    return "program(" + child.toString() + ")";
  }

  @Override
  public Value getValue() throws Exception {
    return child.getValue();
  }
}
