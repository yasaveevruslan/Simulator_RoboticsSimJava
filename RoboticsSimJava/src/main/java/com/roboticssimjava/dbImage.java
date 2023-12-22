package com.roboticssimjava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbImage {

    private void addImage(File file, String tableName) throws MalformedURLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        String imageName = file.getName();
        String imagePath = file.toURI().toURL().toString();

        System.out.println("Путь к изображению: " + imagePath);
        System.out.println("Имя изображения: " + imageName);

        String insertQuery = "INSERT INTO datafile." + tableName + " (image_name, image_data) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, imageName);

            File imageFile = new File(file.getAbsolutePath());
            try (FileInputStream fileInputStream = new FileInputStream(imageFile)) {
                preparedStatement.setBinaryStream(2, fileInputStream, (int) imageFile.length());
                preparedStatement.executeUpdate();

                System.out.println("Изображение успешно добавлено в базу данных.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addImageArea(File file) throws MalformedURLException {
        addImage(file, "images");
    }

    public void addImageRobot(File file) throws MalformedURLException {
        addImage(file, "imagesRobot");
    }
}
