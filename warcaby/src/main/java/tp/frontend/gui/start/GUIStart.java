package tp.frontend.gui.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

        ClientNew player = new ClientNew();
        player.clientInit(); // pierwsze polaczenie z serwerem

        FXMLLoader fxmlLoader = new FXMLLoader ( getClass().getResource("GuiXml.fxml"));

        GameStatus gameStatus = new GameStatus();

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

        Scene scene = new Scene ( fxmlLoader.load (), 800, 600 );
        GUIController controller = fxmlLoader.getController();
        controller.setPlayer(player); //NEW

        stage.setTitle ( "CHECKERS" );
        stage.setScene ( scene );
        stage.setResizable ( false );
        stage.show ();
    }
}
