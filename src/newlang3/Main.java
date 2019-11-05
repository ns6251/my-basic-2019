package newlang3;

public class Main {

    public static void main(String[] args) throws Exception {
        String fname = args[0];
        LexicalAnalyzerImpl analyzerContent = new LexicalAnalyzerImpl(fname);
        LexicalUnit lexUnit;

        while (true) {
            lexUnit = analyzerContent.get();
            System.out.println(lexUnit);
            if (lexUnit.getType() == LexicalType.EOF) {
                break;
            }
        }
    }

}
