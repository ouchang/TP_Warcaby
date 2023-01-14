package tp.backend;

import java.io.*;
import java.net.*;

public class ClientNew {
  
  private static PrintWriter clientOut = null;
  private static BufferedReader clientIn = null;

  private CoderDecoder CD;
  private CommandFactory commandFactory;

  private String playerId;
  private String color;
  private boolean firstPlayer;

  public ClientNew() {
    this.CD = new CoderDecoder();
  }

  public boolean getFirstPlayer() {
    return this.firstPlayer;
  }

  public void clientCallAsync(ICommand object) {
    String objectSerialized = CD.codeCommand(object);
    Socket socket = null;

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
    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  public ICommand clientCallSync(ICommand object) {
    String objectSerialized = CD.codeCommand(object);
    Socket socket = null;

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
    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    String clientCallType = CD.decodeCall(objectSerialized);

    switch(clientCallType) {
      case "ClientInit":
        ClientInit clientInit = (ClientInit) CD.decodeCommand(objectSerialized);
        return clientInit;
    }

    return null;
  }

  public void clientInit() {
    ClientInit clientInit = (ClientInit) commandFactory.getCommand("ClientInit");
    clientInit = (ClientInit) clientCallSync(clientInit);

    this.playerId = clientInit.getPlayerId();
    this.color = clientInit.getColor();
    this.firstPlayer = clientInit.getFirstPlayer();
  }

  public void setGameKind(String kind) {
    GameKind gameKind = (GameKind) commandFactory.getCommand("GameKind");
    gameKind.setPlayerId(this.playerId);
    gameKind.setKind(kind);
    clientCallAsync(gameKind);
  }
}
