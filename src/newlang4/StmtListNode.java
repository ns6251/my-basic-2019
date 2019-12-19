package newlang4;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class StmtListNode extends Node {
  List<Node> body = new ArrayList<Node>();
  private static Set<LexicalType> firstSet = EnumSet.of(LexicalType.FUNC, LexicalType.END);

  @Override
  public boolean parse() throws Exception {
    LexicalUnit first = env.getInput().peek();
    if (StmtNode.isFirst(first)) {
      
    }
    return false;
  }

  public static boolean isFirst(LexicalUnit first) {
    return firstSet.contains(first.getType());
  }

  public static Node getHandler(LexicalUnit first, Environment env) {
    return null;
  }
}
