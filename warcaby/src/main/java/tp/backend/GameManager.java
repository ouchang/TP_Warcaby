package tp.backend;

import java.net.*;
import java.io.*;
import java.util.List;

/**
 * MVC - Controller
 * 
 * Class responsible for handling player's requests to model layer (Server Class, Game Class)
 */
public class GameManager implements Runnable {
  GameNew game;
  private Socket client;
  private CoderDecoder CD;
  private GameKindFactory gameKindFactory;

  /**
   * Constructor
   * @param game object of Game model
   * @param client connected client's socket
   */
  GameManager(GameNew game, Socket client) {
    this.game = game;
    this.client = client;
    this.CD = new CoderDecoder();
    this.gameKindFactory = new GameKindFactory();
  }

  /**
   * Method handling initial client/player's connection 
   * @param clientInit command send by client
   * @param clientOut outbound stream to client
   */
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

  /**
   * Method handling game's kind settings
   * @param gameKind game's kind set by client
   */
  private void gameKindHandler(GameKind gameKind) {
    synchronized(game) {
      if(gameKind.getPlayerId().equals(game.getPlayer1ID())) {
        // Design Pattern - factory
        IGameKind kind = gameKindFactory.getGameKind(gameKind.getKind());
        game.setGameKind(kind);
        game.loadInitBoard();
      }
    }

    System.out.println("Client [" + gameKind.getPlayerId() + "] set gameKind = " + gameKind.getKind());
  }

  /**
   * Method handling game's status propagation
   * @param gameStatus game's status
   * @param clientOut outbound stream to client
   * @param moveOutput (in case client ask for game's status after making a move) output info about client's move
   */
  private void gameStatusHandler(GameStatus gameStatus, PrintWriter clientOut, Movement moveOutput) {
    synchronized(game) {
      gameStatus.setGameKind(game.getGameKindName());
      gameStatus.setPlayer1(game.getPlayer1ID());
      gameStatus.setPlayer2(game.getPlayer2ID());
      gameStatus.setActivePlayerID(game.getActivePlayerID());
      gameStatus.setBoard(game.getBoard());
      gameStatus.setError("");

      if(moveOutput != null) {
        gameStatus.setError(moveOutput.getErrorMessage());
        gameStatus.setCapturedFigures(moveOutput.getCapturedFigures());
      }

    }

    String objectSerialized = CD.codeCommand(gameStatus);
    clientOut.println(objectSerialized);
  }

  /**
   * Method handling board piece's movement
   * @param moveCommand client's move command
   * @param clientOut outbound stream to client
   */
  private void moveCommandHandler(MoveCommand moveCommand, PrintWriter clientOut) {
    Movement moveOutput = new Movement();
    synchronized(game) {
      if(moveCommand.getPlayerId().equals(game.getActivePlayerID())) {
        moveOutput = game.move(moveCommand.getPositions(), moveCommand.getPlayerId());
        System.out.println("Client [" + moveCommand.getPlayerId() + "]  makes move from: " + moveCommand.getPositions().get(0) + " to: " + moveCommand.getPositions().get(1));
      }
    }

    gameStatusHandler(new GameStatus(), clientOut, moveOutput);
  }


  private void gameRecordedHandler(GameRecorded gameRecorded, PrintWriter clientOut) {
    List<String> serializedGameStatus = game.loadRecordedGame(gameRecorded.getGameID());
    gameRecorded.setSerializedGameStatus(serializedGameStatus);

    System.out.println("Receiving Recorded Game");
    for(String s : serializedGameStatus) {
      System.out.println("S: " + s);
    }

    String serializedGameRecorded = CD.codeCommand(gameRecorded);
    System.out.println("Serialized Recorded Game: " + serializedGameRecorded);
    clientOut.println(serializedGameRecorded);
  }

  private void gameIDsRecordedHandler(GameIDsRecorded gameIDsRecorded, PrintWriter clientOut) {
    List<String> serializedGameIDsRecorded = game.loadRecordedGameIDs();
    gameIDsRecorded.setSerializedRecordedGameIDs(serializedGameIDsRecorded);

    System.out.println("Receiving Recorded GameIDs");
    for(String s : serializedGameIDsRecorded) {
      System.out.println("S: " + s);
    }

    String objectSerialized = CD.codeCommand(gameIDsRecorded);
    System.out.println("Serialized Recorded Game: " + objectSerialized);
    clientOut.println(objectSerialized);
  }

  /**
   * Starting method for threads
   */
  public void run() {
    try {

      InputStream input = client.getInputStream();
      BufferedReader clientIn = new BufferedReader(new InputStreamReader(input));

      OutputStream output = client.getOutputStream();
      PrintWriter clientOut = new PrintWriter(output, true);

      String clientCallSerialized;

      // read client's request
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
        case "GameRecorded":
          GameRecorded gameRecorded = (GameRecorded) CD.decodeCommand(clientCallSerialized);
          gameRecordedHandler(gameRecorded, clientOut);
          break;
        case "GameIDsRecorded": 
          GameIDsRecorded gameIDsRecorded = (GameIDsRecorded) CD.decodeCommand(clientCallSerialized);
          gameIDsRecordedHandler(gameIDsRecorded, clientOut);
          break;
      }

    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
