package tp.backend;

public class CommandClass {
  private String type;
  private String value;

  public void setType(String type) {
    this.type = type;
  }

  public void setValue(String value) {
    this.value = value;
  }
  
  public String getType() {
    return this.type;
  }

  public String getValue() {
    return this.value;
  }
}
