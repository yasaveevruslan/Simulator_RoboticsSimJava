module com.roboticssimjava {
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires javafx.web;
    requires java.sql;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    requires mysql.connector.java;
    requires annotations;

    opens com.roboticssimjava to javafx.fxml;
    exports com.roboticssimjava;
}