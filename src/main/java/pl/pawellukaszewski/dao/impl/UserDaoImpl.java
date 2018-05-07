package pl.pawellukaszewski.dao.impl;

import pl.pawellukaszewski.dao.UserDao;
import pl.pawellukaszewski.models.MySQLConnector;
import pl.pawellukaszewski.models.UsersSession;
import pl.pawellukaszewski.models.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private MySQLConnector connector = MySQLConnector.getInstance();
    private UsersSession session = UsersSession.getInstance();


    @Override
    public boolean login(String name, String password) {
        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "SELECT * FROM libraryEmployee WHERE username=?");
        try {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }

            session.setId(resultSet.getInt("id"));
            return (resultSet.getString("password")).equals(Utils.shaHash(password));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean registerEmployee(String name,String employeeName, String employeeLastname,String password) {
        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "SELECT * FROM libraryEmployee WHERE username=?");
        try {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }

            PreparedStatement preparedStatementInsert = connector.getPreparedStatement(
                    "INSERT INTO libraryEmployee VALUES(?,?,?,?,?)");

            preparedStatementInsert.setInt(1, 0);
            preparedStatementInsert.setString(2, name);
            preparedStatementInsert.setString(3,employeeName);
            preparedStatementInsert.setString(4,employeeLastname);
            preparedStatementInsert.setString(5, Utils.shaHash(password));
            preparedStatementInsert.execute();
            preparedStatement.close();
            preparedStatementInsert.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

}
