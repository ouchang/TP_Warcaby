package tp;

public class Movement {
  private String kind; // possible values: "REGULAR", "CAPTURE"
  private boolean correctMove;
  private int capturedFigureX;
  private int capturedFigureY;  

  Movement() {
    this.kind = "";
    this.correctMove = false;
    this.capturedFigureX = 0;
    this.capturedFigureY = 0;
  }

  public String getKind() {
    return this.kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public boolean getCorrectMove() {
    return this.correctMove;
  }

  public void setCorrectMove(boolean correctMove) {
    this.correctMove = correctMove;
  }

  public int getCapturedFigureX() {
    return this.capturedFigureX;
  }

  public void setCapturedFigureX(int capturedFigureX) {
    this.capturedFigureX = capturedFigureX;
  }
  
  public int getCapturedFigureY() {
    return this.capturedFigureY;
  }

  public void setCapturedFigureY(int capturedFigureY) {
    this.capturedFigureY = capturedFigureY;
  }
}
