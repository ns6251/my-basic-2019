package newlang5;

import java.util.List;

public class PrintFunction extends Function {

  @Override
  public Value eval(ExprListNode arg) throws Exception {
    List<Node> exprList = arg.getChildren();
    String s = "";
    for (Node n : exprList) {
      s += n.getValue().getSValue() + " ";
    }
    s = s.replace("\\n", "\n");
    System.out.println(s.substring(0, s.length() - 1));
    return null;
  }
}
