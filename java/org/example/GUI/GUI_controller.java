package org.example.GUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.lang.model.type.NullType;
import javax.swing.plaf.IconUIResource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class GUI_controller {

    @FXML
    private CheckBox showInstructionButton;

    @FXML
    private TextArea instruction;

    @FXML
    private ColumnConstraints board;

    @FXML
    private Label detector;

    @FXML
    private Pane pos65;

    @FXML
    private Pane pos74;

    @FXML
    void tedectorAction(MouseEvent event) {
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
    void showInstruction(ActionEvent event) {
        if (instruction.isVisible () == true){
            instruction.setVisible ( false );
        } else {
            instruction.setVisible ( true );
        }
    }

    @FXML
    private ImageView simplePieceIm;


    @FXML
    void movePiece(MouseEvent event) throws FileNotFoundException {
        Pane actual = (Pane) event.getSource ();
        final ObservableList<Node> children = actual.getChildren();

        System.out.println (children.size ());
        if (children.size () == 0){
            System.out.println ("setting image");
            FileInputStream inputstream = new FileInputStream("C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\TP_Warcaby\\java\\org\\example\\GUI\\simpleWhitePiece.png");
            Image image = new Image (inputstream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight ( 40 );
            imageView.setFitWidth ( 50 );
            actual.getChildren ().add ( imageView );
        } else if (children.size () == 1){
            System.out.println ("deleting image");
            actual.getChildren ().removeAll (children );
        }
    //if has image -> delete
    //if has no image -> add

    }

    @FXML
    private Button exitButton;

    @FXML
    private GridPane game;


    @FXML
    private Button sendMessageButton;

    @FXML
    void exitGame(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }


}