package newlang4;

import java.io.File;

public class Main {

  public static void main(String[] args) {
    String fname = "test.bas";
    if (args.length > 0) {
      fname = args[0];
    }
    try {
      File sourceFile = new File(fname);
      LexicalAnalyzer lex = new LexicalAnalyzerImpl(sourceFile);
      LexicalUnit first = lex.get();
      lex.unget(first);
      Environment env = new Environment(lex);
      System.out.println("basic parser");
      if (ProgramNode.isFirst(first)) {
        Node program = ProgramNode.getHandler(first, env);
        if (program != null && program.parse()) {
          System.out.println(program);
          System.out.println("value = " + program.getValue());
        }
      } else {
        System.out.println("syntax error");
      }
    } catch (Exception e) {
      System.out.println("execution error");
    }
  }
}
