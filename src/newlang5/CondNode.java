package newlang5;

import java.util.EnumSet;
import java.util.Set;

public class CondNode extends Node {
  private static final Set<LexicalType> COMP_OP_SET =
      EnumSet.of(
          LexicalType.EQ,
          LexicalType.GT,
          LexicalType.LT,
          LexicalType.GE,
          LexicalType.LE,
          LexicalType.NE);

  private Node leftExpr;
  private Node rightExpr;
  private LexicalType operator;

  private CondNode(Environment env) {
    super(NodeType.IF_BLOCK, env);
  }

  public static final boolean isFirst(LexicalUnit first) {
    return ExprNode.isFirst(first);
  }

  public static Node getHandler(Environment env) {
    return new CondNode(env);
  }

  @Override
  public boolean parse() throws Exception {
    if (!ExprNode.isFirst(env.getInput().peek())) throw new Exception("Invalid");
    leftExpr = ExprNode.getHandler(env);
    leftExpr.parse();

    operator = env.getInput().get().getType();
    if (!COMP_OP_SET.contains(operator)) throw new Exception("Syntax exception");

    if (!ExprNode.isFirst(env.getInput().peek())) throw new Exception("Invalid");
    rightExpr = ExprNode.getHandler(env);
    rightExpr.parse();

    return true;
  }

  @Override
  public Value getValue() throws Exception {
    Value lval = this.leftExpr.getValue();
    Value rval = this.rightExpr.getValue();
    ValueType lvt = lval.getType();
    ValueType rvt = rval.getType();

    boolean result = false;

    if (lvt == ValueType.STRING || rvt == ValueType.STRING) {
      switch (this.operator) {
        case EQ:
          result = lval.getSValue().equals(rval.getSValue());
          break;
        case NE:
          result = !lval.getSValue().equals(rval.getSValue());
          break;
        default:
          throw new RuntimeException();
      }
    } else if (lvt == ValueType.BOOL || rvt == ValueType.BOOL) {
      if (lvt != rvt) throw new RuntimeException("cannot compare bool with number");
      switch (this.operator) {
        case EQ:
          result = lval.getBValue() == rval.getBValue();
          break;
        case NE:
          result = lval.getBValue() != rval.getBValue();
          break;
        default:
          throw new RuntimeException("undefined: bool" + this.operator + "bool");
      }
    } else {
      switch (this.operator) {
        case EQ:
          result = lval.getDValue() == rval.getDValue();
          break;
        case NE:
          result = lval.getDValue() != rval.getDValue();
          break;
        case GT:
          result = lval.getDValue() > rval.getDValue();
          break;
        case GE:
          result = lval.getDValue() >= rval.getDValue();
          break;
        case LT:
          result = lval.getDValue() < rval.getDValue();
          break;
        case LE:
          result = lval.getDValue() <= rval.getDValue();
          break;
        default:
          throw new RuntimeException();
      }
    }
    return new ValueImpl(result);
  }

  @Override
  public String toString() {
    return "[" + leftExpr.toString() + " " + operator.toString() + " " + rightExpr.toString() + "]";
  }
}
