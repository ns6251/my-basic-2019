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

  public static Node getHandler(Environment env) {
    return new StmtListNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    while (true) {
      skipNL();
      LexicalUnit first = env.getInput().peek();

      if (StmtNode.isFirst(first)) {
        Node child = StmtNode.getHandler(env);
        this.children.add(child);
        child.parse();
        continue;
      }

      if (BlockNode.isFirst(first)) {
        Node child = BlockNode.getHandler(env);
        this.children.add(child);
        child.parse();
        continue;
      }

      return true;
    }
  }

  @Override
  public String toString() {
    String str = "stmt_list(\n";
    for (Node child : children) {
      str += child.toString() + ",\n";
    }
    return str.substring(0, str.length() - 2) + "\n)";
  }

  @Override
  public Value getValue() throws Exception {
    return null;
  }

  private final void skipNL() throws Exception {
    while (env.getInput().expect(LexicalType.NL)) env.getInput().get();
  }
}
