package newlang4;

import java.util.EnumSet;
import java.util.Set;

public class StmtNode extends Node {
  Node body;
  private static Set<LexicalType> firstSet = EnumSet.of(LexicalType.FOR, LexicalType.END);

  private StmtNode(Environment env) {
    super.env = env;
    super.type = NodeType.STMT;
  }

  public static boolean isFirst(LexicalUnit lu) {
    return firstSet.contains(lu.getType());
  }

  public static Node getHandler(LexicalUnit first, Environment env) {
    return new StmtNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    LexicalUnit lu = env.getInput().get();
    env.getInput().unget(lu);

    body = SubstNode.isMatch(env, lu);
    if (body != null) {
      return body.parse();
    }

    body = CallSubNode.isMatch(env, lu);
    if (body != null) {
      return body.parse();
    }

    if (lu.getType() == LexicalType.END) {
      super.type = NodeType.END;
      return true;
    }

    return false;
  }
}
