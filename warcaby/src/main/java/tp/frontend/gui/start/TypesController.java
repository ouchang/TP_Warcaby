package tp.frontend.gui.start;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import tp.backend.ClientNew;

import java.io.IOException;

/**
 * MVC - Controller
 *
 *  Class managing the process of choosing a game type
 */
public class TypesController {
    @FXML
    private Button czech;

    @FXML
    private Button swedish;

    @FXML
    private Button german;

    @FXML
    private Pane textPane;

    @FXML
    public GridPane wallpapper;

    ClientNew player;

    public void setPlayer(ClientNew player) {
        this.player = player;
    }

    @FXML
    public void choseType(ActionEvent event) throws IOException {
        Stage stage = (Stage) czech.getScene().getWindow();
        System.out.println("choosing type");
        System.out.println(event.getSource());

        
        if (czech.equals(event.getSource())) {
            this.player.setGameKind("czech");
        } else if (swedish.equals(event.getSource())){
            this.player.setGameKind("swedish");
        } else if (german.equals(event.getSource())) {
            this.player.setGameKind("german");
        } else {
            return;
        }

        this.player.sendGameKind();
//      Maria
//        FXMLLoader board = new FXMLLoader( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guifxml.fxml" ).toUri().toURL () );
//      Ola
        FXMLLoader board = new FXMLLoader ( getClass().getResource("guifxml.fxml"));

        Scene secondScene = new Scene( board.load(), 800, 600);
        GUIController controller = board.getController();
        controller.setPlayer(this.player);

        stage.setTitle("WHITE");
        stage.setScene(secondScene);
        stage.show();
    }
}
