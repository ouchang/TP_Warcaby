package tp.frontend.gui.start;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class GUIController {

    @FXML
    private CheckBox showInstructionButton;

    @FXML
    private TextArea instruction;

    @FXML
    private ColumnConstraints board;

    @FXML
    private Label detector;

    @FXML
    private Button exitButton;

    @FXML
    private GridPane game;


    @FXML
    private Button sendMessageButton;

    @FXML
    public Pane pos65;

    @FXML
    public Pane pos74;

//

    @FXML
    public void tedectorAction(MouseEvent event) {
        if (event.getButton () == MouseButton.PRIMARY){
            System.out.println ("lewy przycisk");
            detector.setText ( "lewy przycisk" );
        }
        if (event.getButton () == MouseButton.SECONDARY){
            System.out.println ("prawy przycisk");
            detector.setText ( "prawy przycisk" );
        }
    }

    @FXML
    public void showInstruction(ActionEvent event) {
        if (instruction.isVisible () == true){
            instruction.setVisible ( false );
        } else {
            instruction.setVisible ( true );
        }
    }

    public static int movesCounter = 0;
    public GUIbehaviour guiBehaviour;
    @FXML
    public void movePiece(MouseEvent event) throws FileNotFoundException, MalformedURLException {
        Pane actual = (Pane) event.getSource ();
        if (movesCounter == 0){
            guiBehaviour = new GUIbehaviour ();
            guiBehaviour.fromTo.add ( actual );
            movesCounter ++;
        } else {
            movesCounter = 0;
            guiBehaviour.fromTo.add ( actual );
            guiBehaviour.react();
            guiBehaviour = null;
        }
    }


    @FXML
    public void exitGame(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public GUIController(){
        System.out.println ("gui controller created");
    }
}

