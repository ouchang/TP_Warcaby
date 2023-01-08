module warcaby_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop; //

    opens warcaby_fx to javafx.fxml;
    exports warcaby_fx;
}
