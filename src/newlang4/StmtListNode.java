package newlang4;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class StmtListNode extends Node {
  private List<Node> children = new ArrayList<Node>();
  private static final Set<LexicalType> FIRST_SET =
      EnumSet.of(
          LexicalType.NAME,
          LexicalType.IF,
          LexicalType.DO,
          LexicalType.WHILE,
          LexicalType.FOR,
          LexicalType.END,
          LexicalType.NL);

  private StmtListNode(Environment env) {
    super(NodeType.STMT_LIST, env);
  }

  public static boolean isFirst(LexicalUnit first) {
    return FIRST_SET.contains(first.getType());
  }

  public static Node getHandler(LexicalUnit first, Environment env) {
    return new StmtListNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    Node child;
    while (true) {
      while (env.getInput().expect(LexicalType.NL)) {
        env.getInput().get();
      }
      LexicalUnit first = env.getInput().peek();
      if (StmtNode.isFirst(first)) {
        child = StmtNode.getHandler(first, env);
        children.add(child);
        if (!child.parse()) {
          return false;
        }
      } else {
        return true;
      }
    }
  }

  @Override
  public String toString() {
    String str = "stmt_list(";
    for (Node child : children) {
      str += child.toString() + ",";
    }
    return str + ")";
  }

  @Override
  public Value getValue() throws Exception {
    return null;
  }
}
