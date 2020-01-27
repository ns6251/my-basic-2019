package newlang5;

public class ValueImpl extends Value {
  String val;
  ValueType type;

  public ValueImpl(String s, ValueType t) {
    this.val = s;
    this.type = t;
  }

  public ValueImpl(boolean b) {
    this.val = b + "";
    this.type = ValueType.BOOL;
  }

  public ValueImpl(double d) {
    this.val = d + "";
    this.type = ValueType.DOUBLE;
  }

  public ValueImpl(int i) {
    this.val = i + "";
    this.type = ValueType.INTEGER;
  }

  public ValueImpl(String s) {
    this.val = s;
    this.type = ValueType.STRING;
  }

  @Override
  public String getSValue() {
    return this.val;
  }

  @Override
  public int getIValue() {
    return Integer.parseInt(val);
  }

  @Override
  public double getDValue() {
    return Double.parseDouble(val);
  }

  @Override
  public boolean getBValue() {
    return Boolean.parseBoolean(val);
  }

  @Override
  public ValueType getType() {
    return this.type;
  }
}
