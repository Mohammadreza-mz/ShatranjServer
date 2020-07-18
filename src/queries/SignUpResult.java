package queries;

import java.io.Serializable;

public class SignUpResult implements Serializable {
    public boolean wasSuccessful;

    public SignUpResult(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }
}
