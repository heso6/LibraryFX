package pl.pawellukaszewski.dao;

public interface UserDao {
    boolean login(String name, String password);
    boolean registerEmployee(String name,String employeeName, String employeeLastname,String password);

}

