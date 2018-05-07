package pl.pawellukaszewski.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.pawellukaszewski.dao.UserDao;
import pl.pawellukaszewski.dao.impl.UserDaoImpl;
import pl.pawellukaszewski.models.UsersSession;
import pl.pawellukaszewski.models.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField textLogSection, textLogEmpRegistration, textNameEmpRegistration, textLastnameEmpRegistration;
    @FXML
    PasswordField passwordLogSection, passwordLogEmpRegistration, passwordRepeatLogEmpRegistration;
    @FXML
    Button buttonLogSection,  buttonEmpRegistration;

    private UsersSession usersSession = UsersSession.getInstance();
    private UserDao userDao = new UserDaoImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textLogSection.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    System.out.println("zalogowano");
                    tryLogin();
            }
        });
        passwordLogSection.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    System.out.println("zalogowano");
                    tryLogin();
            }
        });


        buttonLogSection.setOnMouseClicked(e -> tryLogin());
        buttonEmpRegistration.setOnMouseClicked(e -> tryRegisterNewEmployee());

    }


    private boolean checkLoginData() {
        String login = textLogSection.getText();
        String password = passwordLogSection.getText();

        if (login.isEmpty() || password.isEmpty()) {
            Utils.createSimpleDialog("Logowanie", "", "Pola nie moga byc puste");
            return false;
        }
        if (login.length() <= 3 || password.length() <= 5) {
            Utils.createSimpleDialog("Logowanie", "", "Dane za krótkie");
            return false;
        }
        return true;
    }


    private void tryLogin() {
        String login = textLogSection.getText();
        String password = passwordLogSection.getText();

        if (!checkLoginData()) {
            return;
        }

        if (userDao.login(login, password)) {
            usersSession.setUsername(login);
            usersSession.setLoggedIn(true);

            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainView.fxml"));
                Stage stageRoot = (Stage) buttonLogSection.getScene().getWindow();
                stageRoot.setScene(new Scene(root, 660, 400));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Utils.createSimpleDialog("Logowanie", "", "Masz bład w danych");
        }
    }

    private boolean checkRegistrationData() {
        String login = textLogEmpRegistration.getText();
        String password = passwordLogEmpRegistration.getText();
        String passwordRepeat = passwordRepeatLogEmpRegistration.getText();

        if (login.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
            Utils.createSimpleDialog("Rejestracja", "", "Pola nie moga byc puste");
            return false;
        }
        if (!password.equals(passwordRepeat)) {
            Utils.createSimpleDialog("Rejestracja", "", "Hasła musza byc takie same");
            return false;
        }
        if (password.length() < 5 || login.length() < 5) {
            Utils.createSimpleDialog("Rejestracja", "", "Hasło musi byc dluzsze niz 5 znakow");
            return false;
        }
        if (login.equals(password)) {
            Utils.createSimpleDialog("Rejestracja", "", "Login i haslo musza sie roznic");
            return false;
        }

        return true;
    }

    private void tryRegisterNewEmployee() {
        String login = textLogEmpRegistration.getText();
        String password = passwordLogEmpRegistration.getText();
        String employeeName = textNameEmpRegistration.getText();
        String employeeLastname = textLastnameEmpRegistration.getText();


        if (!checkRegistrationData()) {
            return;
        }
        if (userDao.registerEmployee(login, employeeName, employeeLastname, password)) {
            Utils.createSimpleDialog("Logowanie", "", "Zarejestrowano poprawnie");
        } else {
            Utils.createSimpleDialog("Logowanie", "", "Login juz istnieje");
        }
    }
}
