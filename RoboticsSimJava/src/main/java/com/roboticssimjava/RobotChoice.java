package com.roboticssimjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RobotChoice {

    public VBox imageBox1,imageBox2,imageBox3;
    public ImageView image1,image2,image3;
    public Button choiceButton;
    public Button settingsButton;
    public MenuButton AdminButton;

    @FXML
    private Button menuButton, playButton;
    public static ImageHolder holderRobot;
    private ImageView clickedImageView;
    private static final DropShadow HOVER_BORDER_EFFECT = new DropShadow(10, Color.DARKBLUE);

    public void initialize() {
        getImageFromData();
        addHoverBorder(imageBox1, image1);
        addHoverBorder(imageBox2, image2);
        addHoverBorder(imageBox3, image3);

        addButtonHoverEffect(choiceButton);
    }

    private void addHoverBorder(@NotNull VBox imageBox, ImageView imageView) {
        imageBox.setOnMouseEntered(event -> imageView.setEffect(HOVER_BORDER_EFFECT));
        imageBox.setOnMouseExited(event -> imageView.setEffect(null));
    }

    private void addButtonHoverEffect(@NotNull Button button) {
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #555555;")); // Цвет при наведении
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #3A3A3A;")); // Исходный цвет
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


    public void handleImageClick(@NotNull MouseEvent mouseEvent)
    {
        clickedImageView  = (ImageView) mouseEvent.getSource();
        System.out.println("Image Clicked: " + clickedImageView.getId());
    }

    public void choiceRobotImage(ActionEvent event)
    {
        holderRobot = new ImageHolder(clickedImageView);
    }

    private void getImageFromData()
    {
        List<Image> images = new ArrayList<>();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();
        String sql = "SELECT * FROM imagesRobot WHERE useIT = true";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                byte[] imageData = resultSet.getBytes("image_data");
                boolean useIT = resultSet.getBoolean("useIT");

                if (useIT)
                {
                    images.add(new Image(new ByteArrayInputStream(imageData)));
                }
            }
            addOnScreen(images, image1,image2,image3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addOnScreen(List<Image> images, ImageView... imageViews)
    {
        for (int i = 0; i < imageViews.length; i++) {
            if (i < images.size()) {
                Image image = images.get(i);
                imageViews[i].setImage(image);
            } else {
                imageViews[i].setImage(null);
            }
        }
    }
}
