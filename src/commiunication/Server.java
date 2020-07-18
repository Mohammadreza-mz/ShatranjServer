package commiunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;
    private static int port= 8080;

    public static void main(String[] args) throws IOException {
        serverSocket= new ServerSocket(port);
        while (true){
            Socket socket= serverSocket.accept();
            new Thread(new ClientHandler(socket)).start();
        }
    }

}
