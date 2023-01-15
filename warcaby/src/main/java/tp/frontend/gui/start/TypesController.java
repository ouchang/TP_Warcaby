package tp.frontend.gui.start;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tp.backend.ClientNew;

import java.io.IOException;
import java.nio.file.Paths;

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
        System.out.println("choose type");
        System.out.println(event.getSource());

        
        if (czech.equals(event.getSource())) {
            this.player.setGameKind("czech");
        } else if (swedish.equals(event.getSource())){
            this.player.setGameKind("swedish");
        } else if (german.equals(event.getSource())) {
            this.player.setGameKind("german");
        } else {
            //player.setGameKind("nieokreslony");
            return;
        }
//        System.out.println(this.player.getGameKind());
        this.player.sendGameKind();

        FXMLLoader board = new FXMLLoader ( getClass().getResource("GuiXml.fxml"));
        Scene secondScene = new Scene( board.load(), 800, 600);
        GUIController controller = board.getController();
        controller.setPlayer(this.player); //NEW

        stage.setTitle("WHITE");
        stage.setScene(secondScene);
        stage.show();
    }
}
//todo okno powitalne dla drugiego gracza
//todo rotate plansza
//todo zaznaczyc klikniete pole
//todo info czyja tura + blokowanie planszy
