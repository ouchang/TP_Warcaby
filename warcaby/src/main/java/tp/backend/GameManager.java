package tp.backend;

import java.net.*;
import java.io.*;

public class GameManager implements Runnable {
  GameNew game;
  private Socket client;
  private CoderDecoder CD;
  private GameKindFactory gameKindFactory;

  GameManager(GameNew game, Socket client) {
    this.game = game;
    this.client = client;
    this.CD = new CoderDecoder();
    this.gameKindFactory = new GameKindFactory();
  }

  private void clientInitHandler(ClientInit clientInit, PrintWriter clientOut) {
    String playerId;

    synchronized(game) {
      playerId = game.addPlayer();
    }

    clientInit.setPlayerId(playerId);

    if(playerId == game.getPlayer1ID()) {
      clientInit.setFirstPlayer(true);
      clientInit.setColour("WHITE");
    } else {
      clientInit.setFirstPlayer(false);
      clientInit.setColour("BLACK");
    }

    String objectSerialized = CD.codeCommand(clientInit);
    clientOut.println(objectSerialized);

    System.out.println("Sever sends ClientInit [" + playerId + "]");
  }

  private void gameKindHandler(GameKind gameKind) {
    synchronized(game) {
      if(gameKind.getPlayerId().equals(game.getPlayer1ID())) {
        IGameKind kind = gameKindFactory.getGameKind(gameKind.getKind());
        game.setGameKind(kind);
        game.loadInitBoard();
      }
    }

    System.out.println("Client [" + gameKind.getPlayerId() + "] set gameKind = " + gameKind.getKind());
  }

  private void gameStatusHandler(GameStatus gameStatus, PrintWriter clientOut, Movement moveOutput) {
    synchronized(game) {
      gameStatus.setGameKind(game.getGameKindName());
      gameStatus.setPlayer1(game.getPlayer1ID());
      gameStatus.setPlayer2(game.getPlayer2ID());
      gameStatus.setActivePlayerID(game.getActivePlayerID());
      gameStatus.setBoard(game.getBoard());
      gameStatus.setError(""); // TO DO: Check me

      if(moveOutput != null) {
        gameStatus.setError(moveOutput.getErrorMessage()); // TO DO: Check me
        gameStatus.setCapturedFigures(moveOutput.getCapturedFigures());
      }

    }

    String objectSerialized = CD.codeCommand(gameStatus);
    clientOut.println(objectSerialized);
    System.out.println("Client [" + gameStatus.getPlayerId() + "]  gets gameStatus: " + objectSerialized);
  }

  private void moveCommandHandler(MoveCommand moveCommand, PrintWriter clientOut) {
    String errorMessage = "";
    Movement moveOutput = new Movement();
    synchronized(game) {
      if(moveCommand.getPlayerId().equals(game.getActivePlayerID())) {
        moveOutput = game.move(moveCommand.getPositions(), moveCommand.getPlayerId());
        System.out.println("Client [" + moveCommand.getPlayerId() + "]  makes move from: " + moveCommand.getPositions().get(0) + " to: " + moveCommand.getPositions().get(1));
      }
    }

    gameStatusHandler(new GameStatus(), clientOut, moveOutput);
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
          gameKindHandler(gameKind);
          break;
        case "GameStatus":
          GameStatus gameStatus = (GameStatus) CD.decodeCommand(clientCallSerialized);
          gameStatusHandler(gameStatus, clientOut, null);
          break;
        case "MoveCommand":
          MoveCommand moveCommand = (MoveCommand) CD.decodeCommand(clientCallSerialized);
          moveCommandHandler(moveCommand, clientOut);
          break;
      }

    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }
}