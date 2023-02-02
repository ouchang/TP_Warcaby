package tp.backend;

import java.io.*;
import java.net.*;
import java.util.List;

/**
 * MVC - Controller
 * 
 * Connection and sending commands client's manager class
 */
public class ClientNew {
  private CoderDecoder CD;
  private CommandFactory commandFactory;

  private String playerId;
  private String color;
  private boolean firstPlayer;
  private String gameKind;
  public boolean isRecordedGameLoaded = false;
  public List<String> serializedGameStatus;
  public PollingAgent pollingAgent;

  /**
   * Constructor
   */
  public ClientNew() {
    this.CD = new CoderDecoder();
    this.gameKind = "czech";
  }

  // Getters & Setter for class's attributes

  public void setIsRecordedGameLoaded(boolean is) {
    this.isRecordedGameLoaded = is;
  }

  public boolean getIsRecordedGameLoaded() {
    return this.isRecordedGameLoaded;
  }

  public void setSerializedGameStatus(List<String> list) {
    this.serializedGameStatus = list;
  }

  public List<String> getSerializedGameStatus() {
    return this.serializedGameStatus;
  }

  public String getPlayerId() {
    return this.playerId;
  }

  public boolean getFirstPlayer() {
    return this.firstPlayer;
  }

  public void setGameKind(String gameKind) {
    this.gameKind = gameKind;
  }

  public String getGameKind() {
    return this.gameKind;
  }

  public PollingAgent getPollingAgent() {
    return this.pollingAgent;
  }

  /**
   * Clients sends asynchronously commands to Server
   * @param object command object that is sent
   */
  public void clientCallAsync(ICommand object) {
    String objectSerialized = CD.codeCommand(object);
    Socket socket = null;
    PrintWriter clientOut = null;

    try {
      socket = new Socket("localhost", 4444);

      clientOut = new PrintWriter(socket.getOutputStream(), true);
    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    // send serialized command to Server
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

  /**
   * Fully synchronized connection Client to Server
   * (send & receive command)
   * @param object command object that is sent
   * @return server's output
   */
  public ICommand clientCallSync(ICommand object) {
    String objectSerialized = CD.codeCommand(object);
    Socket socket = null;
    PrintWriter clientOut = null;
    BufferedReader clientIn = null;

    try {
      socket = new Socket("localhost", 4444);
      
      clientOut = new PrintWriter(socket.getOutputStream(), true);
      clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch(IOException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    // send client's command
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

    // receive server's output
    String clientCallType = CD.decodeCall(objectSerialized);

    switch(clientCallType) {
      case "ClientInit":
        ClientInit clientInit = (ClientInit) CD.decodeCommand(objectSerialized);
        return clientInit;
      case "GameStatus":
        GameStatus gameStatus = (GameStatus) CD.decodeCommand(objectSerialized);
        return gameStatus;
      case "GameRecorded":
        GameRecorded gameRecorded = (GameRecorded) CD.decodeCommand(objectSerialized);
        return gameRecorded;
      case "GameIDsRecorded":
        GameIDsRecorded gameIDsRecorded = (GameIDsRecorded) CD.decodeCommand(objectSerialized);
        return gameIDsRecorded;
    }

    return null;
  }

  /**
   * Registering client to the game
   */
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

    // start PollingAgent
    pollingAgent = new PollingAgent(this);
    new Thread(pollingAgent).start();
  }

  /**
   * Setting the game's kind
   */
  public void sendGameKind() {
    GameKind gameKindObject = (GameKind) commandFactory.getCommand("GameKind");
    gameKindObject.setPlayerId(this.playerId);
    gameKindObject.setKind(this.gameKind);
    clientCallAsync(gameKindObject);
  }

  /**
   * Request for current game's status
   * @return current game's status
   */
  public GameStatus getGameStatus() {
    GameStatus gameStatus = (GameStatus) commandFactory.getCommand("GameStatus");
    gameStatus.setPlayerId(this.playerId);
    gameStatus = (GameStatus) clientCallSync(gameStatus);
    return gameStatus;
  }
  /**
   * Sending movement's command to be validated by Server
   * @param positions clicked positions on game's board by client
   * @return game's status after movement's validation
   */
  public GameStatus sendMoveCommand(List<Position> positions) {
    MoveCommand moveCommand = (MoveCommand) commandFactory.getCommand("MoveCommand");
    moveCommand.setPlayerId(this.playerId);
    moveCommand.setPositions(positions);


    //Helper to check clicked positions
    System.out.println("Client [" + this.playerId + "] sends move with positions: ");
    for(Position p : positions) {
      System.out.println("  X: " + p.getX() + " Y: " + p.getY());
    }

    GameStatus gameStatus = (GameStatus) clientCallSync(moveCommand);
    return gameStatus;
  }

  public GameRecorded getGameRecorded(String gameID) {
    GameRecorded gameRecorded = (GameRecorded) commandFactory.getCommand("GameRecorded");
    gameRecorded.setGameID(gameID);
    
    gameRecorded = (GameRecorded) clientCallSync(gameRecorded);

    return gameRecorded;
  }

  public GameIDsRecorded getGameIDsRecorded() {
    GameIDsRecorded gameIDsRecorded = (GameIDsRecorded) commandFactory.getCommand("GameIDsRecorded");

    gameIDsRecorded = (GameIDsRecorded) clientCallSync(gameIDsRecorded);
    return gameIDsRecorded;
  }

  /**
   * Agent class to handle polling requests of game's status
   * Implemented as Threads
   */
  public class PollingAgent implements Runnable {
    GameStatus gameStatus;
    ClientNew client;
    boolean stop;

    /**
     * Construtor
     * @param client client/player object to be updated (thread-safe)
     */
    PollingAgent(ClientNew client) {
      this.client = client;
      this.stop = false;
    }

    public synchronized GameStatus getGameStatus() {
      return this.gameStatus;
    }

    /**
     * Thread runner
     */
    public void run() {
      try {
        while(!stop) {
          synchronized(this) {
            this.gameStatus = client.getGameStatus();
          }
          Thread.sleep(500); //1000
        }

      } catch(InterruptedException e) {
        System.out.println(e.getMessage());
        System.exit(1);
      }
    }

    public void stop() {
      this.stop = true;
    }
  }
}
