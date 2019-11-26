package newlang4;

import java.util.HashSet;
import java.util.Set;

public class StmtNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = new HashSet<LexicalType>();

  static {
    firstSet.addAll(SubstNode.getFirstSet());
    firstSet.addAll(CallSubNode.getFirstSet());
    firstSet.add(LexicalType.FOR);
    firstSet.add(LexicalType.END);
  }

  private StmtNode(Environment env) {
    super.env = env;
    super.type = NodeType.STMT;
  }

  public static Node isMatch(Environment env, LexicalUnit first) {
    if (!firstSet.contains(first.type)) {
      return null;
    }
    return new StmtNode(env);
  }

  public static Set<LexicalType> getFirstSet() {
    return firstSet;
  }

  @Override
  public boolean Parse() throws Exception {
    LexicalUnit lu = env.getInput().get();
    env.getInput().unget(lu);

    body = SubstNode.isMatch(env, lu);
    if (body != null) {
      return body.Parse();
    }

    body = CallSubNode.isMatch(env, lu);
    if (body != null) {
      return body.Parse();
    }

    if (lu.getType() == LexicalType.END) {
      super.type = NodeType.END;
      return true;
    }

    return false;
  }
}
