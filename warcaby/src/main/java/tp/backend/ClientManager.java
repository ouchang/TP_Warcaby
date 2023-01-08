package tp.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class ClientManager {
  InputStreamReader inRead;
  BufferedReader reader;
  int playerId;
  CoderDecoder CD;

  ClientManager(int playerId) {
    this.inRead = new InputStreamReader(System.in);
    this.reader = new BufferedReader(inRead);
    this.playerId = playerId;
    this.CD = new CoderDecoder();
  }

  public gameCommandClass makeMove(int pieceId, List<Position> positions) {
      gameCommandClass command = new gameCommandClass();
      command.setActorId(playerId);
      command.setPieceId(pieceId);
      command.setPositions(positions);
      return command;
  }

  public ICommand start() { //ICommand
    String typeOfMove;
    String isEndOfMove="";
    String typeOfFigure;
    String moveX, moveY;

    Position p = new Position();
    List<Position> positions = new ArrayList<Position>();

    try {
      System.out.println("Write type of move: (single / multi): ");
      typeOfMove = reader.readLine();
      System.out.println("Write id number of figure: (0 = piece / 1 = king): ");
      typeOfFigure = reader.readLine();
      
      while(isEndOfMove.equals("yes") == false) {
        p = new Position();
        System.out.println("Write starting position (index X): ");
        moveX = reader.readLine();
        p.setX(Integer.parseInt(moveX));
        System.out.println("Write starting position (index Y): ");
        moveY = reader.readLine();
        p.setY(Integer.parseInt(moveY));

        // add move to list of positions
        positions.add(p);

        System.out.println("Have you finished your move? (yes / no): ");
        isEndOfMove = reader.readLine();
      }

      System.out.println("OK! We received your move info! Let's make a move!");
      return makeMove(Integer.parseInt(typeOfFigure), positions);
    } catch(IOException e) {
      System.out.println( e.getMessage());
    }

    return null;
  }
}
