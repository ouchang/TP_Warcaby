package tp.frontend.gui.start;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import tp.backend.Client;
import tp.frontend.gui.gametype.TypesController;
import tp.frontend.gui.start.GUIbehaviour;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import static tp.backend.Client.gametype;

public class GUIStart extends Application implements Runnable {
//    public String board[][] = "[[\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"],[\"0\",\"0\",\"3\",\"0\",\"3\",\"0\",\"3\",\"0\",\"3\"],[\"0\",\"3\",\"0\",\"3\",\"0\",\"3\",\"0\",\"3\",\"0\"],[\"0\",\"0\",\"3\",\"0\",\"3\",\"0\",\"3\",\"0\",\"3\"],[\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"],[\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"],[\"0\",\"1\",\"0\",\"1\",\"0\",\"1\",\"0\",\"1\",\"0\"],[\"0\",\"0\",\"1\",\"0\",\"1\",\"0\",\"1\",\"0\",\"1\"],[\"0\",\"1\",\"0\",\"1\",\"0\",\"1\",\"0\",\"1\",\"0\"]]";
    public String[][] boardString =
                {{"1", "1", "2", "3", "2", "1", "1", "1"},
                {"1", "1", "1", "1", "1", "1", "1", "1"},
                {"1", "1", "1", "1", "1", "1", "1", "1"},
                {"1", "1", "1", "1", "1", "1", "1", "1"},
                {"1", "1", "1", "1", "1", "1", "1", "1"},
                {"2", "2", "2", "2", "2", "2", "2", "2"},
                {"4", "4", "4", "4", "4", "4", "4", "4"},
                {"3", "3", "3", "3", "3", "3", "3", "3"}};
    public void updateBoard(String[][] boardString, GUIController controller){
        ObservableList<Node> childrenPane = controller.board8x8.getChildren();
        for (Node node : childrenPane){
            System.out.println(node.getId());
        }

        for (Node node : controller.board8x8.getChildren()) {

            if (node instanceof Pane) {
                Pane pane = (Pane) node;
                final ObservableList<Node> children = pane.getChildren();
                pane.getChildren().removeAll(children);
            }
        }


        for (int i = 0 ; i<8; i++){
            for (int j = 0; j<8; j++){
                switch (boardString[j][i]){

                    case "0":{
                        break;
                    }
                    case "1":{
                        Image image = new Image(getClass().getClassLoader().getResourceAsStream("simpleWhitePiece.png"));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight ( 40 );
                        imageView.setFitWidth ( 50 );

                        controller.board8x8.add(imageView, i, j);
                        break;
                    }
                    case "2":{
                        Image image = new Image(getClass().getClassLoader().getResourceAsStream("kingWhitePiece.png"));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight ( 40 );
                        imageView.setFitWidth ( 50 );
                        controller.board8x8.add(imageView, i, j);
                        break;
                    }
                    case "3":{
                        Image image = new Image(getClass().getClassLoader().getResourceAsStream("simpleBlackPiece.png"));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight ( 40 );
                        imageView.setFitWidth ( 50 );
                        controller.board8x8.add(imageView, i, j);
                        break;
                    }
                    case "4":{
                        Image image = new Image(getClass().getClassLoader().getResourceAsStream("kingBlackPiece.png"));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight ( 40 );
                        imageView.setFitWidth ( 50 );
                        controller.board8x8.add(imageView, i, j);
                        break;
                    }
                }
            }
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
//     Maria
        FXMLLoader board = new FXMLLoader( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guixml.fxml" ).toUri().toURL () );
        FXMLLoader wait = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\gametype\\wait.fxml" ).toUri().toURL () );
        FXMLLoader types = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\gametype\\types.fxml" ).toUri().toURL () );
//      Ola
//        FXMLLoader fxmlLoader = new FXMLLoader ( getClass().getResource("file:/guixml.fxml"));


//        if (Client.playerId == 1){
//            Scene scene = new Scene ( types.load (), 800, 600 );
//            TypesController controller = types.getController ();
//            if (controller == null){
//                System.out.println ("controller is null");
//            } else {
//                System.out.println("pomiedzy");
//                stage.setTitle("Welcome to checkers!");
//                updateBoard(boardString);
//                stage.setScene(scene);
//                stage.setResizable(false);
//                stage.show();
//            }
//        } else {

//            Scene scene = new Scene(wait.load(), 800, 600);
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
            updateBoard(boardString, controller);
            stage.setResizable(false);
            stage.show();
//            GUIStart ex = new GUIStart();
//            Thread a = new Thread(ex);
//            a.start();


//        }
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
