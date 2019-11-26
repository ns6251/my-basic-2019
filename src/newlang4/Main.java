package newlang4;

public class Main {

  public static void main(String[] args) throws Exception {
    for (LexicalType lt : StmtNode.getFirstSet()) {
      System.out.println(lt.toString());
    }
    String fname = "test.bas";
    if (args.length > 0) {
      fname = args[0];
    }
    LexicalAnalyzer lex = new LexicalAnalyzerImpl(fname);
    LexicalUnit first = lex.get();
    Environment env = new Environment(lex);
    System.out.println("basic parser");
    Node program = ProgramNode.isMatch(env, first);
    if (program != null && program.Parse()) {
      System.out.println(program);
      System.out.println("value = " + program.getValue());
    } else {
      System.out.println("syntax error");
    }
  }
}
