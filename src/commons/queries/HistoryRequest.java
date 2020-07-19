package commons.queries;

import java.io.Serializable;

public class HistoryRequest implements Serializable {
    public String username;

    public HistoryRequest(String username) {
        this.username = username;
    }
}
