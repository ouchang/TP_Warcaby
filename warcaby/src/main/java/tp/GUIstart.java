package tp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.nio.file.Paths;

import javafx.stage.Stage;

public class GUIstart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader ( Paths.get ( "C:\\Users\\hnatiuk\\Desktop\\pwr\\TP\\WarcabyGit\\warcaby\\src\\main\\java\\tp\\gui_xml.fxml" ).toUri().toURL () );
        Scene scene = new Scene ( fxmlLoader.load (), 800, 600 );
        stage.setTitle ( "CHECKERS" );
        stage.setScene ( scene );
        stage.setResizable ( false );
        stage.show ();
    }

}
