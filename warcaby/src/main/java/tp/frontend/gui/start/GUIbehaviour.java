package tp.frontend.gui.start;

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

import javax.management.relation.Role;

/**
 * MVC - View
 */
public class GUIbehaviour {
    ArrayList<Pane> fromTo;
    public static ArrayList<Pane> positionChanges = new ArrayList<> ();
    String[] imageType;
    
    GUIbehaviour(){
        this.fromTo = new ArrayList<Pane>();
        imageType = new String[5];
        imageType[0] = "";
        imageType[1] = "simpleWhitePiece.png";
        imageType[2] = "kingWhitePiece.png"; 
        imageType[3] = "simpleBlackPiece.png";
        imageType[4] = "kingBlackPiece.png"; 
    }

    public void addElement(Pane element) {
        this.fromTo.add(element);
    }

    public void swapList(ArrayList<Pane> list) {
        for(Pane p : list) {
            addElement(p);
        }
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
            imageView.setFitHeight ( 40 );
            imageView.setFitWidth ( 50 );
            position.getChildren ().add ( imageView );
        } else {
            System.out.println("Empty fileName!");
        }
    }

    public synchronized void updateBoard(String[][] boardString, GUIController controller){
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
                    imageView.setFitHeight( 40 );
                    imageView.setFitWidth( 50 );

                    try {
                        add(pane, fileName);
                    } catch(FileNotFoundException e) {
                        System.out.println(e.getMessage());
                        System.exit(1);
                    } catch(MalformedURLException e) {
                        System.out.println(e.getMessage());
                        System.exit(1);
                    }
                }
            }
        }
    }

    public void react(int figureIdx, List<Position> capturedFigures, GridPane gridPane) throws FileNotFoundException, MalformedURLException {
        // get figure's image file name
        String fileName = imageType[figureIdx];

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
