package pl.pawellukaszewski.dao;

import java.util.List;

public interface MainDao {
    boolean registerCustomer(String name, String lastname, String phone);

    boolean addBook(String title, String author, String publishedYear);

    boolean addLend(int userId, int bookId, String returnDate);

    List<String> getAllLends();

    List<String> getAllBooks();

    List<String> getAllCustomers();

    boolean removeCustomer(int id);

    boolean removeBook(int id);

    boolean removeLend(int id);
}
