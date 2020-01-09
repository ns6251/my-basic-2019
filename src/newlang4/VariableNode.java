package newlang4;

public class VariableNode extends Node {
  private String name;
  private Value value;

  /** Creates a new instance of variable */
  public VariableNode(String name) {
    this.name = name;
  }

  public VariableNode(LexicalUnit lu) {
    this.name = lu.getValue().getSValue();
  }

  public static boolean isFirst(LexicalUnit first) {
    return (first.getType() == LexicalType.NAME);
  }

  public static Node getHandler(LexicalType first, Environment my_env) throws Exception {
    if (first != LexicalType.NAME) {
      throw new Exception("Invalid error in variablenode");
    }
    LexicalUnit lu = my_env.getInput().get();
    String s = lu.getValue().getSValue();
    return my_env.getVariable(s);
  }

  public void setValue(Value v) {
    this.value = v;
  }

  public Value getValue() {
    return this.value;
  }

  public String toString() {
    return this.name;
  }
}
