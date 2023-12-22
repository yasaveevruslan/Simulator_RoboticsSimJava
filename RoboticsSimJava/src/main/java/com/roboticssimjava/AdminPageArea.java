package com.roboticssimjava;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class AdminPageArea {
    public Button SaveArea;

    public Button settingsButton;
    public Button playButton;
    public Button robotButton;
    public Button menuButton;
    public MenuButton AdminButton;
    public TableView<ImgTableView> tableView;
    public TableColumn<ImgTableView, Image> imgColumn;
    public TableColumn<ImgTableView, Boolean> checkBoxColumn;
    public TableColumn<ImgTableView, String>  dataColumn;
    public TableColumn<ImgTableView, String> nameColumn;
    public TableColumn<ImgTableView, Boolean> checkDeleteBoxColumn;
    public Button DeleteArea;

    private ArrayList<ImgTableView> imgDelete = new ArrayList<>();
    private ArrayList<ImgTableView> imgShow = new ArrayList<>();

    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        imgColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        imgColumn.setCellFactory(new Callback<TableColumn<ImgTableView, Image>, TableCell<ImgTableView, Image>>() {
            @Override
            public TableCell<ImgTableView, Image> call(TableColumn<ImgTableView, Image> param) {
                return new TableCell<ImgTableView, Image>()
                {
                    @Override
                    protected void updateItem(Image item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty || item == null)
                        {
                            setGraphic(null);
                        }
                        else
                        {
                            ImageView imageView = new ImageView(item);
                            imageView.setFitWidth(180);
                            imageView.setFitHeight(120);
                            setGraphic(imageView);
                        }
                    }
                };
            }
        });


        checkBoxColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isCheckBoxSelectedShow()));

        checkBoxColumn.setCellFactory(new Callback<TableColumn<ImgTableView, Boolean>, TableCell<ImgTableView, Boolean>>() {
            @Override
            public TableCell<ImgTableView, Boolean> call(TableColumn<ImgTableView, Boolean> param) {
                return new CheckBoxTableCell<>(){
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            ImgTableView img = getTableView().getItems().get(getIndex());

                            CheckBox checkBox = new CheckBox();
                            checkBox.setSelected(img.isCheckBoxSelectedShow());
                            setGraphic(checkBox);

                            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                                if (newValue) {
                                    if (getSelectedCheckboxCount() < 3) {
                                        img.setCheckBoxSelectedShow(true);
                                        imgShow.add(img);
                                        if (!imgShow.isEmpty()) {
                                            updatePhotoData(img.getId(), true);
                                        }

                                    } else {
                                        checkBox.setSelected(false);
                                    }
                                } else {
                                    img.setCheckBoxSelectedShow(false);
                                    imgShow.remove(img);

                                    if (!imgShow.isEmpty()) {
                                        updatePhotoData(img.getId(), false);
                                    }
                                }
                                System.out.println("Selected checkboxes count: " + imgShow.size());
                            });
                        }
                    }

                    private long getSelectedCheckboxCount() {
                        return imgShow.stream().filter(ImgTableView::isCheckBoxSelectedShow).count();
                    }
                };
            }
        });


        checkDeleteBoxColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isCheckBoxSelectedDelete()));
        checkDeleteBoxColumn.setCellFactory(new Callback<TableColumn<ImgTableView, Boolean>, TableCell<ImgTableView, Boolean>>() {
            @Override
            public TableCell<ImgTableView, Boolean> call(TableColumn<ImgTableView, Boolean> param) {
                return new CheckBoxTableCell<>(){
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            CheckBox checkBox = new CheckBox();
                            checkBox.setSelected(item);
                            setGraphic(checkBox);

                            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                                ImgTableView img = getTableView().getItems().get(getIndex());
                                img.setCheckBoxSelectedDelete(newValue);
                                if(newValue){
                                    imgDelete.add(img);
                                }else {
                                    imgDelete.remove(img);
                                }

                            });
                        }
                    }
                };
            }

        });

        ObservableList<ImgTableView> data = FXCollections.observableArrayList();
        data.addAll(new LoginPageController().createArraylistImg());
        tableView.setItems(data);
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
    public void chooseAreaImage() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            dbImage image = new dbImage();
            image.addImageArea(file);
            reloadWindow();
        }
    }
    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
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

    public void deleteAreaImage(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        if (imgDelete.isEmpty()) {
            System.out.println("No images selected for deletion.");
            return;
        }

        String sql = "DELETE FROM datafile.images WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (ImgTableView img : imgDelete) {
                statement.setInt(1, img.getId());
                statement.executeUpdate();
            }

            imgDelete.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        reloadWindow();
    }

    private void updatePhotoData(int id, boolean newValue) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();
        String sql = "UPDATE datafile.images SET useIT = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, newValue);
            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Изображения успешно обновлено.");
            } else {
                System.out.println("Изображения не обновлено. Возможно, запись с id=" + id + " не найдена.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void reloadWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPageArea.fxml"));
            Parent root = loader.load();

            Scene currentScene = tableView.getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
