package newlang4;

import java.util.Hashtable;

public class Environment {
  LexicalAnalyzer input;
  Hashtable var_table;

  public Environment(LexicalAnalyzer input) {
    this.input = input;
    this.var_table = new Hashtable();
  }

  public LexicalAnalyzer getInput() {
    return input;
  }
}
