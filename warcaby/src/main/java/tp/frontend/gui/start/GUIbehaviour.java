package tp.frontend.gui.start;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class GUIbehaviour {
    ArrayList<Pane> fromTo = new ArrayList<> (2);
    public static ArrayList<Pane> positionChanges = new ArrayList<> ();

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
        final ObservableList<Node> children = fromTo.get ( 0 ).getChildren();

        //URL abc = new URL ( "file:/C:/Users/hnatiuk/Desktop/pwr/TP/WarcabyGit/warcaby/src/main/java/tp/simpleWhitePiece.png" );
        URL abc = this.getClass().getClassLoader().getResource("simpleWhitePiece.png");

        if (children.size () != 0){
            System.out.println ("OBJECT: " + getImageName(((ImageView) children.get(0)).getImage().getUrl()));
            System.out.println ("WHITE URL2: " + getImageName(abc.getPath()));

            String childName = getImageName(((ImageView) children.get(0)).getImage().getUrl());
            String whiteName = getImageName(abc.getPath());

            //boolean b = Objects.equals ( ((ImageView) children.get(0)).getImage().getUrl(), abc.toString()); //todo po drugim poruszeniu nie umie znalezc url i wurzuca null: ustawiac url po dodaniu obrazka?
            boolean b = childName.equals(whiteName);

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
    public void react() throws FileNotFoundException, MalformedURLException {
        System.out.println("START ADD_PIECE FUNC");
        addPiece ( fromTo.get ( 1 ) );
        System.out.println("START DELETE_PIECE FUNC");
        deletePiece ( fromTo.get ( 0 ) );
    }
}