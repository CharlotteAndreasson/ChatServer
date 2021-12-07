import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    ServerSocket serverSocket;
    ArrayList<Socket> clientSockets;
    BlockingQueue<Message> broadcastQueue;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port); // Numret är port
            clientSockets = new ArrayList<>();
            broadcastQueue = new LinkedBlockingQueue<>();
        } catch (IOException e) {
            System.out.println("Lost connection with client");
        }
    }

    public void run() {
            try {
                new BroadcastThread(this).start();
                System.out.println("Waiting for connection...");
                // Wait for connection - BLOCKING
                while (true) {
                    Socket clientSocket = serverSocket.accept(); // Direct line to client socket, blockerande anrop
                    clientSockets.add(clientSocket);
                    new ClientThread(clientSocket, this).start();
                }
            } catch (IOException e) {
                System.out.println("Lost connection with client");
            }
    }

    public ArrayList<Socket> getClientSockets() {
        return clientSockets;
    }

    public BlockingQueue<Message> getBroadcastQueue() {
        return broadcastQueue;
    }
}