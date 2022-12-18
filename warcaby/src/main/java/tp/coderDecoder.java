package tp;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

//abstract class Command {};
interface Command {
  String showView();
};

class gameCommandClass implements Command {
  String id;
  String actor;
  String command;
  String from;
  String to;

  public String showView() {
    return "!";
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
  String player1;
  String player2;
  String turn;
  String status;
  String error;
  String[][] board;

  public String showView() {
    return "!";
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

  Command decodeCommand(File json, String type) { //
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
  

  void codeCommand(Command object) {
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
      String commandAsString;
      mapper.writeValue(new File("/home/ola/WORKSPACE/pwr/warcaby_tp/warcaby/src/main/java/tp/output.json"), object);
      //commandAsString = mapper.writeValueAsString(object);
      //return commandAsString;
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    } catch(IOException e) {
      System.out.println(e.getMessage());
    }

    //return null;
  }
}
