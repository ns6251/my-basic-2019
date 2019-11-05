package newlang3;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    FileReader fr;

    public LexicalAnalyzerImpl(String fname) throws FileNotFoundException {
        fr = new FileReader(fname);
    }

    @Override
    public LexicalUnit get() throws Exception {
        int c = fr.read();
        while (c != -1) {
            if (c == ' ' || c == '\t') {
                c = fr.read();
            } else if (c >= '0' && c <= '9') {
                return getInt();
            } else if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                return getStr();
            } else if (c == '=') {
                return getLiteral();
            } else {
                return getSpecial();
            }
        }
        return null;

    }

    @Override
    public boolean expect(LexicalType type) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public void unget(LexicalUnit token) throws Exception {
        // TODO 自動生成されたメソッド・スタブ

    }

    private LexicalUnit getInt() {
        return null;
    }

    private LexicalUnit getStr() {
        return null;
    }

    private LexicalUnit getLiteral() {
        return null;
    }

    private LexicalUnit getSpecial() {
        return null;
    }
}
