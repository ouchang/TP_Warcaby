package tp.backend;

import tp.backend.position.Position;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

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
    capturedFigures.add(cf);
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public boolean isEqual(String kind, boolean correctMove, List<Position> capturedFigures, String errorMessage) {
    if(kind.equals(this.kind)) {
      if(correctMove == this.correctMove) {
        if(errorMessage.equals(this.errorMessage)) {

          Iterator<Position> itr = this.capturedFigures.iterator();
          Position q;

          for(Position p : capturedFigures){
            itr = this.capturedFigures.iterator();
            while(itr.hasNext()) {
              q = itr.next();
              if(q.getX() == p.getX() && q.getY() == p.getY()) {
                itr.remove();
              }
            }
          }
          
          if(this.capturedFigures.size() == 0) {
            return true;        
          }       
        } 
      }
    }

    return false;
  }
}
