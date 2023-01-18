package tp.frontend.gui.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.collections.ObservableList;

import java.lang.Thread;
import java.nio.file.Paths;

import tp.backend.ClientNew;
import tp.backend.GameStatus;

/**
 * MVC - View
 */
public class GUIStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//      Maria
        FXMLLoader board = new FXMLLoader( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guifxml.fxml" ).toUri().toURL () );
        FXMLLoader types = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\types.fxml" ).toUri().toURL () );
//      Ola
//        FXMLLoader board = new FXMLLoader ( getClass().getResource("guifxml.fxml"));
//        FXMLLoader types = new FXMLLoader ( getClass().getResource("types.fxml"));

        ClientNew player = new ClientNew();
        player.clientInit(); // register client

        GameStatus gameStatus = new GameStatus();

        if (player.getFirstPlayer() == true) {
            System.out.println("GUI sends gameKind");

            Scene scene = new Scene ( types.load(), 800, 600 );
            TypesController setBoard = types.getController();
            setBoard.setPlayer(player);

            stage.setTitle("Welcome to checkers!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } else {
            String gameType = player.getPollingAgent().getGameStatus().getGameKind();
            while(gameType.equals("")) {
                gameStatus = player.getPollingAgent().getGameStatus();
                gameType = gameStatus.getGameKind();
                Thread.sleep(500);
            }

            // todo: rozważyć opcje dolączenia więcej jak dwóch graczy
            Scene scene = new Scene(board.load(), 800, 600 );
            stage.setTitle("BLACK");
            GUIController controller = board.getController();
            controller.setPlayer(player); 
            
            controller.board8x8.setRotate(180);
            ObservableList<Node> childrenPane = controller.board8x8.getChildren();
            for (Node node : childrenPane){
                node.setRotate(180);
            }
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }
}
