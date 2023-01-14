package tp.backend;

import java.net.*;
import java.io.*;

public class GameManager implements Runnable {
  GameNew game;
  private Socket client;
  private CoderDecoder CD;

  GameManager(GameNew game, Socket client) {
    this.game = game;
    this.client = client;
    this.CD = new CoderDecoder();
  }

  private void clientInitHandler(ClientInit clientInit, PrintWriter clientOut) {
    String playerId;

    synchronized(game) {
      playerId = game.addPlayer();
    }

    clientInit.setPlayerId(playerId);

    if(playerId == game.getPlayer1D()) {
      clientInit.setFirstPlayer(true);
      clientInit.setColour("WHITE");
    } else {
      clientInit.setFirstPlayer(false);
      clientInit.setColour("BLACK");
    }

    String objectSerialized = CD.codeCommand(clientInit);
    clientOut.println(objectSerialized);
  }

  public void run() {
    try {

      InputStream input = client.getInputStream();
      BufferedReader clientIn = new BufferedReader(new InputStreamReader(input));

      OutputStream output = client.getOutputStream();
      PrintWriter clientOut = new PrintWriter(output, true);

      String clientCallSerialized;

      clientCallSerialized = clientIn.readLine();
      String clientCallType = CD.decodeCall(clientCallSerialized);

      switch(clientCallType) {
        case "ClientInit":
          ClientInit clientInit = (ClientInit) CD.decodeCommand(clientCallSerialized);
          clientInitHandler(clientInit, clientOut);
          break;
        case "GameKind":
          GameKind gameKind = (GameKind) CD.decodeCommand(clientCallSerialized);
          //method
          break;
      }

    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
