package pl.pawellukaszewski.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.pawellukaszewski.dao.MainDao;
import pl.pawellukaszewski.dao.impl.MainDaoImpl;
import pl.pawellukaszewski.models.UsersSession;
import pl.pawellukaszewski.models.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    TextField textNameNewCustomer, textLastnameNewCustomer, textPhoneNewCustomer, textTitleNewbook,
            textAuthorNewbook, textPublishedYearNewbook, textCustomeridAddLend, textbookidAddLend,
            textReturnDayAddLend, textRemoveCustomer, textRemoveBook, textRemoveLend;

    @FXML
    Button buttonNewCustomer, buttonNewBook, buttonAddLend, buttonRemoveCustomer, buttonRemoveBook,
            buttonLogout, buttonRemoveLend;

    @FXML
    ListView<String> listsCustomer, listsBooks, listsLend;

    private UsersSession session = UsersSession.getInstance();
    private ObservableList customerItems;
    private MainDao mainDao = new MainDaoImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCustomers();
        loadBooks();
        loadLends();

        textNameNewCustomer.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    System.out.println("Zarejestrowano pomyslnie");
                    tryRegisterNewCustomer();
            }
        });
        textLastnameNewCustomer.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    System.out.println("Zarejestrowano pomyslnie");
                    tryRegisterNewCustomer();
            }
        });

        textPhoneNewCustomer.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    System.out.println("Zarejestrowano pomyslnie");
                    tryRegisterNewCustomer();
            }
        });


        buttonNewCustomer.setOnMouseClicked(e -> tryRegisterNewCustomer());
        buttonNewBook.setOnMouseClicked(e -> tryAddBook());
        buttonAddLend.setOnMouseClicked(e -> tryAddLend());
        buttonRemoveCustomer.setOnMouseClicked(e -> tryRemoveCustomer());
        buttonLogout.setOnMouseClicked(e -> logOut());
        buttonRemoveBook.setOnMouseClicked(e -> tryRemoveBook());
        buttonRemoveLend.setOnMouseClicked(e -> tryRemoveLend());
    }

    private void tryRemoveLend() {
        int lendID = Integer.parseInt((textRemoveLend.getText()));


        if (!checkRemoveLendData()) {
            return;
        }
        if (mainDao.removeLend(lendID)) {

            Utils.createSimpleDialog("Deleted", "", "Usunieto poprawnie");
        }

        textRemoveLend.clear();

        loadLends();
    }

    private boolean checkRemoveLendData() {
        //        if (textRemoveLend.getText().trim().isEmpty()) {
//            Utils.createSimpleDialog("Deleted", "", "Pola nie moga byc puste");
//            return false;
//        }
//
        return true;
    }


    private boolean checkRemoveBookData() {

//        if (textRemoveBook.getText().trim().isEmpty()) {
//            Utils.createSimpleDialog("Deleted", "", "Pola nie moga byc puste");
//            return false;
//        }
//
        return true;
    }

    private void tryRemoveBook() {
        int bookID = Integer.parseInt((textRemoveBook.getText()));


        if (!checkRemoveBookData()) {
            return;
        }
        if (mainDao.removeBook(bookID)) {

            Utils.createSimpleDialog("Deleted", "", "Usunieto poprawnie");
        }

        textRemoveBook.clear();

        loadBooks();

    }

    private boolean checkRemoveCustomerData() {

//        if (textRemoveCustomer.getText().trim().isEmpty()) {
//            Utils.createSimpleDialog("Deleted", "", "Pola nie moga byc puste");
//            return false;
//        }
//
        return true;
    }

    private void tryRemoveCustomer() {
        int userID = Integer.parseInt(((textRemoveCustomer.getText())));


        if (!checkRemoveCustomerData()) {
            return;
        }
        if (mainDao.removeCustomer(userID)) {

            Utils.createSimpleDialog("Deleted", "", "Usunieto poprawnie");
        }
        loadCustomers();
        textRemoveCustomer.clear();


    }


    private boolean checkRegistrationNewCustomerData() {
        String name = textNameNewCustomer.getText();
        String lastname = textLastnameNewCustomer.getText();
        String phoneNumber = textNameNewCustomer.getText();

        if (name.isEmpty() || lastname.isEmpty() || phoneNumber.isEmpty()) {
            Utils.createSimpleDialog("Rejestracja", "", "Pola nie moga byc puste");
            return false;
        }
        if (name.length() < 5 || lastname.length() < 5 || phoneNumber.length() < 5) {
            Utils.createSimpleDialog("Rejestracja", "", "wyrazy muszą byc dluzsze niz 5 znakow");
            return false;
        }

        return true;
    }

    private void tryRegisterNewCustomer() {
        String name = textNameNewCustomer.getText();
        String lastname = textLastnameNewCustomer.getText();
        String phoneNumber = textPhoneNewCustomer.getText();


        if (!checkRegistrationNewCustomerData()) {
            return;
        }
        if (mainDao.registerCustomer(name, lastname, phoneNumber)) {

            Utils.createSimpleDialog("Logowanie", "", "Zarejestrowano poprawnie");
        } else {
            Utils.createSimpleDialog("Logowanie", "", "Bład podczas rejestracji");
        }
        textNameNewCustomer.clear();
        textLastnameNewCustomer.clear();
        textPhoneNewCustomer.clear();
        loadCustomers();
    }

    private void loadCustomers() {

        customerItems = FXCollections.observableArrayList(mainDao.getAllCustomers());
        listsCustomer.setItems(customerItems);
    }

    private void tryAddBook() {
        String title = textTitleNewbook.getText();
        String author = textAuthorNewbook.getText();
        String publishedYear = textTitleNewbook.getText();


        if (!checkAddBookData()) {
            return;
        }
        if (mainDao.addBook(title, author, publishedYear)) {

            Utils.createSimpleDialog("Add Book", "", "Książe dodano poprawnie");
        } else {
            Utils.createSimpleDialog("Add Book", "", "Bład podczas dodawania ksiazki");
        }

        textTitleNewbook.clear();
        textAuthorNewbook.clear();
        textTitleNewbook.clear();
        loadBooks();
    }


    private boolean checkAddBookData() {
        String title = textTitleNewbook.getText();
        String author = textAuthorNewbook.getText();
        String publishedYear = textPublishedYearNewbook.getText();

        if (title.isEmpty() || author.isEmpty() || publishedYear.isEmpty()) {
            Utils.createSimpleDialog("Add Book", "", "Pola nie moga byc puste");
            return false;
        }
        if (publishedYear.length() < 4) {
            Utils.createSimpleDialog("Add Book", "", "wyrazy muszą byc dluzsze niz 5 znakow");
            return false;
        }

        return true;
    }

    private void loadBooks() {

        customerItems = FXCollections.observableArrayList(mainDao.getAllBooks());
        listsBooks.setItems(customerItems);
    }

    private void tryAddLend() {
        int userId = (textCustomeridAddLend.getText().trim().length());
        int bookId = (textbookidAddLend.getText().trim().length());
        String returnDay = textReturnDayAddLend.getText();

        if (!checkAddLendData()) {
            return;
        }

        if (mainDao.addLend(userId, bookId, returnDay)) {

            Utils.createSimpleDialog("Logowanie", "", "Zarejestrowano poprawnie");
        } else {
            Utils.createSimpleDialog("Logowanie", "", "Bład podczas rejestracji");
        }
        loadLends();
    }

    private boolean checkAddLendData() {
        String returnDay = textReturnDayAddLend.getText();

        if (textCustomeridAddLend.getText().trim().isEmpty() || textbookidAddLend.getText().trim().isEmpty() || returnDay.isEmpty()) {
            Utils.createSimpleDialog("Add Lend", "", "Pola nie moga byc puste");
            return false;
        }
        if (returnDay.length() < 10) {
            Utils.createSimpleDialog("Add Lend", "", "Nie istniejace dane lub data za krótka (yyyy-MM-dd)");
            return false;
        }

        return true;
    }

    private void loadLends() {

        customerItems = FXCollections.observableArrayList(mainDao.getAllLends());
        listsLend.setItems(customerItems);
    }


    private void logOut() {
        session.setLoggedIn(false);
        session.setUsername(null);
        session.setId(0);

        Stage stage = (Stage) buttonLogout.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginView.fxml"));
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}






