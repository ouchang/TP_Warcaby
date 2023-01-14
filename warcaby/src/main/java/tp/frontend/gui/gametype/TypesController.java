package tp.frontend.gui.gametype;

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

    @FXML
    public void choseType(ActionEvent event) throws IOException {
        Stage stage = (Stage) czech.getScene().getWindow();
        System.out.println("chose type");
        System.out.println(event.getSource());
        ClientNew clientTemp = new ClientNew();
        if (czech.equals(event.getSource())) {
            clientTemp.setGameKind("czech");
        } else if (swedish.equals(event.getSource())){
            clientTemp.setGameKind("swedish");
        } else if (german.equals(event.getSource())) {
            clientTemp.setGameKind("german");
        } else {
            clientTemp.setGameKind("nieokreslony");

        }
//        System.out.println(clientTemp.getGameKind());
        FXMLLoader board = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guixml.fxml" ).toUri().toURL () );
        Scene secondScene = new Scene( board.load(), 800, 600);
        stage.setTitle("WHITE");
        stage.setScene(secondScene);
        stage.show();

    }
}
//todo okno powitalne dla drugiego gracza
//todo rotate plansza
//todo zaznaczyc klikniete pole
//todo info czyja tura + blokowanie planszy