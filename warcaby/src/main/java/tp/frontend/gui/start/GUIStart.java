package tp.frontend.gui.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.collections.ObservableList;

import java.lang.Thread;

import tp.backend.ClientNew;
import tp.backend.GameStatus;

/**
 * MVC - View
 */
public class GUIStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//      Maria
//        FXMLLoader fxmlLoader = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\guiversion\\warcaby\\src\\main\\java\\tp\\frontend\\GuiXml.fxml" ).toUri().toURL () );
//        FXMLLoader fxmlLoader = new FXMLLoader ( getClass ().getResource ( "GuiXml.fxml" ));
//        Test tees1 = new Test ();
//        FXMLLoader fxmlLoader = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\guiversion\\warcaby\\src\\main\\java\\tp\\frontend\\gui_xml.fxml" ).toUri().toURL () );

//      Ola
        FXMLLoader board = new FXMLLoader ( getClass().getResource("GuiXml.fxml"));
        FXMLLoader wait = new FXMLLoader ( getClass().getResource("wait.fxml"));
        FXMLLoader types = new FXMLLoader ( getClass().getResource("types.fxml"));

        ClientNew player = new ClientNew();
        player.clientInit(); // register client

        FXMLLoader fxmlLoader = new FXMLLoader ( getClass().getResource("GuiXml.fxml"));

        GameStatus gameStatus = new GameStatus();

        if (player.getFirstPlayer() == true) {
            System.out.println("GUI sends gameKind");

            Scene scene = new Scene ( types.load(), 800, 600 );
            TypesController typesController = types.getController();
            typesController.setPlayer(player);

            stage.setTitle("Welcome to checkers!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } else {
            String tmp = player.getPollingAgent().getGameStatus().getGameKind();
            while(tmp.equals("")) {
                gameStatus = player.getPollingAgent().getGameStatus();
                tmp = gameStatus.getGameKind();
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
