package com.roboticssimjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class SettingsPageController {
    public ToggleButton themeToggle;
    public Button loginPage;
    public Button menuButton;
    public Button robotButton;
    public Button playButton;
    public MenuButton AdminButton;

    public void goToRegistration()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root = loader.load();

            Main.changeResizableWindow(root,loginPage,false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggleTheme()
    {

    }

    public void changeMenuPage() {
        try {
            FXMLLoader loader;
            if(Main.positionRight.equals("Admin")){
                loader = new FXMLLoader(getClass().getResource("menuAdminPage.fxml"));
            }else{
                loader = new FXMLLoader(getClass().getResource("menuPage.fxml"));
            }
            Parent root = loader.load();
            menuButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeRobotPage() {
        try {
            FXMLLoader loader;
            if(Main.positionRight.equals("Admin")){
                loader = new FXMLLoader(getClass().getResource("robotAdminChoice.fxml"));
            }else{
                loader = new FXMLLoader(getClass().getResource("robotChoice.fxml"));
            }
            Parent root = loader.load();
            robotButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePlayMode() {
        try {
            FXMLLoader loader;
            if(Main.positionRight.equals("Admin")){
                loader = new FXMLLoader(getClass().getResource("main-viewAdminPage.fxml"));
            }else{
                loader = new FXMLLoader(getClass().getResource("main-viewPage.fxml"));
            }
            Parent root = loader.load();
            playButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeAdminPageArea() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPageArea.fxml"));
            Parent root = loader.load();
            AdminButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeAdminPageRobot() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPageRobot.fxml"));
            Parent root = loader.load();
            AdminButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeAdminPageRight() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPageRight.fxml"));
            Parent root = loader.load();
            AdminButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
