package tp.backend;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class representing movement's output information
 * It is used during the process of movement's check
 */
public class Movement {
  private String kind; // possible values: "REGULAR", "CAPTURE"
  private boolean correctMove;
  private List<Position> capturedFigures;
  private String errorMessage;

  public Movement() {
    this.kind = "";
    this.correctMove = false;
    this.capturedFigures = new ArrayList<Position>();
    this.errorMessage = "";
  }

  // Setter & Getters

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

  /**
   * Method used to check if 2 Movement's objects are equal
   * @param kind object's kind attribute
   * @param correctMove object's info about the movement's correctness attribute
   * @param capturedFigures object's list of captured figures attribute
   * @param errorMessage object's error's message attribute
   * @return are those 2 objects equal?
   */
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
