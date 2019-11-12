package newlang3;

public class Main {

    public static void main(String[] args) {
        String fname = "test.bas";
        if (args.length > 0) {
            fname = args[0];
        }
        LexicalAnalyzer la = new LexicalAnalyzerImpl(fname);
        while (true) {
            LexicalUnit lu = null;
            try {
                lu = la.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(lu);
            if (lu.getType() == LexicalType.EOF) {
                break;
            }
        }
    }

}
