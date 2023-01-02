package tp;

import java.io.*;
import java.net.*;

public class Server {
  private static int clientCounter=0;

  private static Socket firstClientSocket;
  private static Socket secondClientSocket;

  public static void main(String[] args) {
    try(ServerSocket serverSocket = new ServerSocket(4444)) {
      System.out.println("Turning on the server");
      CzechKind CG = new CzechKind();

      //while(true) {
        // Only 2 clients can play! (CHECKING POINT)

        if(clientCounter < 2) { 
          firstClientSocket = serverSocket.accept();
          System.out.println("First player (client) connected");
          System.out.println("Waiting for the second player");
          clientCounter++;

          secondClientSocket = serverSocket.accept();
          System.out.println("Second player (client) connected");
          clientCounter++;
        }
        
        // create GameKindFactory

        // create and start game thread -> pass firstClientSocket and secondClientSocket to thread
        Game game = new Game(firstClientSocket, secondClientSocket, CG);
        Thread gameThread = new Thread(game);
        gameThread.start();
      //}

    } catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
