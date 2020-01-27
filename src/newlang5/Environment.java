package newlang5;

import java.util.HashMap;
import java.util.Map;

public class Environment {
  private LexicalAnalyzer input;
  private Map<String, VariableNode> varTable;
  private Map<String, Function> library;

  public Environment(LexicalAnalyzer input) {
    this.input = input;
    this.varTable = new HashMap<String, VariableNode>();
    this.library = new HashMap<String, Function>();
    library.put("PRINT", new PrintFunction());
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

  public Function getFunction(String fname) {
    return (Function) library.get(fname);
  }
}
