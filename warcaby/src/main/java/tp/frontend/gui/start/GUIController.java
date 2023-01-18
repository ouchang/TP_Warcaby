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
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    boolean isBoardLocked = false;

    public void setPlayer(ClientNew player) {
        this.player = player;

        GameStatus gameStatus = player.getGameStatus();

        //start thread -> thread unlocks board
        GameBoardManager gameBoardManager = new GameBoardManager(this, gameStatus);
        gameBoardManager.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle (WorkerStateEvent e) {
                unlockBoard();
                updateOnDemand();
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(gameBoardManager);
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


    void updateOnDemand() {
        if(!player.getPollingAgent().getGameStatus().getActivePlayerID().equals(player.getPlayerId())) {
            System.out.println("Wait until opponent makes move!");
            return;
        }

        GUIbehaviour bevhaviour = new GUIbehaviour();
        GameStatus gameStatus = player.getPollingAgent().getGameStatus();
        System.out.println("Updating board!");
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
        String gameType =  player.getGameKind();

        switch (gameType){
            case "czech":{
                instruction.setText("- By wykonać zwykły ruch/pojedyncze zbicie, zaznacz wybrane pola za pomocą lewego klawisza myszki.\n - By wykonać wielokrotne zbicie: pierwsze pole zaznacz lewym klawiszem myszki, pośrednie pola - prawym klawiszem myszki, końcowe pole - lewym klawiszem myszki.\n Reguły typu czeskiego: 1) Pionki poruszają się i zbijają wyłącznie do przodu\n2) Jeżeli istnieje możliwość bicia, to gracz musi wykonać dowolne bicie\n");
                break;
            }
            case "swedish":{
                instruction.setText("- By wykonać zwykły ruch/pojedyncze zbicie, zaznacz wybrane pola za pomocą lewego klawisza myszki.\n - By wykonać wielokrotne zbicie: pierwsze pole zaznacz lewym klawiszem myszki, pośrednie pola - prawym klawiszem myszki, końcowe pole - lewym klawiszem myszki.\n Reguły typu szwedzkiego: 1) Pionki poruszają się i zbijają wyłącznie do przodu\n");
                break;
            }
            case "german":{
                instruction.setText("- By wykonać zwykły ruch/pojedyncze zbicie, zaznacz wybrane pola za pomocą lewego klawisza myszki.\n - By wykonać wielokrotne zbicie: pierwsze pole zaznacz lewym klawiszem myszki, pośrednie pola - prawym klawiszem myszki, końcowe pole - lewym klawiszem myszki.\n Reguły typu czeskiego: 1) Pionki poruszają się wyłącznie do przodu\n2) Pionek może wykonać bicie do tyłu\n");
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

//                System.out.println("___________________________________________");
//                for (Position poss : capturedFigures){
//                    System.out.println("X: " + poss.getX() + " Y: " + poss.getY());
//                }
//                System.out.println("ID OF PICTURE : " + behaviour.figureIdx);
//                System.out.println("___________________________________________");

                if (behaviour.getCorrectMove()) {
                    if (capturedFigures.size() != 0) {
                        //todo get from server if the move is valid - if not do not delete pieces
                        //todo podświetlać pane jak nad nim przejeżdża się myszką
                        behaviour.removePiecesAfterMove(behaviour.figureIdx, capturedFigures, board8x8);
                        lockBoard();
                    } else {
                        behaviour.removePiecesAfterMove(behaviour.figureIdx, null, board8x8);
                    }

                    //lock board
                    lockBoard();

                    //start thread -> thread unlocks board
                    GameBoardManager gameBoardManager = new GameBoardManager(this, gameStatus);
                    gameBoardManager.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle (WorkerStateEvent e) {
                            unlockBoard();
                            updateOnDemand();
                        }
                    });

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(gameBoardManager);

                } else {
                    System.out.println("GUIController - Wrong move");
                    System.out.println(gameStatus.getError());
                    // print error message
                }

                for (Pane place : pieceAllWay) {
                    if (place.getStyle().equals("-fx-background-color: #666990")) {
                        place.setStyle("-fx-background-color: #d67342");
                    }
                }

                // clear
                pieceFromTo.clear();
                pieceAllWay.clear();
                firstClick = false;
            }
        } else if(event.getButton() == MouseButton.SECONDARY && firstClick) {
            System.out.println("MIDDLE CLICK");
            actual.setStyle("-fx-background-color: #666990");       //todo change color
            pieceAllWay.add(actual);
        }
    }

    public void lockBoard() {
        board8x8.setDisable(true);
        //todo block panes if not your move
//        board8x8.
    }

    public void unlockBoard() {
        board8x8.setDisable(false);
    }

    @FXML
    public void exitGame(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public class GameBoardManager extends Task<Boolean> {
        GUIController guiController;
        GameStatus initGameStatus;

        GameBoardManager(GUIController guiController, GameStatus initGameStatus) {
            this.guiController = guiController;
            this.initGameStatus = initGameStatus;
        }

        public Boolean call() {
            String currentPlayer;
            String myPlayerID = guiController.player.getPlayerId();

            currentPlayer = initGameStatus.getActivePlayerID();
            try {
                while(!myPlayerID.equals(currentPlayer)) {
                    Thread.sleep(2000);
                    currentPlayer = guiController.player.getPollingAgent().getGameStatus().getActivePlayerID();
                }
            } catch(InterruptedException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            return true;
        }
    }
}

