package tp.frontend.gui.start;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import tp.backend.ClientNew;
import tp.backend.GameStatus;
import tp.frontend.gui.gametype.TypesController;

import java.nio.file.Paths;

public class GUIStart extends Application {
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

//      Maria zmiany:
        FXMLLoader board = new FXMLLoader( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guixml.fxml" ).toUri().toURL () );
        FXMLLoader wait = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\gametype\\wait.fxml" ).toUri().toURL () );
        FXMLLoader types = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\gametype\\types.fxml" ).toUri().toURL () );

        ClientNew player = new ClientNew();
        player.clientInit(); // pierwsze polaczenie z serwerem

        if (player.getFirstPlayer() == true){

            Scene scene = new Scene ( types.load (), 800, 600 );
            TypesController controller = types.getController ();
            stage.setTitle("Welcome to checkers!");
//            updateBoard(boardString);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } else {
            // todo: rozważyć opcje dolączenia więcej jak dwóch graczy
            Scene scene = new Scene ( board.load (), 800, 600 );
            stage.setTitle("BLACK");
            GUIController controller = board.getController();
            // kolejne 6 linijek to obracanie planszy, zauważ że one nie zmieniają logiki gry (zachowują indeksy pól)
            controller.board8x8.setRotate(180);
            ObservableList<Node> childrenPane = controller.board8x8.getChildren();
            for (Node node : childrenPane){
                System.out.println(node.getId());
                node.setRotate(180);
            }
//                updateBoard(boardString);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }





//        FXMLLoader fxmlLoader = new FXMLLoader ( getClass().getResource("GuiXml.fxml"));

//        GameStatus gameStatus;
//
//        if(player.getFirstPlayer()) {
//            System.out.println("GUI sends gameKind");
//            // TO DO: Remove me!
//            player.setGameKind("czech");
//
//            player.sendGameKind();
//            gameStatus = player.getGameStatus();
//        } else {
//            gameStatus = player.getGameStatus();
//        }
//
//        Scene scene = new Scene ( fxmlLoader.load (), 800, 600 );
//        GUIController controller = fxmlLoader.getController();
//        controller.setPlayer(player); //NEW
//
//        stage.setTitle ( "CHECKERS" );
//        stage.setScene ( scene );
//        stage.setResizable ( false );
//        stage.show ();
    }
}
