package commons.queries;

import java.io.Serializable;

public class GameMessages implements Serializable {
    public String username,message;

    public GameMessages(String username, String message) {
        this.username = username;
        this.message = message;
    }
}
