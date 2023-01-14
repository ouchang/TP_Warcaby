package tp.backend;

import java.io.*;
import java.net.*;

/**
 * Server's class representation.
 * Within this class, server socket listens on a given port.
 */
public class Server {
  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    GameNew game = new GameNew();

    try {
      serverSocket = new ServerSocket(4444);
      serverSocket.setReuseAddress(true);

      System.out.println("Turning on the server");

      //Game game = new Game(firstClientSocket, secondClientSocket, CG);
      //Thread gameThread = new Thread(game);
      //gameThread.start();

      //NEW VERSION

      while(true) {
        Socket client = serverSocket.accept();

        GameManager gameManager = new GameManager(game, client);
        new Thread(gameManager).start();
      }
    } catch(IOException e) {
      System.out.println(e.getMessage());
    } finally {
      if(serverSocket != null) {
        try {
          serverSocket.close();
        } catch(IOException e) {
          System.out.println(e.getMessage());
          System.exit(1);
        }
      }
    }
  }
}
