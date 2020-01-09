package newlang4;

import java.io.File;

public class Main {

  public static void main(String[] args) {
    String fname = (args.length > 0) ? args[0] : "sample.bas";
    // test
    fname = "../interpreter-testPrograms/test01.bas";
    try {
      File sourceFile = new File(fname);
      LexicalAnalyzer lex = new LexicalAnalyzerImpl(sourceFile);
      Environment env = new Environment(lex);
      LexicalUnit first = env.getInput().peek();
      if (ProgramNode.isFirst(first)) {
        Node program = ProgramNode.getHandler(env);
        if (program != null && program.parse()) {
          System.out.println(program.toString());
        } else {
          throw new Exception("Syntax error");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
