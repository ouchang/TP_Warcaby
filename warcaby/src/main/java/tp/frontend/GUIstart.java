package tp.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.nio.file.Paths;

import javafx.stage.Stage;
import tp.frontend.GUI_controller;

public class GUIstart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Test tees1 = new Test ();
        FXMLLoader fxmlLoader = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\guiversion\\warcaby\\src\\main\\java\\tp\\frontend\\gui_xml.fxml" ).toUri().toURL () );
//        Test tees2 = new Test ();
        Scene scene = new Scene ( fxmlLoader.load (), 800, 600 );
        //System.out.println (Client.positionChanges.get ( 0 ).getId ());
        GUI_controller controller = fxmlLoader.getController ();
        if (controller == null){
            System.out.println ("controller is null");
        } else {
            System.out.println ( "pomiedzy" );

//            Test tees3 = new Test ();
            stage.setTitle ( "CHECKERS" );
            stage.setScene ( scene );
            stage.setResizable ( false );
            stage.show ();
        }
    }

}
