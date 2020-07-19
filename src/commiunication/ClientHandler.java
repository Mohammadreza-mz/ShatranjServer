package commiunication;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import DB.GameDB;
import DB.UserDB;
import commons.dataTypes.EndState;
import commons.dataTypes.History;
import commons.queries.*;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private final SocketOutput socketOutput;

    public ClientHandler(Socket socket, SocketOutput socketOutput) {
        this.socket = socket;
        this.socketOutput = socketOutput;
    }

    @Override
    public void run() {

        DataInputStream dis=null;
        ObjectInputStream ois=null;
        try {
            dis= new DataInputStream(socket.getInputStream());
            ois= new ObjectInputStream(dis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String username=null;
        try {
            while (true) {
                Object object= ois.readObject();

                if(object instanceof LoginRequest){
                    LoginResult loginResult=UserDB.login((LoginRequest) object);
                    if(loginResult.user!=null) {
                        username = loginResult.user.username;
                        ClientsInteractHandler.setUsernameSocketOutput(username,socketOutput);
                    }
                    socketOutput.send(loginResult);
                }

                if(object instanceof SignUpRequest){
                    SignUpResult signUpResult=UserDB.signUp((SignUpRequest) object);
                    if(signUpResult.user != null)
                        username= signUpResult.user.username;
                    socketOutput.send(signUpResult);
                }

                if(object instanceof Logout){
                    UserDB.logout((Logout) object);
                    ClientsInteractHandler.setUsernameSocketOutput(username,null);
                    username=null;
                }

                if(object instanceof DeleteAccount){
                    UserDB.deleteAccount((DeleteAccount) object);
                    ClientsInteractHandler.setUsernameSocketOutput(username,null);
                    username=null;
                }

                if(object instanceof ChangePassword){
                    UserDB.changePassword((ChangePassword) object);
                }

                if(object instanceof SearchRequest){
                    socketOutput.send(UserDB.search((SearchRequest) object));
                }

                if(object instanceof ScoreboardRequest){
                    socketOutput.send(GameDB.Scoreboard((ScoreboardRequest) object));
                }

                if(object instanceof GameRequest){
                    ClientsInteractHandler.sendTo(((GameRequest) object).to, object);
                }

                if(object instanceof GameAccepted){
                    ClientsInteractHandler.sendTo(((GameAccepted) object).to, object);
                }

                if (object instanceof GameFailed){
                    ClientsInteractHandler.sendTo(((GameFailed) object).to,object);
                }

                if(object instanceof GameUpdate){
                    //update viewer
                    ClientsInteractHandler.sendTo(((GameUpdate) object).to,object);
                }

                if(object instanceof GameEnd){
                    ClientsInteractHandler.sendTo(((GameEnd) object).to,object);
                    GameEnd tmp= ((GameEnd) object);
                    if(tmp.endState.equals(EndState.WON)){
                        UserDB.updateHistory(tmp.from,new History(tmp.from, tmp.to, tmp.from,tmp.board,tmp.timeStamp,tmp.totalMoves));
                        UserDB.updateHistory(tmp.to,new History(tmp.to, tmp.from, tmp.from,tmp.board,tmp.timeStamp,tmp.totalMoves));
                    }
                    else{
                        UserDB.updateHistory(tmp.from,new History(tmp.from, tmp.to, tmp.to,tmp.board,tmp.timeStamp,tmp.totalMoves));
                        UserDB.updateHistory(tmp.to,new History(tmp.to, tmp.from, tmp.to,tmp.board,tmp.timeStamp,tmp.totalMoves));
                    }
                }

                if(object instanceof GameMessages){
                    ClientsInteractHandler.sendTo(((GameMessages) object).username,object);
                }
            }
        } /*catch (Exception e) {
            e.printStackTrace();
        }*/

        catch (IOException e){
            if(username!=null)
                UserDB.logout(new Logout(username));
            username= null;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
