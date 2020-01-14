package newlang4;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExprNode extends Node {

  private Deque<Object> rpnQueue = new ArrayDeque<Object>();

  private static final Set<LexicalType> FIRST_SET =
      EnumSet.of(
          LexicalType.SUB,
          LexicalType.LP,
          LexicalType.NAME,
          LexicalType.INTVAL,
          LexicalType.DOUBLEVAL,
          LexicalType.LITERAL);

  private static Map<LexicalType, Integer> prededence = new HashMap<>();

  static {
    prededence.put(LexicalType.MUL, 5);
    prededence.put(LexicalType.DIV, 5);
    prededence.put(LexicalType.ADD, 4);
    prededence.put(LexicalType.SUB, 4);
    prededence.put(LexicalType.LP, 0);
  }

  private static final Set<LexicalType> EXPR_SET =
      EnumSet.of(
          LexicalType.LP,
          LexicalType.RP,
          LexicalType.MUL,
          LexicalType.DIV,
          LexicalType.ADD,
          LexicalType.SUB,
          LexicalType.NAME,
          LexicalType.INTVAL,
          LexicalType.DOUBLEVAL,
          LexicalType.LITERAL,
          LexicalType.FUNC);

  private ExprNode(Environment env) {
    super(NodeType.EXPR, env);
  }

  public ExprNode(Node l, Node r, LexicalType o) {
    super(NodeType.EXPR);
  }

  public static boolean isFirst(LexicalUnit lu) {
    return FIRST_SET.contains(lu.getType());
  }

  public static Node getHandler(Environment env) throws Exception {
    LexicalType first = env.getInput().peek().getType();
    LexicalType second = env.getInput().peek(2).getType();
    if (first == LexicalType.SUB || first == LexicalType.LP || prededence.containsKey(second)) {
      return new ExprNode(env);
    }
    if (first == LexicalType.NAME) {
      return VariableNode.getHandler(first, env);
    }
    if (ConstNode.isFirst(env.getInput().peek())) {
      return ConstNode.getHandler(env.getInput().peek(), env);
    }
    throw new Exception("Invalid");
  }

  @Override
  public boolean parse() throws Exception {
    RPNize();
    return true;
  }

  @Override
  public Value getValue() throws Exception {
    return super.getValue();
  }

  @Override
  public String toString() {
    String s = rpnQueue.toString();
    return s.substring(1, s.length() - 1);
  }

  private void RPNize() throws Exception {
    Deque<Object> stack = new ArrayDeque<>();

    while (true) {
      LexicalUnit lu = env.getInput().peek();
      LexicalType lt = lu.getType();
      if (!EXPR_SET.contains(lt)) {
        break;
      }

      if (lt == LexicalType.LP) {
        stack.push(env.getInput().get().getType());
        continue;
      }

      if (lt == LexicalType.RP) {
        env.getInput().get();
        while (stack.peek() != LexicalType.LP) {
          rpnQueue.add(stack.pop());
        }
        stack.pop();
        continue;
      }

      if (prededence.containsKey(lt)) {
        while (!stack.isEmpty() && prededence.get(lt) <= prededence.get(stack.peek())) {
          rpnQueue.add(stack.pop());
        }
        stack.push(env.getInput().get().getType());
        continue;
      }

      if (VariableNode.isFirst(lu)) {
        rpnQueue.add(VariableNode.getHandler(lt, env));
        continue;
      }

      if (ConstNode.isFirst(lu)) {
        rpnQueue.add(ConstNode.getHandler(lu, env));
        continue;
      }

      throw new Exception("Invalid");
    }

    while (!stack.isEmpty()) {
      rpnQueue.add(stack.pop());
    }
  }
}
