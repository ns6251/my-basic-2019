package newlang4;

public class ValueImpl extends Value {
  String val;
  ValueType type;

  public ValueImpl(String s, ValueType t) {
    this.val = s;
    this.type = t;
  }

  public ValueImpl(boolean b) {}

  public ValueImpl(double d) {}

  public ValueImpl(int i) {}

  public ValueImpl(String s) {
    this.val = s;
  }

  @Override
  public String get_sValue() {
    return null;
  }

  @Override
  public String getSValue() {
    return this.val;
  }

  @Override
  public int getIValue() {
    return 0;
  }

  @Override
  public double getDValue() {
    return 0;
  }

  @Override
  public boolean getBValue() {
    return false;
  }

  @Override
  public ValueType getType() {
    return null;
  }
}
