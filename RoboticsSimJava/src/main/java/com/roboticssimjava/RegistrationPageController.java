package com.roboticssimjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistrationPageController {

    public Button loginButton;
    public Button registrationButton;
    public PasswordField passwordPasswordField;
    public TextField usernameTextField;
    public Label loginMessageLabel;
    public TextField firstNameTextField;
    public TextField lastNameTextField;

    public void setRegistrationButton(ActionEvent actionEvent) {
        if(!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank() && !lastNameTextField.getText().isBlank() && !firstNameTextField.getText().isBlank()){
            validateRegistration();
            if(validateRegistration())
            {
                registrationUser();
            }
        }else{
            loginMessageLabel.setText("Заполнить поля вашими данными.");

        }
    }

    public boolean validateRegistration(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();

        String verifyLogin = "SELECT count(1) FROM useraccounts WHERE UserName = '"+usernameTextField.getText()+"' AND Password = '"+passwordPasswordField.getText()+"';";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);

            while(resultSet.next()){
                if(resultSet.getInt(1) == 1){
                    loginMessageLabel.setText("Такая учетная запись уже существует.");
                    return false;
                }else {
                    return true;
                }
            }
        }catch (Exception e){
            loginMessageLabel.setText("Что-то пошло не так.");
            e.printStackTrace();
        }
        return false;
    }

    public void registrationUser()
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDatabaseLink();
        String addUser = "INSERT INTO useraccounts (FirstName, LastName, UserName, Password, PositionRight) VALUES ('"+firstNameTextField.getText()+"', '"+lastNameTextField.getText()+"', '"+usernameTextField.getText()+"', '"+passwordPasswordField.getText()+"', 'User');";
        String verifyLogin = "SELECT count(1) FROM useraccounts WHERE UserName = '"+usernameTextField.getText()+"' AND Password = '"+passwordPasswordField.getText()+"';";
        try{
            System.out.println(addUser);
            Statement statement = connection.createStatement();
            statement.executeUpdate(addUser);
            ResultSet resultSet = statement.executeQuery(verifyLogin);

            while(resultSet.next()){
                if(resultSet.getInt(1) == 1){
                    loginMessageLabel.setText("Ваша учетная запись успешно зарегестрирована.");
                }else {
                    loginMessageLabel.setText("Что-то пошло не так.");
                }
            }
        }catch (Exception e){
            loginMessageLabel.setText("Что-то пошло не так.");
            e.printStackTrace();
        }
    }


    public void changeLoginPage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root = loader.load();
            loginButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
