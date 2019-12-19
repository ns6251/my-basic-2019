package newlang3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
  private PushbackReader reader;
  private static final String REG_LITERAL = "^\".*\"$";
  private static final String REG_NUMBER = "^[0-9]+(\\.?[0-9]*)*$";
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
    reserved.put("WHILE", new LexicalUnit(LexicalType.WHILE));
    reserved.put("DO", new LexicalUnit(LexicalType.DO));
    reserved.put("UNTIL", new LexicalUnit(LexicalType.UNTIL));
    reserved.put("LOOP", new LexicalUnit(LexicalType.LOOP));
    reserved.put("TO", new LexicalUnit(LexicalType.TO));
    reserved.put("WEND", new LexicalUnit(LexicalType.WEND));
    reserved.put("IF", new LexicalUnit(LexicalType.IF));
    operator.put(".", new LexicalUnit(LexicalType.DOT));
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
    operator.put("(", new LexicalUnit(LexicalType.LP));
    operator.put(")", new LexicalUnit(LexicalType.RP));
    operator.put(",", new LexicalUnit(LexicalType.COMMA));
    special.put("\n", new LexicalUnit(LexicalType.NL));
    special.put("\r", new LexicalUnit(LexicalType.NL));
    special.put("\r\n", new LexicalUnit(LexicalType.NL));
  }

  public LexicalAnalyzerImpl(String fname) throws FileNotFoundException {
    reader = new PushbackReader(new FileReader(fname));
  }

  @Override
  public LexicalUnit get() throws IOException {
    int c = skipBlank();
    if (c == -1) {
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
      return getNewline(target);
    } else {
      System.err.println("字句解析エラー");
      System.err.println("What is \"" + (char) c + "\" ?");
      System.exit(1);
      return null;
    }
  }

  private LexicalUnit getNewline(String target) throws IOException {
    int next = reader.read();
    String n = String.valueOf((char) next);
    if (special.get(target + n) == null && next != -1) {
      reader.unread(next);
    }
    return new LexicalUnit(LexicalType.NL);
  }

  private LexicalUnit getName(String target) throws IOException {
    while (true) {
      int next = reader.read();
      String n = String.valueOf((char) next);
      if ((target + n).matches(REG_NAME)) {
        target += n;
      } else if (next != -1) {
        reader.unread(next);
        break;
      }
    }
    LexicalUnit lu = reserved.get(target);
    if (lu == null) {
      return new LexicalUnit(LexicalType.NAME, new ValueImpl(target));
    }
    return lu;
  }

  private LexicalUnit getNumber(String target) throws IOException {
    while (true) {
      int next = reader.read();
      String n = String.valueOf((char) next);
      if ((target + n).matches(REG_NUMBER)) {
        target += n;
      } else if (next != -1) {
        reader.unread(next);
        break;
      }
    }
    if (target.matches(REG_INTVAL)) {
      return new LexicalUnit(LexicalType.INTVAL, new ValueImpl(target, ValueType.INTEGER));
    } else {
      return new LexicalUnit(LexicalType.DOUBLEVAL, new ValueImpl(target, ValueType.DOUBLE));
    }
  }

  private LexicalUnit getLiteral(String target) throws IOException {
    while (true) {
      int next = reader.read();
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

  private LexicalUnit getOperator(String target) throws IOException {
    while (true) {
      int next = reader.read();
      String n = String.valueOf((char) next);
      if (operator.containsKey(target + n)) {
        target += n;
      } else if (next != -1) {
        reader.unread(next);
        return operator.get(target);
      }
    }
  }

  private int skipBlank() throws IOException {
    int c;
    do {
      c = reader.read();
    } while (String.valueOf((char) c).matches(REG_BLANK));
    return c;
  }

  @Override
  public boolean expect(LexicalType type) throws Exception {
    return false;
  }

  @Override
  public void unget(LexicalUnit token) throws Exception {}
}
