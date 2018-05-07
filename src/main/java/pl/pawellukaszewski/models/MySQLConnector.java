package pl.pawellukaszewski.models;

import java.sql.*;

public class MySQLConnector {

    private final static String DBURL = "jdbc:mysql://5.135.218.27/PawelDataBase";
    private final static String DBLOGIN = "pablito";
    private final static String DBPASSWORD = "pawel123";
    private final static String DBCLASS = "com.mysql.jdbc.Driver";

    private static Connection connection;
    private static MySQLConnector instance;
    private MySQLConnector() {

        try {
            Class.forName(DBCLASS);
            connection = DriverManager.getConnection(DBURL, DBLOGIN, DBPASSWORD);
            System.out.println("Połaczono");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Singleton
    public static MySQLConnector getInstance() {
        if (instance == null) {
            instance = new MySQLConnector();
        }

        return instance;
    }

    public Statement getStatment() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PreparedStatement getPreparedStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

