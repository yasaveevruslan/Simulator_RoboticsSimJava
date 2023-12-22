package com.roboticssimjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class LoginPageController {

    @FXML
    private Button loginButton, registrationButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    public void setLoginButton(ActionEvent e)
    {
        if(!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()){
            validateLogin();
        }else{
            loginMessageLabel.setText("Пожалуйста введите имя пользователя и пароль.");
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        String currentUsername = usernameTextField.getText();
        String currentPassword = passwordPasswordField.getText();

        String verifyLogin = "SELECT * FROM datafile.useraccounts WHERE UserName = '" + currentUsername + "' AND Password = '" + currentPassword + "';";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);
            System.out.println(resultSet == null);

            if(resultSet != null)
            {
                while (resultSet.next())
                {
                    System.out.println(resultSet.getInt(1));
                    Main.positionRight = resultSet.getString("PositionRight");
                    loginMessageLabel.setText("Выполняем вход.");
                    changeMenuPage();
                }
            }
            else
            {
            System.out.println(resultSet.getInt(1));
            loginMessageLabel.setText("Неправильно введены данные.");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeRegistrPage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registrationPage.fxml"));
            Parent root = loader.load();
            registrationButton.getScene().setRoot(root);
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
            Main.changeResizableWindow(root,loginButton,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkRight(String UserName)

    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        String selectAllUsers = "SELECT * FROM datafile.useraccounts";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsers);

            while (resultSet.next()) {
                String userName = resultSet.getString("UserName");
                String fullName = resultSet.getString("FirstName") + " " + resultSet.getString("LastName");
                String password = resultSet.getString("Password");
                String positionRight = resultSet.getString("PositionRight");

                System.out.println("User Name: " + userName);
                System.out.println("Full Name: " + fullName);
                System.out.println("Password: " + password);
                System.out.println("Position Right: " + positionRight);
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void displayAllUserData() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        String selectAllUsers = "SELECT * FROM datafile.useraccounts";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsers);

            while (resultSet.next()) {
                String userName = resultSet.getString("UserName");
                String fullName = resultSet.getString("FirstName") + " " + resultSet.getString("LastName");
                String password = resultSet.getString("Password");
                String positionRight = resultSet.getString("PositionRight");

                System.out.println("User Name: " + userName);
                System.out.println("Full Name: " + fullName);
                System.out.println("Password: " + password);
                System.out.println("Position Right: " + positionRight);
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PersonTableView> createArraylistPerson()
    {
        ArrayList<PersonTableView> personArrayList = new ArrayList<>();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        String selectAllUsers = "SELECT * FROM datafile.useraccounts";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsers);

            while (resultSet.next()) {
                String userName = resultSet.getString("UserName");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");

                String password = resultSet.getString("Password");
                String positionRight = resultSet.getString("PositionRight");
                personArrayList.add(new PersonTableView(firstName, lastName, positionRight, positionRight.equals("Admin")));
            }

            return personArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ImgTableView> createArraylistImg()
    {
        ArrayList<ImgTableView> imgTableViewArrayList = new ArrayList<>();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        String selectAllUsers = "SELECT * FROM datafile.images";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsers);

            while (resultSet.next()) {
                int id  = resultSet.getInt("id");

                String imageName  = resultSet.getString("image_name");
                byte[] imageData  = resultSet.getBytes("image_data");
                String createdAt  = resultSet.getString("created_at");
                boolean useIT  = resultSet.getBoolean("useIT");

                System.out.println("Image Name: " + imageName);
                System.out.println("Image Data Length: " + imageData.length);
                System.out.println("Created At: " + createdAt);
                System.out.println("useIT: " + useIT);

                System.out.println("------------------------------");
                imgTableViewArrayList.add(new ImgTableView(id,imageName, imageData, createdAt, false,useIT));
            }

            return imgTableViewArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ImgTableView> createArraylistImgRobot()
    {
        ArrayList<ImgTableView> imgTableViewArrayList = new ArrayList<>();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        String selectAllUsers = "SELECT * FROM datafile.imagesRobot";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsers);

            while (resultSet.next()) {
                int id  = resultSet.getInt("id");

                String imageName  = resultSet.getString("image_name");
                byte[] imageData  = resultSet.getBytes("image_data");
                String createdAt  = resultSet.getString("created_at");
                boolean useIT  = resultSet.getBoolean("useIT");

                System.out.println("Image Name: " + imageName);
                System.out.println("Image Data Length: " + imageData.length);
                System.out.println("Created At: " + createdAt);
                System.out.println("useIT: " + useIT);

                System.out.println("------------------------------");
                imgTableViewArrayList.add(new ImgTableView(id,imageName, imageData, createdAt, false,useIT));
            }

            return imgTableViewArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
