package newlang3;

public class ValueImpl extends Value {
  String val;
  ValueType type;

  public ValueImpl(String s, ValueType t) {
    super(s, t);
    this.val = s;
    this.type = t;
  }

  public ValueImpl(boolean b) {
    super(b);
    // TODO 自動生成されたコンストラクター・スタブ
  }

  public ValueImpl(double d) {
    super(d);
    // TODO 自動生成されたコンストラクター・スタブ
  }

  public ValueImpl(int i) {
    super(i);
    // TODO 自動生成されたコンストラクター・スタブ
  }

  public ValueImpl(String s) {
    super(s);
    this.val = s;
  }

  @Override
  public String get_sValue() {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }

  @Override
  public String getSValue() {
    return this.val;
  }

  @Override
  public int getIValue() {
    // TODO 自動生成されたメソッド・スタブ
    return 0;
  }

  @Override
  public double getDValue() {
    // TODO 自動生成されたメソッド・スタブ
    return 0;
  }

  @Override
  public boolean getBValue() {
    // TODO 自動生成されたメソッド・スタブ
    return false;
  }

  @Override
  public ValueType getType() {
    // TODO 自動生成されたメソッド・スタブ
    return null;
  }
}
