package newlang4;

public class Node {
  NodeType type;
  Environment env;

  public Node() {}

  public Node(NodeType type) {
    this.type = type;
  }

  public Node(Environment env) {
    this.env = env;
  }

  public NodeType getType() {
    return type;
  }

  public boolean parse() throws Exception {
    return true;
  }

  public Value getValue() throws Exception {
    return null;
  }

  public String toString() {
    if (type == NodeType.END) return "END";
    else return "Node";
  }
}
