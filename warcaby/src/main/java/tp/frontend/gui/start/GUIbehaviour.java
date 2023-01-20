package tp.frontend.gui.start;

import tp.backend.ClientNew;
import tp.backend.GameStatus;
import tp.backend.Position;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.management.relation.Role;

/**
 * MVC - View
 *
 * Class representing direct communication with a server.
 */
public class GUIbehaviour {
    private ArrayList<Pane> fromTo;

    public int figureIdx = -1;

    private boolean correctMove;

    public GUIbehaviour(){
        this.fromTo = new ArrayList<Pane>();
    }

    public void addElement(Pane element) {
        fromTo.add(element);
    }

    /**
     * Method copies received list of starting / ending position to the private ArrayList.
     */
    public void copyFromTo(ArrayList<Pane> list) {
        for(Pane p : list) {
            addElement(p);
        }
    }

    public List<Position> getPositionFromPane(List<Pane> pieceAllWay){
        List<Position> positions = new ArrayList<>();

        for (Pane actual : pieceAllWay) {
            Position position = transformIndexes(actual);
            positions.add(position);
        }
        return positions;
    }
    public void setCorrectMove(boolean correctMove){
        this.correctMove = correctMove;
    }
    public boolean getCorrectMove(){
        return correctMove;
    }
    public GameStatus gameStatus = new GameStatus();

    /**
     * Method serverCheck checks the correctness of the move.
     * This method sends an information about the move to the server and recieves the curent game status.
     * @param player ID of the player who made move
     * @param pieceAllWay describes a move (as a Pane) including starting / ending position and all captures.
     * @return list of captured figures while making this move.
     */
    public List<Position> serverCheck(ClientNew player, List<Pane> pieceAllWay){
        System.out.println("___________________________________________");
        for (Pane p : pieceAllWay){
            System.out.println("ROW: " + GridPane.getRowIndex(p) + "COL: " + GridPane.getColumnIndex(p) );
        }
        System.out.println("___________________________________________");

        List<Position> positions = getPositionFromPane(pieceAllWay);
        List<Position> capturedFigures = new ArrayList<Position>();
        Position pos = new Position();

        // send move info to server
        for (Position poss : positions){
            System.out.println("POSITION: X= " + poss.getX() + " Y= " + poss.getY());
        }

        GameStatus gameStatus = player.sendMoveCommand(positions);
        String errorMessage = gameStatus.getError();
        capturedFigures = gameStatus.getCapturedFigures();
        String[][] gameBoard = gameStatus.getBoard();

        pos = positions.get(positions.size()-1);
        figureIdx = Integer.parseInt(gameBoard[pos.getX()][pos.getY()]);

        System.out.println("Error Message: " + errorMessage);

        if(errorMessage.equals("")) {
            setCorrectMove(true);
            System.out.println("GUIController - Correct move");
        } else {
            setCorrectMove(false);
            System.out.println("GUIController - Wrong move");
            System.out.println(gameStatus.getError());
        }
        return  capturedFigures;
    }
    public String getImageName(String url) {
        int slashIdx = url.length()-1;
        while(url.charAt(slashIdx) != '/') {
            slashIdx--;
        }

        return url.substring(slashIdx+1);
    }

    public void deletePiece(Pane position){
        final ObservableList<Node> children = position.getChildren();
        position.getChildren ().removeAll (children );
    }

    /**
     * Method that adds the specified file to a proper position on the board.
     */
    public void add(Pane position, String fileName) throws FileNotFoundException, MalformedURLException {
        if(!Objects.equals(fileName, "")) {
            Image image = new Image(getClass().getClassLoader().getResourceAsStream(fileName));
            ImageView imageView = newImageview(image);
            position.getChildren ().add ( imageView );
        } else {
            System.out.println("Empty fileName!");
        }
    }
    public ImageView newImageview(Image image){
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(4);
        imageView.setLayoutY(12);
        imageView.setFitHeight ( 40 );
        imageView.setFitWidth ( 50 );
        return imageView;
    }

    /**
     * Method updates a board due to boardString received from the server.
     * This methods also unmarkes the last move from the board.
     * @param boardString the information about the position on the board received from server.
     * @param controller controller of the gui.
     */
    public synchronized void updateBoard(String[][] boardString, GUIController controller){
        int row, col;
        String fileName = "";
        Image image;
        for (Node node : controller.board8x8.getChildren()) {
            if (node instanceof Pane) {
                Position position = transformIndexes(node);

                switch (boardString[position.getX()][position.getY()]) {
                    case "0":{
                        fileName = ImageType.EMPTY.toString();
                        break;
                    }
                    case "1":{
                        fileName = ImageType.WHITE_SIMPLE.toString();
                        break;
                    }
                    case "2":{
                        fileName = ImageType.WHITE_KING.toString();
                        break;
                    }
                    case "3":{
                        fileName = ImageType.BLACK_SIMPLE.toString();
                        break;
                    }
                    case "4":{
                        fileName = ImageType.BLACK_KING.toString();
                        break;
                    }
                }

                Pane pane = (Pane) node;
                pane.getChildren().removeAll(pane.getChildren());

                if(!ImageType.EMPTY.equalsName(fileName)) {
                    image = new Image(getClass().getClassLoader().getResourceAsStream(fileName));
                    ImageView imageView = newImageview(image);
                    pane.getChildren().add(imageView);
                }

                if (pane.getStyle().equals("-fx-background-color: #666990")) {
                    pane.setStyle("-fx-background-color: #d67342");
                }
            }
        }
    }

    /**
     * Method that takes the position of the node
     * and transforms it to the position that server can operate on.
     */
    public Position transformIndexes( Node node ){
        Position position = new Position();
        int row, col;
        if(GridPane.getRowIndex(node) == null) { // rowIndex = 0
            row = 1;
        } else {
            row = GridPane.getRowIndex(node) + 1;
        }

        if(GridPane.getColumnIndex(node) == null) { // colIndex = 0
            col = 1;
        } else {
            col = GridPane.getColumnIndex(node) + 1;
        }
        position.setX(row);
        position.setY(col);
        return position;
    }

    /**
     * Method removePiecesAfterMove removes unnecessary pieces from the board.
     * This method also adds a piece at the last position of the move.
     * @param figureIdx ID of the piece that ends a move and have to be added to a new position.
     * @param capturedFigures Pieces of the opponent that have to be deleted from thw board.
     * @param gridPane game's board
     */
    public void removePiecesAfterMove(int figureIdx, List<Position> capturedFigures, GridPane gridPane) throws FileNotFoundException, MalformedURLException {
        // get figure's image file name
        String fileName = ImageType.values()[figureIdx].toString();
        // Add figure's image to the ending position
        add(fromTo.get(1), fileName);

        // Delete captured figures' images
        if(capturedFigures != null) {
            // Capture  move
            ObservableList<Node> children = gridPane.getChildren();
            for(Position p : capturedFigures) {
                for(Node node : children) {
                    if(node instanceof Pane) {
                        Position position = transformIndexes(node);
                        if(p.getX() == position.getX() && p.getY() == position.getY()) {
                            Pane pane = (Pane) node;
                            deletePiece(pane);
                        }
                    }
                }
            }
        }
            
        // Delete figure's image from starting position
        deletePiece(fromTo.get(0));
    }
}
