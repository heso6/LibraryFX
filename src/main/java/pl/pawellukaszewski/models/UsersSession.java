package pl.pawellukaszewski.models;

import lombok.Data;
import lombok.Getter;

@Data
public class UsersSession {
    private static UsersSession ourInstance = new UsersSession();

    public static UsersSession getInstance() {
        return ourInstance;
    }

    private UsersSession() {
    }

    private int id;
    @Getter
    private  String username;
    private boolean isLoggedIn;

}
