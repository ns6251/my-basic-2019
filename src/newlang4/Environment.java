package newlang4;

import java.util.HashMap;
import java.util.Map;

public class Environment {
  private LexicalAnalyzer input;
  private Map<String, VariableNode> varTable;

  public Environment(LexicalAnalyzer input) {
    this.input = input;
    this.varTable = new HashMap<String, VariableNode>();
  }

  public LexicalAnalyzer getInput() {
    return this.input;
  }

  public VariableNode getVariable(String vname) {
    VariableNode v = (VariableNode) varTable.get(vname);
    if (v == null) {
      v = new VariableNode(vname);
      varTable.put(vname, v);
    }
    return v;
  }
}
