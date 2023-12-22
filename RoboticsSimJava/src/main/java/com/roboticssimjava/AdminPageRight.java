package com.roboticssimjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminPageRight {
    public MenuButton AdminButton;
    public Button menuButton, robotButton, playButton, settingsButton;
    @FXML
    private TableView<PersonTableView> tableView;
    @FXML
    private TableColumn<PersonTableView, String> firstNameColumn, lastNameColumn, rightColumn;
    public TableColumn<PersonTableView, Boolean> checkBoxColumn;

    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        rightColumn.setCellValueFactory(new PropertyValueFactory<>("rightName"));

        checkBoxColumn.setCellValueFactory(cellData -> cellData.getValue().rightCheckBoxProperty());
        checkBoxColumn.setCellFactory(column -> new TableCell<PersonTableView, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);
                    setGraphic(checkBox);

                    checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                        PersonTableView person = getTableView().getItems().get(getIndex());
                        person.setRightCheckBoxSelected(newValue);
                        updatePersonInDatabase(person, newValue);
                    });
                }
            }
        });

        ObservableList<PersonTableView> data = FXCollections.observableArrayList(new LoginPageController().createArraylistPerson());
        tableView.setItems(data);
    }
    private void updatePersonInDatabase(PersonTableView person, Boolean newValue) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        String newPositionRight = newValue ? "Admin" : "User";

        String sql = "UPDATE datafile.useraccounts SET PositionRight = ? WHERE FirstName = ? AND LastName = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newPositionRight);
            statement.setString(2, person.getName());
            statement.setString(3, person.getLastName());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Обновление прошло успешно!");
                tableView.refresh();

                tableView.getItems().clear();
                tableView.getItems().addAll(new LoginPageController().createArraylistPerson());
                reloadWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void reloadWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPageRight.fxml"));
            Parent root = loader.load();

            Scene currentScene = tableView.getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void changeSettings()
    {
        try {
            FXMLLoader loader;
            if(Main.positionRight.equals("Admin")){
                loader = new FXMLLoader(getClass().getResource("SettingsAdminPage.fxml"));
            }else{
                loader = new FXMLLoader(getClass().getResource("SettingsPage.fxml"));
            }
            Parent root = loader.load();
            settingsButton.getScene().setRoot(root);
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
}
