package tp.frontend.gui.start;

import tp.backend.ClientNew;
import tp.backend.GameStatus;
import tp.backend.Position;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;

/**
 * MVC - Controller
 */
public class GUIController {
    ClientNew player;
    private boolean firstClick = false;
    public ArrayList<Pane> pieceFromTo = new ArrayList<>();
    public List<Pane> pieceAllWay = new ArrayList<>();

    @FXML
    private CheckBox showInstructionButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField instruction;

    @FXML
    public GridPane board8x8;

    @FXML
    private ColumnConstraints board;

    @FXML
    private Label detector;

    @FXML
    private Label printer;

    @FXML
    private Button exitButton;

    @FXML
    private GridPane game;

    public GUIController(){
        System.out.println ("GUI controller created");
    }

    public void setPlayer(ClientNew player) {
        this.player = player;
    }

    @FXML
    void updateBoard(ActionEvent event) {
        unlockBoard();
        detector.setText("");
        if(!player.getPollingAgent().getGameStatus().getActivePlayerID().equals(player.getPlayerId())) {
            System.out.println("Wait until opponent makes move!");
            detector.setText("Opponents move");
        } else {
            GUIbehaviour bevhaviour = new GUIbehaviour();
            GameStatus gameStatus = player.getPollingAgent().getGameStatus();
            bevhaviour.updateBoard(gameStatus.getBoard(), this);
            detector.setText("Your move");
        }
    }

    @FXML
    public void showInstruction(ActionEvent event) {
        String gameType =  player.getGameKind();

        switch (gameType){
            case "czech":{
                instruction.setText("CzechType");
                break;
            }
            case "swedish":{
                instruction.setText("SwedishType");
                break;
            }
            case "german":{
                instruction.setText("GermanType");
                break;
            }
        }
        if (instruction.isVisible () == true){
            instruction.setVisible ( false );
        } else {
            instruction.setVisible ( true );
        }
    }

    public void getTurn(){
        if(!player.getPollingAgent().getGameStatus().getActivePlayerID().equals(player.getPlayerId())) {
            System.out.println("Not your turn!");
            detector.setText("Not your turn!");
            return;
        } else {
            detector.setText("Your turn");
        }
    }

    @FXML
    public void movePiece(MouseEvent event) throws FileNotFoundException, MalformedURLException {
        getTurn();

        Pane actual = (Pane) event.getSource();
        
        if(event.getButton() == MouseButton.PRIMARY) {
            if(!this.firstClick) {
                System.out.println("FIRST CLICK");
                pieceAllWay.add(actual);
                pieceFromTo.add(actual);
                actual.setStyle("-fx-background-color: #666990");
                firstClick = true;
            } else {

                System.out.println("LAST CLICK");
                pieceFromTo.add(actual);
                pieceAllWay.add(actual);

                GUIbehaviour behaviour = new GUIbehaviour();
                behaviour.copyFromTo(pieceFromTo);
                List<Position> capturedFigures =  behaviour.serverCheck(player, pieceAllWay);

                System.out.println("___________________________________________");
                for (Position poss : capturedFigures){
                    System.out.println("X: " + poss.getX() + " Y: " + poss.getY());
                }
                System.out.println("ID OF PICTURE : " + behaviour.figureIdx);
                System.out.println("___________________________________________");

                if(capturedFigures.size() != 0) {
                    //todo get from server if the move is valid - if not do not delete pieces
                    //todo podświetlać pane jak nad nim przejeżdża się myszką
                    behaviour.react(behaviour.figureIdx, capturedFigures, board8x8);
                    lockBoard();
                } else {
                    behaviour.react(behaviour.figureIdx, null, board8x8);
                }

                for (Pane place : pieceAllWay){
                    place.setStyle("-fx-background-color: #d67342");
                }
                // clear
                pieceFromTo.clear();
                pieceAllWay.clear();
                firstClick = false;
            }
        } else if(event.getButton() == MouseButton.SECONDARY && firstClick) {
            System.out.println("MIDDLE CLICK");
            actual.setStyle("-fx-background-color: #eb7990");       //todo change color
            pieceAllWay.add(actual);
        }
    }

    public void lockBoard() {
        board8x8.setDisable(true);
    }

    public void unlockBoard() {
        board8x8.setDisable(false);
    }

    @FXML
    public void exitGame(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}

