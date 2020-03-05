# Java based BASIC interpreter

講義で作成したBASIC（モドキ）インタプリタ。以下で示すBNFの定義に準じてパースを行う。不完全な実装であり、配列や関数定義が使えない。また、変数のスコープもすべてグローバル。

## Usage

- パッケージnewlang3では字句解析のみを行い、結果を簡易的に標準出力をする。
- newlang4では構文解析まで行い、結果を標準出力する。
- newlang5で実行まで行う。

それぞれのmainメソッドの引数にファイル名を渡して実行が可能。

## BNF

```xml
<program> ::=
  <stmt_list>

<stmt_list> ::=
  <stmt>
  | <stmt_list> <NL> <stmt>
  | <block>
  | <stmt_list> <block>

<block> ::=
  <if_prefix> <stmt> <NL>
  | <if_prefix> <stmt> <ELSE> <stmt> <NL>
  | <if_prefix> <NL> <stmt_list> <else_block> <ENDIF> <NL>
  | <WHILE> <cond> <NL> <stmt_list> <WEND> <NL>
  | <DO> <WHILE> <cond> <NL> <stmt_list> <LOOP> <NL>
  | <DO> <UNTIL> <cond> <NL> <stmt_list> <LOOP> <NL>
  | <DO> <NL> <stmt_list> <LOOP> <WHILE> <cond> <NL>
  | <DO> <NL> <stmt_list> <LOOP> <UNTIL> <cond> <NL>

<stmt> ::=
  <subst>
  | <call_sub>
  | <FOR> <subst> <TO> <INTVAL> <NL> <stmt_list> <NEXT> <NAME>
  | <END>

<varlist> ::=
  <var>
  | <varlist> <COMMA> <var>

<expr_list> ::=
  <expr>
  | <expr_list> <COMMA> <expr>

<if_prefix> ::=
  <IF> <cond> <THEN>

<else_block> ::=
  <else_if_block>
  | <else_if_block> <ELSE> <NL> <stmt_list>

<else_if_block> ::=
  φ
  | <else_if_block> <ELSEIF> <cond> <THEN> <NL> <stmt_list>

<subst> ::=
  <leftvar> <EQ> <expr>

<cond> ::=
  <expr> <EQ> <expr>
  | <expr> <GT> <expr>
  | <expr> <LT> <expr>
  | <expr> <GE> <expr>
  | <expr> <LE> <expr>
  | <expr> <NE> <expr>

<expr> ::= 
  <expr> <ADD> <expr>
  | <expr> <SUB> <expr>
  | <expr> <MUL> <expr>
  | <expr> <DIV> <expr>
  | <SUB> <expr>
  | <LP> <expr> <RP>
  | <NAME>
  | <INTVAL>
  | <DOUBLEVAL>
  | <LITERAL>
  | <call_func>

<var> ::=
  <NAME> <!-- normal variable -->

<leftvar> ::=
  <NAME> <!-- normal variable -->

<call_func> ::=
  <NAME> <LP> <expr_list> <RP>

<call_sub> ::=
  <NAME> <expr_list>

```
