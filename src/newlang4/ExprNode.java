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

  private static final Set<LexicalType> UNARY_SET = EnumSet.of(LexicalType.SUB);

  private ExprNode(Environment env) {
    super(NodeType.EXPR, env);
  }

  public static boolean isFirst(LexicalUnit lu) {
    return FIRST_SET.contains(lu.getType());
  }

  public static Node getHandler(Environment env) throws Exception {
    LexicalType first = env.getInput().peek().getType();
    LexicalType second = env.getInput().peek(2).getType();
    if (first == LexicalType.LP || UNARY_SET.contains(first) || prededence.containsKey(second)) {
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
    Deque<Object> currStack = new ArrayDeque<>();
    boolean probUnary = true;
    int funcDepth = 0;
    LexicalUnit lu = env.getInput().peek();
    do {
      funcDepth = calcFuncDepth(funcDepth, lu);
      probUnary = RPNize(currStack, probUnary, lu);
      lu = env.getInput().peek();
    } while (EXPR_SET.contains(lu.getType()) && isInScope(funcDepth, lu));
    while (!currStack.isEmpty()) {
      rpnQueue.add(currStack.pop());
    }
    return true;
  }

  private static final boolean isInScope(int funcDepth, LexicalUnit lu) {
    return funcDepth > 0 || lu.getType() != LexicalType.RP;
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

  private boolean RPNize(Deque<Object> stack, boolean probUnary, LexicalUnit lu) throws Exception {
    LexicalType lt = lu.getType();
    if (lt == LexicalType.LP) {
      stack.push(env.getInput().get().getType());
      return true;
    }

    if (lt == LexicalType.RP) {
      env.getInput().get();
      while (stack.peek() != LexicalType.LP) {
        rpnQueue.add(stack.pop());
      }
      stack.pop();
      return false;
    }

    if (isUnary(probUnary, lt)) {
      env.getInput().get();
      switch (lt) {
        case SUB:
          LexicalUnit negative1 = new LexicalUnit(LexicalType.INTVAL, new ValueImpl(-1));
          LexicalUnit mul = new LexicalUnit(LexicalType.MUL);
          env.getInput().unget(mul);
          env.getInput().unget(negative1);
          break;
        default:
          throw new Exception("Invalid");
      }
      return true;
    }

    if (prededence.containsKey(lt)) {
      while (!stack.isEmpty() && prededence.get(lt) <= prededence.get(stack.peek())) {
        rpnQueue.add(stack.pop());
      }
      stack.push(env.getInput().get().getType());
      return true;
    }

    if (lt == LexicalType.NAME) {
      if (env.getInput().expect(2, LexicalType.LP)) {
        Node fn = CallFuncNode.getHandler(env);
        fn.parse();
        rpnQueue.add(fn);
      } else {
        rpnQueue.add(VariableNode.getHandler(lt, env));
      }
      return false;
    }

    if (ConstNode.isFirst(lu)) {
      rpnQueue.add(ConstNode.getHandler(lu, env));
      return false;
    }

    throw new Exception("Invalid");
  }

  private static final boolean isUnary(boolean prob, LexicalType lt) {
    return prob && UNARY_SET.contains(lt);
  }

  private static final int calcFuncDepth(int funcDepth, LexicalUnit lu) {
    switch (lu.getType()) {
      case LP:
        funcDepth++;
        break;
      case RP:
        funcDepth--;
      default:
        break;
    }
    return funcDepth;
  }
}
