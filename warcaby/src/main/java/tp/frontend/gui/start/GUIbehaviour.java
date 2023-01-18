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

/**
 * MVC - View
 */
public class GUIbehaviour {
    ArrayList<Pane> fromTo;
    public static ArrayList<Pane> positionChanges = new ArrayList<> ();

    GUIbehaviour(){
        this.fromTo = new ArrayList<Pane>();

    }

    public void addElement(Pane element) {
        fromTo.add(element);
    }

    public void copyFromTo(ArrayList<Pane> list) {
        for(Pane p : list) {
            addElement(p);
        }
    }

    public List<Position> getPositionFromPane(List<Pane> pieceAllWay){  //todo ocsobno getListOfPos oraz getPosFromPane
        List<Position> positions = new ArrayList<Position>();

        for (Pane actual : pieceAllWay) {
            Position pos = new Position();
            int row, col;
            // handling when rowIndex/colIndex = 0
            if (GridPane.getRowIndex(actual) == null) { // rowIndex = 0
                row = 1;
            } else {
                row = GridPane.getRowIndex(actual) + 1;
            }

            if (GridPane.getColumnIndex(actual) == null) { // colIndex = 0
                col = 1;
            } else {
                col = GridPane.getColumnIndex(actual) + 1;
            }
            pos.setX(row);
            pos.setY(col);
            positions.add(pos);
        }
        return positions;
    }
    public int figureIdx = 99;
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
            System.out.println("GUIController - Correct move");
        } else {
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

    public void add(Pane position, String fileName) throws FileNotFoundException, MalformedURLException {
        if(fileName != "") {
            Image image = new Image(getClass().getClassLoader().getResourceAsStream(fileName));
            ImageView imageView = new ImageView(image);
            imageView.setLayoutX(4);
            imageView.setLayoutY(12);
            imageView.setFitHeight ( 40 );
            imageView.setFitWidth ( 50 );
            position.getChildren ().add ( imageView );
        } else {
            System.out.println("Empty fileName!");
        }
    }

    public void updateBoard(String[][] boardString, GUIController controller){
        //todo: create class board and send not list of string but object board
        int row, col;
        String fileName = "";
        Image image = null;
        boolean emptyField = false;
        for (Node node : controller.board8x8.getChildren()) {

            if (node instanceof Pane) {
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


                switch (boardString[row][col]) {
                    case "0":{
                        emptyField = true;
                        break;
                    }
                    case "1":{
                        fileName = "simpleWhitePiece.png";
                        emptyField = false;
                        break;
                    }
                    case "2":{
                        fileName = "kingWhitePiece.png";
                        emptyField = false;
                        break;
                    }
                    case "3":{
                        fileName = "simpleBlackPiece.png";
                        emptyField = false;
                        break;
                    }
                    case "4":{
                        fileName = "kingBlackPiece.png";
                        emptyField = false;
                        break;
                    }
                }

                Pane pane = (Pane) node;
                ObservableList<Node> children = pane.getChildren();
                pane.getChildren().removeAll(children);                

                if(!emptyField) {
                    image = new Image(getClass().getClassLoader().getResourceAsStream(fileName));
                    ImageView imageView = new ImageView(image);
                    imageView.setLayoutX(4);
                    imageView.setLayoutY(12);
                    imageView.setFitHeight( 40 );
                    imageView.setFitWidth( 50 );

                    pane.getChildren().add(imageView);
                }
            }
        }
    }

    public void react(int figureIdx, List<Position> capturedFigures, GridPane gridPane) throws FileNotFoundException, MalformedURLException {
        // get figure's image file name

        String fileName = ImageType.values()[figureIdx].toString();

        System.out.println("FIGURE ID: " + figureIdx);
        System.out.println("FILENAME: " + fileName);

        // Add figure's image to the ending position
        add(fromTo.get(1), fileName);

        // Delete captured figures' images
        if(capturedFigures != null) {
            // Capture  move
            int row, col;
            ObservableList<Node> children = gridPane.getChildren();
            for(Position p : capturedFigures) {
                for(Node node : children) {
                    if(node instanceof Pane) {
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

                        if(p.getX() == row && p.getY() == col) {
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
