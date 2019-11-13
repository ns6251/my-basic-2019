package newlang3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    private PushbackReader reader;
    private static int next;
    private static final String REG_LITERAL = "^\".*\"$";
    private static final String REG_NUMBER = "^[0-9]+\\.?[0-9]*$";
    private static final String REG_INTVAL = "^[0-9]+$";
    private static final String REG_NAME = "^[a-zA-Z_][a-zA-Z0-9_]*$";
    private static final String REG_BLANK = "^[ \t]+$";
    private static Map<String, LexicalUnit> reserved = new HashMap<String, LexicalUnit>();
    private static Map<String, LexicalUnit> operator = new HashMap<String, LexicalUnit>();
    private static Map<String, LexicalUnit> special = new HashMap<String, LexicalUnit>();
    static {
        reserved.put("IF", new LexicalUnit(LexicalType.IF));
        reserved.put("THEN", new LexicalUnit(LexicalType.THEN));
        reserved.put("ELSE", new LexicalUnit(LexicalType.ELSE));
        reserved.put("ELSEIF", new LexicalUnit(LexicalType.ELSEIF));
        reserved.put("ENDIF", new LexicalUnit(LexicalType.ENDIF));
        reserved.put("FOR", new LexicalUnit(LexicalType.FOR));
        reserved.put("FORALL", new LexicalUnit(LexicalType.FORALL));
        reserved.put("NEXT", new LexicalUnit(LexicalType.NEXT));
        reserved.put("SUB", new LexicalUnit(LexicalType.SUB));
        reserved.put("FUNC", new LexicalUnit(LexicalType.FUNC));
        reserved.put("DIM", new LexicalUnit(LexicalType.DIM));
        reserved.put("AS", new LexicalUnit(LexicalType.AS));
        reserved.put("END", new LexicalUnit(LexicalType.END));
        reserved.put("DOT", new LexicalUnit(LexicalType.DOT));
        reserved.put("WHILE", new LexicalUnit(LexicalType.WHILE));
        reserved.put("DO", new LexicalUnit(LexicalType.DO));
        reserved.put("UNTIL", new LexicalUnit(LexicalType.UNTIL));
        reserved.put("LOOP", new LexicalUnit(LexicalType.LOOP));
        reserved.put("TO", new LexicalUnit(LexicalType.TO));
        reserved.put("WEND", new LexicalUnit(LexicalType.WEND));
        reserved.put("IF", new LexicalUnit(LexicalType.IF));
        operator.put("=", new LexicalUnit(LexicalType.EQ));
        operator.put("<", new LexicalUnit(LexicalType.LT));
        operator.put(">", new LexicalUnit(LexicalType.GT));
        operator.put("<=", new LexicalUnit(LexicalType.LE));
        operator.put("=<", new LexicalUnit(LexicalType.LE));
        operator.put(">=", new LexicalUnit(LexicalType.GE));
        operator.put("=>", new LexicalUnit(LexicalType.GE));
        operator.put("<>", new LexicalUnit(LexicalType.NE));
        operator.put("+", new LexicalUnit(LexicalType.ADD));
        operator.put("-", new LexicalUnit(LexicalType.SUB));
        operator.put("*", new LexicalUnit(LexicalType.MUL));
        operator.put("/", new LexicalUnit(LexicalType.DIV));
        operator.put(")", new LexicalUnit(LexicalType.LP));
        operator.put("(", new LexicalUnit(LexicalType.RP));
        operator.put(",", new LexicalUnit(LexicalType.COMMA));
        special.put("\n", new LexicalUnit(LexicalType.NL));
        special.put("\r", new LexicalUnit(LexicalType.NL));
        special.put("\r\n", new LexicalUnit(LexicalType.NL));
    }

    public LexicalAnalyzerImpl(String fname) {
        try {
            reader = new PushbackReader(new FileReader(fname));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LexicalUnit get() {
        int c = skipBlank();
        if (c == -1 || next == -1) {
            return new LexicalUnit(LexicalType.EOF);
        }
        String target = String.valueOf((char) c);
        if (operator.containsKey(target)) {
            return getOperator(target);
        } else if (target.matches("\"")) {
            return getLiteral(target);
        } else if (target.matches(REG_INTVAL)) {
            return getNumber(target);
        } else if (target.matches(REG_NAME)) {
            return getName(target);
        } else if (special.containsKey(target)) {
            return getSpecial(target);
        } else {
            System.err.println("字句解析エラー！");
            System.err.println("What is \"" + (char) c + "\" ?");
            System.exit(1);
            return null;
        }
    }

    private LexicalUnit getSpecial(String target) {
        try {
            next = reader.read();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String n = String.valueOf((char) next);
        if (special.get(target + n) == null) {
            try {
                reader.unread(next);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new LexicalUnit(LexicalType.NL);
    }

    private LexicalUnit getName(String target) {
        while (true) {
            try {
                next = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String n = String.valueOf((char) next);
            if ((target + n).matches(REG_NAME)) {
                target += n;
            } else {
                try {
                    reader.unread(next);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        LexicalUnit lu = reserved.get(target);
        if (lu == null) {
            return new LexicalUnit(LexicalType.NAME, new ValueImpl(target));
        }
        return lu;
    }

    private LexicalUnit getNumber(String target) {
        while (true) {
            try {
                next = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String n = String.valueOf((char) next);
            if ((target + n).matches(REG_NUMBER)) {
                target += n;
            } else {
                try {
                    reader.unread(next);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if (target.matches(REG_INTVAL)) {
            return new LexicalUnit(LexicalType.INTVAL, new ValueImpl(target, ValueType.INTEGER));
        } else {
            return new LexicalUnit(LexicalType.DOUBLEVAL, new ValueImpl(target, ValueType.DOUBLE));
        }
    }

    private LexicalUnit getLiteral(String target) {
        while (true) {
            try {
                next = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String n = String.valueOf((char) next);
            if (next == -1 || special.containsKey(n)) {
                System.err.println("字句解析エラー: ... \"" + target + "\"");
                System.err.println("\"で閉じられませんでした。");
                System.exit(1);
            }
            target += n;
            if (target.matches(REG_LITERAL)) {
                target = target.substring(1, target.length() - 1);
                return new LexicalUnit(LexicalType.LITERAL, new ValueImpl(target, ValueType.STRING));
            }
        }
    }

    private LexicalUnit getOperator(String target) {
        while (true) {
            try {
                next = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String n = String.valueOf((char) next);
            if (operator.containsKey(target + n)) {
                target += n;
            } else {
                try {
                    reader.unread(next);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return operator.get(target);
            }
        }
    }

    private int skipBlank() {
        int c;
        try {
            do {
                c = reader.read();
            } while (String.valueOf((char) c).matches(REG_BLANK));
            return c;
        } catch (Exception e) {
            return -1;
        }
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

}
