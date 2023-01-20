package tp.frontend.gui.start;

import tp.backend.ClientNew;
import tp.backend.GameStatus;
import tp.backend.Position;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
 *
 * Class representing controller of the board
 */
public class GUIController {
    ClientNew player;
    private boolean firstClick = false;
    public ArrayList<Pane> pieceFromTo = new ArrayList<>();
    public List<Pane> pieceAllWay = new ArrayList<>();
    @FXML
    private TextArea instruction;

    @FXML
    public GridPane board8x8;

    @FXML
    private Label detector;

    @FXML
    private Button exitButton;

    public GUIController(){
        System.out.println ("GUI controller created");
    }

    /**
     * Method setPlayer sets who can make a move in that time.
     * This method unlocks a board for an opponent and updates his board.
     * @param player player who has to make move.
     */
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
                getTurn();
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(gameBoardManager);
    }

    /**
     * Class representing changes of players that are making a move.
     */
    public class GameBoardManager extends Task<Boolean> {
        private GUIController guiController;
        private GameStatus initGameStatus;

        public List<Position> positions = new ArrayList<Position>();

        public GameBoardManager(GUIController guiController, GameStatus initGameStatus) {
            this.guiController = guiController;
            this.initGameStatus = initGameStatus;
        }

        /**
         * Method call checks if the turn has changed // if the opponent made a move.
         */
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

    /**
     * Method updateOnDemand says a player to get an information about the position and to update a board
     */
    public void updateOnDemand() {
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
    public void exitGame(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showInstruction(ActionEvent event) {
        String gameType =  player.getGameKind();

        switch (gameType){
            case "czech":{
                instruction.setText("- By wykonać zwykły ruch/pojedyncze zbicie, zaznacz wybrane pola za pomocą lewego klawisza myszki.\n - By wykonać wielokrotne zbicie: pierwsze pole zaznacz lewym klawiszem myszki, pośrednie pola - prawym klawiszem myszki, końcowe pole - lewym klawiszem myszki.\n\n Reguły typu czeskiego: 1) Pionki poruszają się i zbijają wyłącznie do przodu\n2) Jeżeli istnieje możliwość bicia, to gracz musi wykonać dowolne bicie\n");
                break;
            }
            case "swedish":{
                instruction.setText("- By wykonać zwykły ruch/pojedyncze zbicie, zaznacz wybrane pola za pomocą lewego klawisza myszki.\n - By wykonać wielokrotne zbicie: pierwsze pole zaznacz lewym klawiszem myszki, pośrednie pola - prawym klawiszem myszki, końcowe pole - lewym klawiszem myszki.\n\n Reguły typu szwedzkiego: 1) Pionki poruszają się i zbijają wyłącznie do przodu\n");
                break;
            }
            case "german":{
                instruction.setText("- By wykonać zwykły ruch/pojedyncze zbicie, zaznacz wybrane pola za pomocą lewego klawisza myszki.\n - By wykonać wielokrotne zbicie: pierwsze pole zaznacz lewym klawiszem myszki, pośrednie pola - prawym klawiszem myszki, końcowe pole - lewym klawiszem myszki.\n\n Reguły typu niemieckiego: 1) Pionki poruszają się wyłącznie do przodu\n2) Pionek może wykonać bicie do tyłu\n");
                break;
            }

        }
        if (instruction.isVisible () == true){
            instruction.setVisible ( false );
        } else {
            instruction.setVisible ( true );
        }
    }

    /**
     * Method getTurn checks whose turn is it and prints it on a board
     */
    public void getTurn(){
        if(!player.getPollingAgent().getGameStatus().getActivePlayerID().equals(player.getPlayerId())) {
            System.out.println("Not your turn!");
            detector.setText("Not your turn!");
        } else {
            detector.setText("Your turn");
        }
    }

    /**
     * Method getTurn checks whose turn is it and prints it on a board.
     * This method depends on current status of the game.
     */
    public void getTurnBasedOnGameStatus(GameStatus gameStatus) {
        if(!gameStatus.getActivePlayerID().equals(player.getPlayerId())) {
            System.out.println("Not your turn!");
            detector.setText("Not your turn!");
        } else {
            detector.setText("Your turn");
        }
    }

    public void lockBoard() {
        board8x8.setDisable(true);
    }

    public void unlockBoard() {
        board8x8.setDisable(false);
    }

    /**
     * Method movePiece takes a position of the used pieces from the board.
     * This method checks when the move starts or ends (when pressing a left mouse button)
     * and if there are captures (right mouse button)
     */
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
                actual.setStyle("-fx-background-color: #666990");
                GUIbehaviour behaviour = new GUIbehaviour();
                behaviour.copyFromTo(pieceFromTo);
                List<Position> capturedFigures =  behaviour.serverCheck(player, pieceAllWay);

                if (behaviour.getCorrectMove()) {
                    if (capturedFigures.size() != 0) {
                        behaviour.removePiecesAfterMove(behaviour.figureIdx, capturedFigures, board8x8);
                        lockBoard();
                    } else {
                        behaviour.removePiecesAfterMove(behaviour.figureIdx, null, board8x8);
                    }

                    getTurnBasedOnGameStatus(behaviour.gameStatus);

                    //lock board
                    lockBoard();

                    //start thread -> thread unlocks board
                    GameBoardManager gameBoardManager = new GameBoardManager(this, behaviour.gameStatus);
                    gameBoardManager.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle (WorkerStateEvent e) {
                            unlockBoard();
                            updateOnDemand();
                            getTurn();
                        }
                    });

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(gameBoardManager);

                } else {
                    System.out.println("GUIController - Wrong move");
                    System.out.println( behaviour.gameStatus.getError());
                    detector.setText("Wrong move");
                }

                // clear
                pieceFromTo.clear();
                pieceAllWay.clear();
                firstClick = false;
            }
        } else if(event.getButton() == MouseButton.SECONDARY && firstClick) {
            System.out.println("MIDDLE CLICK");
            actual.setStyle("-fx-background-color: #666990");
            pieceAllWay.add(actual);
        }
    }
}

