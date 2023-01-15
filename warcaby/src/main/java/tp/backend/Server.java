package tp.backend;

import java.io.*;
import java.net.*;

/**
 * MVC - Model
 * 
 * Server's class representation.
 * Entry point kind class for Server.
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

      int i = 0;
      while(true) {
        Socket client = serverSocket.accept();
        System.out.println("Connection nr " + String.valueOf(i));

        GameManager gameManager = new GameManager(game, client);
        new Thread(gameManager).start();
        i++;
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
