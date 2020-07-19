package commiunication;

import java.util.HashMap;

public class ClientsInteractHandler {
    public static HashMap<String,SocketOutput> usernames= new HashMap<>();

    public static void sendTo(String username, Object message){
        if(usernames.get(username)!=null)
            usernames.get(username).send(message);
    }

    public static void setUsernameSocketOutput(String username, SocketOutput socketOutput){
        usernames.put(username,socketOutput);
    }
}
