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

    @FXML
    private CheckBox showInstructionButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextArea instruction;

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
    void updateBoard(ActionEvent event) {
        if(!player.getPollingAgent().getGameStatus().getActivePlayerID().equals(player.getPlayerId())) {
            System.out.println("Wait until opponent makes move!");
            return;
        }

        GUIbehaviour bevhaviour = new GUIbehaviour();
        GameStatus gameStatus = player.getPollingAgent().getGameStatus();
        bevhaviour.updateBoard(gameStatus.getBoard(), this);
    }

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
 
    private boolean firstClick = false;
    public ArrayList<Pane> fromTo = new ArrayList<Pane>();
    public List<Position> positions = new ArrayList<Position>();

    @FXML
    public void movePiece(MouseEvent event) throws FileNotFoundException, MalformedURLException {
        if(!player.getPollingAgent().getGameStatus().getActivePlayerID().equals(player.getPlayerId())) {
            System.out.println("Not your turn!");
            return;
        }

        Pane actual = (Pane) event.getSource();
        String errorMessage;
        Position pos = new Position();

        int row, col;
        // handling when rowIndex/colIndex = 0
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

        
        if(event.getButton() == MouseButton.PRIMARY) {
            if(!this.firstClick) {
                System.out.println("FIRST CLICK");
                
                pos.setX(row);
                pos.setY(col);
                this.positions.add(pos);

                this.fromTo.add(actual);

                // TO DO: mark clicked field on board!

                this.firstClick = true;
            } else {
                System.out.println("LAST CLICK");

                pos.setX(row);
                pos.setY(col);
                this.positions.add(pos);

                this.fromTo.add(actual);
                
                List<Position> capturedFigures = new ArrayList<Position>();

                // send move info to server
                System.out.println("POSITIONS: " + this.positions);
                GameStatus gameStatus = player.sendMoveCommand(this.positions);
                errorMessage = gameStatus.getError();
                capturedFigures = gameStatus.getCapturedFigures();  
                String[][] gameBoard = gameStatus.getBoard();
                int figureIdx = Integer.parseInt(gameBoard[pos.getX()][pos.getY()]);

                System.out.println("Error Message: " + errorMessage);

                if(errorMessage.equals("")) {
                    System.out.println("GUIController - Correct move");
                    GUIbehaviour bevhaviour = new GUIbehaviour();

                    bevhaviour.swapList(this.fromTo);
                    if(capturedFigures.size() != 0) {
                        bevhaviour.react(figureIdx, capturedFigures, this.board8x8);
                    } else {
                        bevhaviour.react(figureIdx, null, this.board8x8);
                    }
                } else {
                    System.out.println("GUIController - Wrong move");
                    System.out.println(gameStatus.getError());
                    // print error message
                }

                // clear
                this.fromTo.clear();
                this.positions.clear();

                this.firstClick = false;
            }
        } else if(event.getButton() == MouseButton.SECONDARY) {
            if(this.firstClick) {
                System.out.println("MIDDLE CLICK");
                pos.setX(row);
                pos.setY(col);
                this.positions.add(pos);
            }
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

