package newlang5;

import java.util.ArrayDeque;
import java.util.Queue;

public class IfBlockNode extends Node {

  private class NodePair {
    public final Node cond;
    public final Node stmt;

    public NodePair(Node cond, Node stmt) {
      this.cond = cond;
      this.stmt = stmt;
    }
  }

  private Queue<NodePair> queue = new ArrayDeque<>();

  public IfBlockNode(Environment env) {
    super(NodeType.IF_BLOCK, env);
  }

  public static final boolean isFirst(LexicalUnit lu) {
    return lu.getType() == LexicalType.IF;
  }

  public static Node getHandler(Environment env) throws Exception {
    if (false == isFirst(env.getInput().peek())) throw new Exception();
    return new IfBlockNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    Node cond = parsePrefix(LexicalType.IF);

    if (env.getInput().expect(LexicalType.NL)) { // if-φelif-φelse
      do {
        skipNL();

        if (!StmtListNode.isFirst(env.getInput().peek())) throw new SyntaxException();
        Node stmts = StmtListNode.getHandler(env);
        stmts.parse();
        queue.add(new NodePair(cond, stmts));

        switch (env.getInput().peek().getType()) {
          case ELSEIF:
            cond = parsePrefix(LexicalType.ELSEIF);
            break;
          case ELSE:
            env.getInput().get();
            cond = createNodeAsTrue();
            break;
          case ENDIF:
            break;
          default:
            throw new SyntaxException();
        }
      } while (!env.getInput().expect(LexicalType.ENDIF));
      env.getInput().get(); // endif
      skipNL();

    } else { // if-φelse (single stmt, one liner)
      if (!StmtNode.isFirst(env.getInput().peek()))
        throw new SyntaxException("Cannot find a statment after if prefix");
      Node stmt = StmtNode.getHandler(env);
      stmt.parse();
      queue.add(new NodePair(cond, stmt));

      switch (env.getInput().get().getType()) {
        case NL:
          break;
        case ELSE:
          cond = createNodeAsTrue();
          stmt = StmtNode.getHandler(env);
          stmt.parse();
          queue.add(new NodePair(cond, stmt));
          skipNL();
          break;
        default:
          throw new SyntaxException();
      }
    }
    return true;
  }

  private final Node parsePrefix(LexicalType reqPrefix) throws Exception {
    if (env.getInput().get().getType() != reqPrefix)
      throw new SyntaxException(
          "Cannot find "
              + reqPrefix.toString()
              + " at first of "
              + reqPrefix.toString().toLowerCase()
              + " prefix");

    if (!CondNode.isFirst(env.getInput().peek())) throw new SyntaxException();
    Node cond = CondNode.getHandler(env);
    cond.parse();

    if (env.getInput().get().getType() != LexicalType.THEN)
      throw new SyntaxException(
          "Cannot find THEN at last of " + reqPrefix.toString().toLowerCase() + " prefix");

    return cond;
  }

  private final void skipNL() throws Exception {
    if (!env.getInput().expect(LexicalType.NL))
      throw new SyntaxException("Requires at least one NL");
    while (env.getInput().expect(LexicalType.NL)) env.getInput().get();
  }

  private final Node createNodeAsTrue() throws Exception {
    LexicalUnit boolUnit = new LexicalUnit(LexicalType.BOOLVAL, new ValueImpl(true));
    env.getInput().unget(boolUnit);
    return ConstNode.getHandler(boolUnit, env);
  }

  @Override
  public Value getValue() throws Exception {
    return super.getValue();
  }

  @Override
  public String toString() {
    String ret = "";
    for (NodePair np : queue) {
      ret += np.cond.toString() + " ? " + np.stmt.toString() + " : ";
    }
    ret = ret.substring(0, ret.length() - 3);
    return ret;
  }
}
