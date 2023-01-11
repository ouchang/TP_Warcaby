package tp.frontend.gui.start;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import tp.backend.Client;
import tp.frontend.gui.gametype.TypesController;

import java.nio.file.Paths;

import static tp.backend.Client.gametype;

public class GUIStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//     Maria
       FXMLLoader board = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guixml.fxml" ).toUri().toURL () );
       FXMLLoader types = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\gametype\\types.fxml" ).toUri().toURL () );
//      Ola
//        FXMLLoader fxmlLoader = new FXMLLoader ( getClass().getResource("file:/guixml.fxml"));

        if (Client.playerId == 1){
            Scene scene = new Scene ( types.load (), 800, 600 );
            TypesController controller = types.getController ();
            if (controller == null){
                System.out.println ("controller is null");
            } else {
                System.out.println("pomiedzy");

                stage.setTitle("Chose a type of a game");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } else {
            Scene scene = new Scene ( board.load (), 800, 600 );
            GUIController controller = board.getController ();
            if (controller == null){
                System.out.println ("controller is null");
            } else {
                System.out.println("pomiedzy");
                Reflection reflection = new Reflection();
                Rotate rotate = new Rotate();
                //Setting pivot points for the rotation
                rotate.setPivotX(300);
                rotate.setPivotY(100);

                stage.setTitle("BLACK");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        }

    }
}
