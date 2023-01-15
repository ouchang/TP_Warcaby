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

public class GUIStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\guiversion\\warcaby\\src\\main\\java\\tp\\frontend\\GuiXml.fxml" ).toUri().toURL () );
//      Maria
//        FXMLLoader fxmlLoader = new FXMLLoader ( getClass ().getResource ( "GuiXml.fxml" ));
//        Test tees1 = new Test ();
//        FXMLLoader fxmlLoader = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\guiversion\\warcaby\\src\\main\\java\\tp\\frontend\\gui_xml.fxml" ).toUri().toURL () );
//      Ola

        FXMLLoader board = new FXMLLoader ( getClass().getResource("GuiXml.fxml"));
        FXMLLoader wait = new FXMLLoader ( getClass().getResource("wait.fxml"));
        FXMLLoader types = new FXMLLoader ( getClass().getResource("types.fxml"));

        ClientNew player = new ClientNew();
        player.clientInit(); // pierwsze polaczenie z serwerem

        FXMLLoader fxmlLoader = new FXMLLoader ( getClass().getResource("GuiXml.fxml"));

        GameStatus gameStatus = new GameStatus();
/*
        if(player.getFirstPlayer()) {
            System.out.println("GUI sends gameKind");

            // TO DO: Remove me!
            //Thread.sleep(10000);

            player.setGameKind("czech");

            player.sendGameKind();
            gameStatus = player.getGameStatus();
        } else {
            String tmp = player.getPollingAgent().getGameStatus().getGameKind();
            while(tmp.equals("")) {
                gameStatus = player.getPollingAgent().getGameStatus();
                tmp = gameStatus.getGameKind();
                Thread.sleep(500);
            }
        }
*/
        //MARIA VER
        if (player.getFirstPlayer() == true) {
            System.out.println("GUI sends gameKind");

            Scene scene = new Scene ( types.load(), 800, 600 );
            TypesController typesController = types.getController();
            typesController.setPlayer(player);

            stage.setTitle("Welcome to checkers!");
//            updateBoard(boardString);
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
            controller.setPlayer(player); //NEW
            // kolejne 6 linijek to obracanie planszy, zauważ że one nie zmieniają logiki gry (zachowują indeksy pól)
            controller.board8x8.setRotate(180);
            ObservableList<Node> childrenPane = controller.board8x8.getChildren();
            for (Node node : childrenPane){
                //System.out.println(node.getId());
                node.setRotate(180);
            }
//                updateBoard(boardString);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        //END OF MARIA VER


/*
        Scene scene = new Scene ( fxmlLoader.load (), 800, 600 );
        GUIController controller = fxmlLoader.getController();
        controller.setPlayer(player); //NEW

        stage.setTitle ( "CHECKERS" );
        stage.setScene ( scene );
        stage.setResizable ( false );
        stage.show ();
*/
    }
}
