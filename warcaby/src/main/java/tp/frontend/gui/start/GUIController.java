package tp.frontend.gui.start;

import tp.backend.ClientNew;
import tp.backend.GameStatusClass;
import tp.backend.Position;

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
import java.util.List;
import java.util.ArrayList;

public class GUIController {
    private ClientNew player;

    private boolean firstClick = false;
    private Position from = new Position();
    private Position to = new Position();
    public ArrayList<Pane> fromTo = new ArrayList<Pane>();

    @FXML
    private CheckBox showInstructionButton;

    @FXML
    private TextArea instruction;

    @FXML
    public GridPane board8x8;


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

    public void setPlayer(ClientNew player) {
        this.player = player;
    }

    @FXML
    public void showInstruction(ActionEvent event) {
        if (instruction.isVisible () == true){
            instruction.setVisible ( false );
        } else {
            instruction.setVisible ( true );
        }
    }
  
    @FXML
    public void movePiece(MouseEvent event) throws FileNotFoundException, MalformedURLException {
        Pane actual = (Pane) event.getSource();
        boolean correctMove = false;
        
        // Regular / Single capture move
        if(!firstClick) {
            System.out.println("FIRST CLICK");
            System.out.println("ROW: " + GridPane.getRowIndex(actual) + " COL: " + GridPane.getColumnIndex(actual));

            int row, col;

            // position's indexes start from 1!
            if(GridPane.getRowIndex(actual) == null) { // rowIndex = 0
                row = 1;
            } else {
                row = GridPane.getRowIndex(actual) + 1;
            }

            if(GridPane.getColumnIndex(actual) == null) { // colIndex = 0
                col = 1;
            } else {
                col = GridPane.getColumnIndex(actual) + 1;
            }

            from.setX(row);
            from.setY(col);

            System.out.println("FROM X:" + from.getX() + " FROM Y: " + from.getY() + " TO X: " + to.getX() + " TO Y: " + to.getY());

            fromTo.add(actual);

            // mark clicked field on board!

            firstClick = true;
        } else {
            System.out.println("SECOND CLICK");
            System.out.println("ROW: " + GridPane.getRowIndex(actual) + " COL: " + GridPane.getColumnIndex(actual));

            int row, col;

            // position's indexes start from 1!
            if(GridPane.getRowIndex(actual) == null) { // rowIndex = 1
                row = 1;
            } else {
                row = GridPane.getRowIndex(actual) + 1;
            }

            if(GridPane.getColumnIndex(actual) == null) { // colIndex = 1
                col = 1;
            } else {
                col = GridPane.getColumnIndex(actual) + 1;
            }

            to.setX(row);
            to.setY(col);
            fromTo.add(actual);

            String[][] gameBoard = player.getGameStatusClass().getBoard();
            int figureIdx = Integer.parseInt(gameBoard[from.getX()][from.getY()]);

            // send move info to server
            correctMove = player.move(from, to);

            if(correctMove) {
                System.out.println("GUIController - Correct move");
                GUIbehaviour bevhaviour = new GUIbehaviour();
                bevhaviour.swapList(fromTo);
                //System.out.println("GUIController - gameStatus: " + player.getGameStatus().getBoard());
                //bevhaviour.react(player.getGameStatus().getBoard(), from);
                
                bevhaviour.react(figureIdx);

                //gameStatus = player.getGameStatus();
                //System.out.println("1 STATUS: " + gameStatus.getStatus() + " WHOSE TURN NOW: " + gameStatus.getTurn());
                
                // lock board
                //lockBoard();

            } else {
                System.out.println("GUIController - Wrong move");
                
                // print error message

                // start the whole move process again
            }

            // clear
            fromTo.clear();
            firstClick = false;

            //player.getGameStatus();
            //System.out.println("Player " + player.getPlayerId() + " received gameStatus!");
        }

       
    }

    public void lockBoard() {
        this.game.setDisable(true);
    }

    public void unlockBoard() {
        this.game.setDisable(false);
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

