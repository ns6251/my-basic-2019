package newlang5;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ExprListNode extends Node {

  private List<Node> children = new ArrayList<>();

  private static final Set<LexicalType> FIRST_SET =
      EnumSet.of(
          LexicalType.SUB,
          LexicalType.LP,
          LexicalType.NAME,
          LexicalType.INTVAL,
          LexicalType.DOUBLEVAL,
          LexicalType.LITERAL);

  public ExprListNode(Environment env) {
    super(NodeType.EXPR_LIST, env);
  }

  public static final boolean isFirst(LexicalUnit lu) {
    return isFirst(lu.getType());
  }

  public static boolean isFirst(LexicalType lt) {
    return FIRST_SET.contains(lt);
  }

  public static Node getHandler(Environment env) throws Exception {
    if (!isFirst(env.getInput().peek())) throw new Exception("Internal exception");
    return new ExprListNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    while (true) {
      if (!ExprNode.isFirst(env.getInput().peek())) throw new Exception("Internal exception");
      Node child = ExprNode.getHandler(env);
      child.parse();
      children.add(child);

      if (env.getInput().expect(LexicalType.COMMA)) {
        env.getInput().get();
      } else {
        return true;
      }
    }
  }

  @Override
  public Value getValue() throws Exception {
    return super.getValue();
  }

  @Override
  public String toString() {
    String ret = "";
    for (Node child : children) {
      ret += "[" + child.toString() + "],";
    }
    return ret.substring(0, ret.length() - 1);
  }
}
