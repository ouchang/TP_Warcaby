package tp;


import java.util.List;
import java.util.ArrayList;

public class Movement {
  private String kind; // possible values: "REGULAR", "CAPTURE"
  private boolean correctMove;
  //private int capturedFigureX;
  //private int capturedFigureY;  
  private List<Position> capturedFigures;
  private String errorMessage;

  Movement() {
    this.kind = "";
    this.correctMove = false;
    //this.capturedFigureX = 0;
    //this.capturedFigureY = 0;
    this.capturedFigures = new ArrayList<Position>();
    this.errorMessage = "";
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
/*
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
*/

  public List<Position> getCapturedFigures() {
    return this.capturedFigures;
  }

  public void setCapturedFigures(List<Position> capturedFigures) {
    this.capturedFigures = capturedFigures;
  }

  public void addCapturedFigure(Position cf) {
    this.capturedFigures.add(cf);
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
