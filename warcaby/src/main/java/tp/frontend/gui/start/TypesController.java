package tp.frontend.gui.start;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

//import tp.backend.Bot;
import tp.backend.ClientNew;
import tp.backend.GameIDsRecorded;
import tp.backend.GameRecorded;
import tp.backend.GameStatus;

import java.io.IOException;
import java.nio.file.Paths;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

/**
 * MVC - Controller
 *
 *  Class managing the process of choosing a game type
 */
public class TypesController {

    @FXML
    private ToggleButton buttonDB;

    @FXML
    private ToggleButton botButton;

    @FXML
    private ToggleButton botButton1; // GUZIK DO OBSLUGI BAZY

    @FXML
    private ToggleButton playerButton;

    @FXML
    private ComboBox<String> gameRecordedCombo;

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
    private ObservableList<String> gameTypes;

    List<String> serializedGameStatus;


    ClientNew player;

    @FXML
    public void initialize() {
        //ClientNew client = new ClientNew();
        this.player = new ClientNew();
        GameIDsRecorded gameIDsRecorded = player.getGameIDsRecorded();
        gameTypes = FXCollections.observableArrayList(gameIDsRecorded.getSerializedRecordedGameIDs());
        gameRecordedCombo.setItems(gameTypes);

        gameRecordedCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldGameRecorded, String newGameRecorded) {
                if(newGameRecorded != null) {
                    GameRecorded gameRecorded = player.getGameRecorded(newGameRecorded);

                    int i=1;
                    if(!gameRecorded.getSerializedGameStatus().isEmpty()) {
                        player.setIsRecordedGameLoaded(true);
                        player.setSerializedGameStatus(gameRecorded.getSerializedGameStatus());
                        for(String s : gameRecorded.getSerializedGameStatus()) {
                            System.out.println("["+ i +"]: " + s);
                            i++;
                        }
                    }

                    try {
                        choseType(new ActionEvent());
                    } catch(IOException e) {
                        System.out.println(e.getMessage());
                        System.exit(1);
                    }
                }
            }
        });
    }
    
    @FXML
    void playWith(ActionEvent event) {
        czech.setDisable(false);
        german.setDisable(false);
        swedish.setDisable(false);

        if (event.getSource() == botButton) {
            //Bot bot = new Bot();


            //todo send info to server that second player in now needed
        } else if (event.getSource() == playerButton) {
        } else if(event.getSource() == botButton1) { // GUZIK DO OBSLUGI BAZY
            System.out.println("Database Button was clicked!");
        } else {
            throw new IllegalArgumentException("Niepoprawne źródło wywołania metody");
        }
    }


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
        } //else {
            //return;
        //}

        this.player.sendGameKind();
//      Maria
        //FXMLLoader board = new FXMLLoader( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\latestWarcaby\\warcaby\\src\\main\\java\\tp\\frontend\\gui\\start\\guifxml.fxml" ).toUri().toURL () );
//      Ola
        FXMLLoader board = new FXMLLoader ( getClass().getResource("guifxml.fxml"));

        Scene secondScene = new Scene( board.load(), 800, 600);
        GUIController controller = board.getController();
        controller.setPlayer(this.player);

        stage.setTitle("WHITE");
        stage.setScene(secondScene);
        stage.show();

        //if(player.getIsRecordedGameLoaded()) {
        //    controller.replayUpdateBoard();
        //}
    }
}
