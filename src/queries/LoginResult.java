package queries;

import dataTypes.User;

public class LoginResult {
    public User user;
    public String message;
    //null if user and pass doesn't match

    public LoginResult(User user, String message) {
        this.user = user;
        this.message = message;
    }
}
