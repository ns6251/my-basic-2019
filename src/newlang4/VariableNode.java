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

  public static boolean isMatch(LexicalType first) {
    return (first == LexicalType.NAME);
  }

  public static Node getHandler(LexicalType first, Environment my_env) {
    if (first == LexicalType.NAME) {
      VariableNode v;

      try {
        LexicalUnit lu = my_env.getInput().peek();
        String s = lu.getValue().getSValue();
        v = my_env.getVariable(s);
        return v;
      } catch (Exception e) {
      }
    }
    return null;
  }

  public void setValue(Value v) {
    this.value = v;
  }

  public Value getValue() {
    return this.value;
  }

  public String toString() {
    return "NAME";
  }
}
