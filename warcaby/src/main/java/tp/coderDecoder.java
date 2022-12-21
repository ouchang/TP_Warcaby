package tp;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

//abstract class Command {};
interface Command {
  String showView(); // TO DO: Delete this
};

class gameCommandClass implements Command {
  //String id;
  int actorId;
  int pieceId; // 0 - piece, 1 - king
  //String command;
  int fromX;
  int fromY;
  int toX;
  int toY;

  void setActorId(int actorId) {
    this.actorId = actorId;
  }

  int getActorId() {
    return this.actorId;
  }

  void setPieceId(int pieceId) {
    this.pieceId = pieceId;
  }

  int getPieceId() {
    return this.pieceId;
  }

  void setFromX(int fromX) {
    this.fromX = fromX;
  }

  int getFromX() {
    return fromX;
  }

  void setFromY(int fromY) {
    this.fromY = fromY;
  }

  int getFromY() {
    return fromY;
  }

  void setToX(int toX) {
    this.toX = toX;
  }

  int getToX() {
    return toX;
  }

  void setToY(int toY) {
    this.toY = toY;
  }

  int getToY() {
    return toY;
  }

  public String showView() { // TO DO: Delete this
    return actorId + "|" + fromX + "|" + fromY + "|" + toX + "|" + toY;
  }
}

class newGameClass implements Command {
  String actor;
  String gameKind;

  String getActor() {
    return actor;
  }

  void setActor(String actor) {
    this.actor = actor;
  }

  String getGameKind() {
    return gameKind;
  }

  void setGameKind(String gameKind) {
    this.gameKind = gameKind;
  }

  public String showView() {
    return actor + "|" + gameKind;
  }
}

class getCurrentGameStateClass implements Command {
  String id;
  String actor;

  public String showView() {
    return "!";
  }
}

class joinGameClass implements Command {
  String id;
  String actor;

  public String showView() {
    return "!";
  }
}

class getGamesClass implements Command {
  List<String> games;

  public String showView() {
    return "!";
  }
}

class gameStatusClass implements Command {
  String id;
  String friendly_name;
  String gameKind;
  String player1;
  String player2;
  String turn;
  String status;
  String error;
  String[][] board;

  void setId(String id) {
    this.id = id;
  }

  String getId() {
    return id;
  }

  void setFriendly_name(String friendly_name) {
    this.friendly_name = friendly_name;
  }

  String getFriendly_name() {
    return this.friendly_name;
  }

  void setPlayer1(String player1) {
    this.player1 = player1;
  }

  String getPlayer1() {
    return this.player1;
  }

  void setPlayer2(String player2) {
    this.player2 = player2;
  }

  String getPlayer2() {
    return this.player2;
  }

  void setGameKind(String gameKind) {
    this.gameKind = gameKind;
  }

  String getGameKind() {
    return gameKind;
  }

  void setTurn(String turn) {
    this.turn = turn;
  }

  String getTurn() {
    return this.turn;
  }

  void setStatus(String status) {
    this.status = status;
  }

  String getStatus() {
    return status;
  }

  void setError(String error) {
    this.error = error;
  }

  String getError() {
    return this.error;
  }

  void setBoard(String[][] board) {
    this.board = board;
  }

  String[][] getBoard() {
    return board;
  }

  public String showView() {
    return id + "|" + friendly_name + "|" + player1 + "|" + player2 + "|" +  gameKind + "|" + turn  + "|" + status + "|" + error + "|" + board;
  }
}

class CommandFactory {

  static Command getInstance(String type) { //
    switch(type) {
      case "gameStatus":
        return (new gameStatusClass());
      case "getGames":
        return (new getGamesClass());
      case "joinGame":
        return (new joinGameClass());
      case "getCurrentGameState":
        return (new getCurrentGameStateClass());
      case "newGame":
        return (new newGameClass());
      case "gameCommand":
        return (new gameCommandClass());
      default:
        return null;
    }
  }

  static Class getClass(String type) { //
    switch(type) {
      case "gameStatus":
        return (new gameStatusClass()).getClass();
      case "getGames":
        return (new getGamesClass()).getClass();
      case "joinGame":
        return (new joinGameClass()).getClass();
      case "getCurrentGameState":
        return (new getCurrentGameStateClass()).getClass();
      case "newGame":
        return (new newGameClass()).getClass();
      case "gameCommand":
        return (new gameCommandClass()).getClass();
      default:
        return null;
    }
  }
}

class CoderDecoder {
  /*
  newGameClass decodeNewGame(String json) {
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      newGameClass newGameObject = new newGameClass();
      newGameObject = mapper.readValue(json, newGameObject.getClass());
      return newGameObject;
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
  */

  Command decodeCommand(String json, String type) { //
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      Command command = CommandFactory.getInstance(type);
      command = mapper.readValue(json, CommandFactory.getClass(type));
      return command;
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    } catch(IOException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
  

  String codeCommand(Command object) { // void
    System.out.println("CODER DECODER");
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
      String commandAsString;
      //mapper.writeValue(new File("/home/ola/WORKSPACE/pwr/warcaby_tp/warcaby/src/main/java/tp/output.json"), object);
      System.out.println("BEFORE WRITING OBJECT AS STRING");
      commandAsString = mapper.writeValueAsString(object);
      System.out.println("AFTER WRITING OBJECT AS STRING: " + commandAsString);
      return commandAsString;
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    } catch(IOException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
}
