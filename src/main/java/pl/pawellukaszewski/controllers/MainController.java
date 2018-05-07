package pl.pawellukaszewski.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import pl.pawellukaszewski.dao.MainDao;
import pl.pawellukaszewski.dao.impl.MainDaoImpl;
import pl.pawellukaszewski.models.UsersSession;
import pl.pawellukaszewski.models.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    TextField textNameNewCustomer, textLastnameNewCustomer, textPhoneNewCustomer, textTitleNewbook, textAuthorNewbook,
            textPublishedYearNewbook, textCustomeridAddLend, textbookidAddLend, textReturnDayAddLend, textRemoveCustomer;

    @FXML
    Button buttonNewCustomer, buttonNewBook, buttonAddLend, buttonRemoveCustomer;

    @FXML
    ListView<String> listsCustomer, listsBooks, listsLend;

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
    }

    private boolean checkRemoveCustomerData() {

        if (textRemoveCustomer.getText().trim().isEmpty()) {
            Utils.createSimpleDialog("Deleted", "", "Pola nie moga byc puste");
            return false;
        }

        return true;
    }

    private void tryRemoveCustomer() {
        int userID = (textRemoveCustomer.getText().trim().length());


        if (!checkRemoveCustomerData()) {
            return;
        }
        if (mainDao.removeCustomer(userID)) {

            Utils.createSimpleDialog("Deleted", "", "Usunieto poprawnie");
        }

        textRemoveCustomer.clear();

        loadCustomers();

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


}






