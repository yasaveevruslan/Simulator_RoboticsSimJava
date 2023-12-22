package com.roboticssimjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    public static String positionRight = "User";
    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNIFIED);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("RoboticsSimJava");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void changeResizableWindow(Parent root, Button button, boolean mainPage)
    {
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) button.getScene().getWindow();
        currentStage.setScene(scene);
        if(!mainPage){
            currentStage.setWidth(600);
            currentStage.setHeight(400);
            currentStage.setResizable(false);

        }
        else
        {
            currentStage.setWidth(1200);
            currentStage.setHeight(800);
            currentStage.setResizable(true);
        }
        currentStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}