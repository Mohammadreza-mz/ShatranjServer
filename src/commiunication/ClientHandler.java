package commiunication;

import java.io.*;
import java.net.Socket;

import DB.UserDB;
import queries.*;

public class ClientHandler implements Runnable{
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        DataInputStream dis=null;
        ObjectInputStream ois=null;
        DataOutputStream dos=null;
        ObjectOutputStream oos=null;
        try {
            dis= new DataInputStream(socket.getInputStream());
            ois= new ObjectInputStream(dis);
            dos= new DataOutputStream(socket.getOutputStream());
            oos= new ObjectOutputStream(dos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String username=null;
        try {
            while (true) {
                Object object= ois.readObject();

                if(object instanceof LoginRequest){
                    LoginResult loginResult=UserDB.login((LoginRequest) object);
                    if(loginResult.user!=null)
                        username=loginResult.user.username;
                    oos.writeObject(loginResult);
                }

                if(object instanceof SignUpRequest){
                    SignUpResult signUpResult=UserDB.signUp((SignUpRequest) object);
                    if(signUpResult.wasSuccessful)
                        username=((SignUpRequest) object).user.username;
                    oos.writeObject(signUpResult);
                }

                if(object instanceof Logout){
                    UserDB.logout((Logout) object);
                    username=null;
                }

            }
        }

        //catch block should be updated if try block handle more type of queries
        catch (IOException | ClassNotFoundException e){
            if(username!=null)
                UserDB.logout(new Logout(username));
        }

    }
}
