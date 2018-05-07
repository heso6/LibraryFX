package pl.pawellukaszewski.dao.impl;

import pl.pawellukaszewski.dao.MainDao;
import pl.pawellukaszewski.models.MySQLConnector;
import pl.pawellukaszewski.models.UsersSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainDaoImpl implements MainDao {

    private MySQLConnector connector = MySQLConnector.getInstance();
    private UsersSession session = UsersSession.getInstance();


    @Override
    public List<String> getAllCustomers() {
        List<String> nameList = new ArrayList<>();


        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "SELECT id, name, lastname FROM user ORDER BY `user`.`id` DESC");

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                nameList.add(resultSet.getString("id") + "\t\t\t\t" + resultSet.getString("name") + " | " + resultSet.getString("lastname"));
            }

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nameList;
    }

    @Override
    public boolean removeLend(int id) {
        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "DELETE FROM lend WHERE id = ?"
        );
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeBook(int id) {
        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "DELETE FROM book WHERE id = ?"
        );
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCustomer(int id) {
        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "DELETE FROM user WHERE id = ?"
        );
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean registerCustomer(String name, String lastname, String phone) {
        try {

            PreparedStatement preparedStatementInsert = connector.getPreparedStatement(
                    "INSERT INTO user VALUES(?,?,?,?)");

            preparedStatementInsert.setInt(1, 0);
            preparedStatementInsert.setString(2, name);
            preparedStatementInsert.setString(3, lastname);
            preparedStatementInsert.setString(4, phone);
            preparedStatementInsert.execute();
            preparedStatementInsert.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean addBook(String title, String author, String publishedYear) {
        PreparedStatement preparedStatement = connector.getPreparedStatement("INSERT INTO book VALUES(?,?,?,?)");
        try {
            preparedStatement.setNull(1, 0);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, author);
            preparedStatement.setString(4, publishedYear);
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public List<String> getAllBooks() {
        List<String> booksList = new ArrayList<>();


        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "SELECT id,title,author, publishedYear FROM book ORDER BY `book`.`id` DESC");

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                booksList.add(resultSet.getString("id") + "\t\t\t" + resultSet.getString("title") + " | " + resultSet.getString("author") +
                        " - " + resultSet.getString("publishedYear"));

            }
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }


    @Override
    public boolean addLend(int userId, int bookId, String returnDate) {
        try {

            PreparedStatement preparedStatementInsert = connector.getPreparedStatement(
                    "INSERT INTO lends VALUES(?,?,?,?,?)");

            preparedStatementInsert.setInt(1, 0);
            preparedStatementInsert.setInt(2, userId);
            preparedStatementInsert.setInt(3, bookId);
            preparedStatementInsert.setDate(4, null);
            preparedStatementInsert.setString(5, returnDate);
            preparedStatementInsert.execute();
            preparedStatementInsert.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public List<String> getAllLends() {
        List<String> lendsList = new ArrayList<>();


        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "SELECT id,userID,bookID, returnDate, date FROM lends ORDER BY `lends`.`id` DESC");

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lendsList.add(resultSet.getString("id") + "\t\t\t" + resultSet.getString("userID") + " | " + resultSet.getString("bookID") +
                        " | " + resultSet.getString("returnDate") + " | " + resultSet.getString("date"));

            }
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lendsList;
    }

}

