package tp.frontend.gui.start;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import tp.backend.ClientNew;
import tp.frontend.gui.gametype.TypesController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

//import static tp.backend.Client.gametype;

public class GUIStart extends Application implements Runnable {

    @Override
    public void start(Stage stage) throws Exception {
//     Maria
//     FIX ME
        //FXMLLoader board = new FXMLLoader( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guixml.fxml" ).toUri().toURL () );
        //FXMLLoader wait = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\gametype\\wait.fxml" ).toUri().toURL () );
        //FXMLLoader types = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\gametype\\types.fxml" ).toUri().toURL () );
//      Ola
        FXMLLoader board = new FXMLLoader ( getClass().getResource("guixml.fxml"));
        FXMLLoader types = new FXMLLoader ( getClass().getResource("types.fxml"));
        FXMLLoader wait = new FXMLLoader ( getClass().getResource("wait.fxml"));

        ClientNew client = new ClientNew();
        client.clientInit();


        if (client.getFirstPlayer()){
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
            Scene scene = new Scene(board.load(), 800, 600);
            stage.setTitle("WAIT");
            GUIController controller = board.getController();
            controller.board8x8.setRotate(180);
            ObservableList<Node> childrenPane = controller.board8x8.getChildren();
            for (Node node : childrenPane){
                System.out.println(node.getId());
                node.setRotate(180);
            }
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
//            GUIStart ex = new GUIStart();
//            Thread a = new Thread(ex);
//            a.start();
        }
    }

    @Override
    public void run() {
        FXMLLoader board = null;
        //try {
            //FIXME
            //board = new FXMLLoader( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guixml.fxml" ).toUri().toURL () );
            
        //} catch (MalformedURLException e) {
            //throw new RuntimeException(e);
        //}

        board = new FXMLLoader ( getClass().getResource("guixml.fxml"));

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
