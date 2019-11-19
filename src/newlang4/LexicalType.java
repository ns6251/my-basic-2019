package newlang4;

public enum LexicalType {
  LITERAL, // 文字列定数（例："文字列"）
  INTVAL, // 整数定数（例：3）
  DOUBLEVAL, // 小数点定数（例：1.2）
  NAME, // 変数（例：i）
  IF, // IF
  THEN, // THEN
  ELSE, // ELSE
  ELSEIF, // ELSEIF
  ENDIF, // ENDIF
  FOR, // FOR
  FORALL, // FORALL
  NEXT, // NEXT
  EQ, // =
  LT, // <
  GT, // >
  LE, // <=, =<
  GE, // >=, =>
  NE, // <>
  FUNC, // SUB
  DIM, // DIM
  AS, // AS
  END, // END
  NL, // 改行
  DOT, // .
  WHILE, // WHILE
  DO, // DO
  UNTIL, // UNTIL
  ADD, // +
  SUB, // -
  MUL, // *
  DIV, // /
  LP, // )
  RP, // (
  COMMA, // ,
  LOOP, // LOOP
  TO, // TO
  WEND, // WEND
  BOOLVAL, // TRUE, FALSE
  EOF, // end of file
}
