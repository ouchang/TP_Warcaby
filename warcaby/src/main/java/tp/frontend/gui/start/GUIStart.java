package tp.frontend.gui.start;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import tp.backend.Client;
import tp.frontend.gui.gametype.TypesController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import static tp.backend.Client.gametype;

public class GUIStart extends Application implements Runnable {

    @Override
    public void start(Stage stage) throws Exception {
//     Maria
        FXMLLoader wait = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\gametype\\wait.fxml" ).toUri().toURL () );
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
                stage.setTitle("Welcome to checkers!");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } else {

            System.out.println("before while");
            Scene scene = new Scene(wait.load(), 800, 600);
            stage.setTitle("WAIT");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            GUIStart ex = new GUIStart();
            Thread a = new Thread(ex);
            a.start();


        }
    }

    @Override
    public void run() {
        FXMLLoader board = null;
        try {
            board = new FXMLLoader( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guixml.fxml" ).toUri().toURL () );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("run");
        while (gametype == 0) {
            Thread.onSpinWait();
        }
        System.out.println("out while");
        Scene scene2 = null;
        try {
            scene2 = new Scene(board.load(), 800, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("BLACK");
        stage.setScene(scene2);
        stage.setResizable(false);
        System.out.println("before show");
        stage.show();
    }
}
