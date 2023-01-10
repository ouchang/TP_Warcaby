package tp.frontend.gui.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\guiversion\\warcaby\\src\\main\\java\\tp\\frontend\\GuiXml.fxml" ).toUri().toURL () );
//      Maria
//        FXMLLoader fxmlLoader = new FXMLLoader ( getClass ().getResource ( "GuiXml.fxml" ));
//        Test tees1 = new Test ();
//        FXMLLoader fxmlLoader = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\guiversion\\warcaby\\src\\main\\java\\tp\\frontend\\gui_xml.fxml" ).toUri().toURL () );
//      Ola
        FXMLLoader fxmlLoader = new FXMLLoader ( getClass().getResource("gui_xml.fxml"));
//        Test tees2 = new Test ();
        Scene scene = new Scene ( fxmlLoader.load (), 800, 600 );
        GUIController controller = fxmlLoader.getController ();
        if (controller == null){
            System.out.println ("controller is null");
        } else {
            System.out.println ( "pomiedzy" );

            stage.setTitle ( "CHECKERS" );
            stage.setScene ( scene );
            stage.setResizable ( false );
            stage.show ();
        }
    }

}
