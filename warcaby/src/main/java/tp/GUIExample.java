package tp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Pete Cappello
 */
public class GUIExample {
    // model attribute
    private int boardSize;  // number of rows & columns in the board
    
    // view attributes
    private int squareSize; // number of pixels in a square's edge
    
    /* For this use case, we may reasonably make the objects below attributes of 
     * the CheckerBoard class. However, if this class is used by other classes, 
     * some or all of the attributes below probably would best be thought of as 
     * NOT being attributes of this class.
     */
    private Image image;
    private ImageIcon imageIcon;
    private JLabel jLabel;
    private JFrame jFrame;
    
    GUIExample() {
        boardSize  = getInt( "n X n checker board for what value of n? [2 - 10]?" );
        squareSize = getInt( "How many pixels per square? [1 - 100]?" );
        int imageSize = boardSize * squareSize;       
        image = new BufferedImage( imageSize, imageSize, BufferedImage.TYPE_INT_ARGB );
        imageIcon = new ImageIcon( image );
        jLabel = new JLabel( imageIcon );
        jFrame = new JFrame( "Checker Board" );
        jFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        Container container = jFrame.getContentPane();
        container.setLayout( new BorderLayout() );
        container.add( jLabel, BorderLayout.CENTER );
        jFrame.pack();
    }
    
    /**
     * @param args the command line arguments: Unused.
     */
    public static void main(String[] args) 
    {
        GUIExample checkerBoard = new GUIExample();
        checkerBoard.paint();
        checkerBoard.view();
    }
    
    private int getInt( String question )
    {
        String intString = JOptionPane.showInputDialog( question );
        return Integer.parseInt( intString );
    }
        
    /**
     * Paint the checker board onto the Image.
     */
    private void paint()
    {
        Graphics graphics = image.getGraphics();
        
        // paint a red  board
        graphics.setColor( Color.white );
        graphics.fillRect( 0, 0, boardSize * squareSize, boardSize * squareSize);
        
        // paint the black squares
        graphics.setColor( Color.black );
        for ( int row = 0; row < boardSize; row++ )
        {
            for ( int col = row % 2; col < boardSize; col += 2 )
            {
                graphics.fillRect( row * squareSize, col * squareSize, squareSize, squareSize );
            }
        }
    }
    
    private void view() { jFrame.setVisible( true ); }
}
