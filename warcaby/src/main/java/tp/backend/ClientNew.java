package tp.backend;

import java.io.*;
import java.net.*;

public class ClientNew {
  private CoderDecoder CD;
  private CommandFactory commandFactory;

  private String playerId;
  private String color;
  private boolean firstPlayer;
  private String gameKind;

  public ClientNew() {
    this.CD = new CoderDecoder();
    this.gameKind = "czech";
  }

  public boolean getFirstPlayer() {
    return this.firstPlayer;
  }

  public void setGameKind(String gameKind) {
    this.gameKind = gameKind;
  }

  public void clientCallAsync(ICommand object) {
    String objectSerialized = CD.codeCommand(object);
    Socket socket = null;
    PrintWriter clientOut = null;

    try {
      socket = new Socket("localhost", 4444);

      // TO DO: Move this section to individual method in GUI class
      clientOut = new PrintWriter(socket.getOutputStream(), true);
    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    clientOut.println(objectSerialized);

    try {
      socket.close();

      if(clientOut != null) {
        clientOut.close();
      }

    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  public ICommand clientCallSync(ICommand object) {
    String objectSerialized = CD.codeCommand(object);
    Socket socket = null;
    PrintWriter clientOut = null;
    BufferedReader clientIn = null;

    try {
      socket = new Socket("localhost", 4444);

      // TO DO: Move this section to individual method in GUI class
      clientOut = new PrintWriter(socket.getOutputStream(), true);
      clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    clientOut.println(objectSerialized);

    try {
      objectSerialized = clientIn.readLine();
      socket.close();

      if(clientIn != null) {
        clientIn.close();
      }

      if(clientOut != null) {
        clientOut.close();
      }
    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    String clientCallType = CD.decodeCall(objectSerialized);

    switch(clientCallType) {
      case "ClientInit":
        ClientInit clientInit = (ClientInit) CD.decodeCommand(objectSerialized);
        return clientInit;
      case "GameStatus":
        GameStatus gameStatus = (GameStatus) CD.decodeCommand(objectSerialized);
        return gameStatus;
    }

    return null;
  }

  public void clientInit() {
    ClientInit clientInit = (ClientInit) commandFactory.getCommand("ClientInit");
    clientInit = (ClientInit) clientCallSync(clientInit);

    this.playerId = clientInit.getPlayerId();
    this.color = clientInit.getColor();
    this.firstPlayer = clientInit.getFirstPlayer();

    System.out.println("[" + this.playerId + "] Client registered!");
    if(this.firstPlayer) {
      System.out.println("[" + this.playerId + "] First privileged client");
    } else {
      System.out.println("[" + this.playerId + "] Second client");
    }
  }

  public void sendGameKind() {
    GameKind gameKindObject = (GameKind) commandFactory.getCommand("GameKind");
    gameKindObject.setPlayerId(this.playerId);
    gameKindObject.setKind(this.gameKind);
    clientCallAsync(gameKindObject);
  }

  public GameStatus getGameStatus() {
    GameStatus gameStatus = (GameStatus) commandFactory.getCommand("GameStatus");
    gameStatus.setPlayerId(this.playerId);
    gameStatus = (GameStatus) clientCallSync(gameStatus);
    System.out.println("Client [" + gameStatus.getPlayerId() + "]  receives gameStatus!");
    return gameStatus;
  }
}
