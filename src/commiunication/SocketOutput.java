package commiunication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketOutput{
    Socket socket;
    DataOutputStream dos=null;
    ObjectOutputStream oos=null;

    public SocketOutput(Socket socket) {
        this.socket = socket;

        try {
            dos= new DataOutputStream(socket.getOutputStream());
            oos= new ObjectOutputStream(dos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Object object){
        try {
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
