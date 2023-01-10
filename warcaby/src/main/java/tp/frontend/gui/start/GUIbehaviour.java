package tp.frontend.gui.start;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import tp.backend.Position;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class GUIbehaviour {
    //ArrayList<Pane> fromTo = new ArrayList<> (2);
    ArrayList<Pane> fromTo;
    public static ArrayList<Pane> positionChanges = new ArrayList<> ();
    String[] imageType;
    
    GUIbehaviour(){
        this.fromTo = new ArrayList<Pane>();
        imageType = new String[5];
        imageType[0] = "";
        imageType[1] = "simpleWhitePiece.png";
        imageType[2] = "simpleWhiteKing.png"; // TO DO: Add image to project!
        imageType[3] = "simpleBlackPiece.png";
        imageType[4] = "simpleBlackKing.png"; // TO DO: Add image to project!
    }

    public void addElement(Pane element) {
        this.fromTo.add(element);
    }

    public void swapList(ArrayList<Pane> list) {
        for(Pane p : list) {
            addElement(p);
        }
        System.out.println("End of List copy process");
    }

    public String getImageName(String url) {
        int slashIdx = url.length()-1;
        while(url.charAt(slashIdx) != '/') {
            slashIdx--;
        }

        return url.substring(slashIdx+1);
    }

    public void deletePiece(Pane position){
        System.out.println ("deleting piece");
        final ObservableList<Node> children = position.getChildren();
        position.getChildren ().removeAll (children );
    }

    public void addPiece(Pane position) throws FileNotFoundException, MalformedURLException {
        System.out.println ("adding image");
        //final ObservableList<Node> children = fromTo.get ( 0 ).getChildren();
        ObservableList<Node> children = fromTo.get ( 0 ).getChildren();

        //URL abc = new URL ( "file:/C:/Users/hnatiuk/Desktop/pwr/TP/WarcabyGit/warcaby/src/main/java/tp/simpleWhitePiece.png" );
        URL abc = this.getClass().getClassLoader().getResource("simpleWhitePiece.png");

        if (children.size () != 0){

            System.out.println ("OBJECT: " + ((ImageView) children.get(0)).getImage().getUrl());
            System.out.println ("WHITE URL2: " + abc.getPath()); 

            //String childName = getImageName(((ImageView) children.get(0)).getImage().getUrl());
            //String whiteName = getImageName(abc.getPath());
            
            //boolean b = Objects.equals ( ((ImageView) children.get(0)).getImage().getUrl(), abc.toString()); //todo po drugim poruszeniu nie umie znalezc url i wurzuca null: ustawiac url po dodaniu obrazka?
            //boolean b = childName.equals(whiteName);
            boolean b = true;
            if (b == true){
                System.out.println ("same white");
                Image image = new Image(getClass().getClassLoader().getResourceAsStream("simpleWhitePiece.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight ( 40 );
                imageView.setFitWidth ( 50 );
                position.getChildren ().add ( imageView );
            } else {
                System.out.println ("different colors");
                Image image = new Image(getClass().getClassLoader().getResourceAsStream("simpleBlackPiece.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight ( 40 );
                imageView.setFitWidth ( 50 );
                position.getChildren ().add ( imageView );
            }
        } else {
            System.out.println ("num of children = 0");
        }

    }

    public void add(Pane position, String fileName) throws FileNotFoundException, MalformedURLException {
        System.out.println ("Add figure's image");

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



    public void react(int figureIdx) throws FileNotFoundException, MalformedURLException {
        // get figure's image file name
        String fileName = imageType[figureIdx];
        System.out.println("FileName: " + fileName);
        System.out.println("START ADD_PIECE FUNC");
        //addPiece ( fromTo.get ( 1 ) );
        add(fromTo.get(1), fileName);
        System.out.println("START DELETE_PIECE FUNC");
        deletePiece ( fromTo.get ( 0 ) );
    }
}
