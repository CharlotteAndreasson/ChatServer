import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try { //Skapar exception om kopplingen bryts eller medd. inte tas emot
            ServerSocket serverSocket = new ServerSocket(9876); // Numret är port
            System.out.println("Waiting for connection...");
            // Wait for connection - BLOCKING
            // Lägg raden nedan i en loop
            Socket clientSocket = serverSocket.accept(); // Direct line to client socket, blockerande anrop
            // starta en thread för denna klient
            // när vi skapar tråden behöver vi skicka över clientSocket

            // Allt nedanför ska göras i tråden

            // Got a connection
            System.out.println("Got connection from " + clientSocket.getInetAddress());
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true); // Tar sträng, omvandlar till bytes och skickar iväg.
            // Skapa evig loop
            while(true) {
                String message = input.readLine();
                System.out.println("Got message: " + message);
                // Exit if client sends text exit
                if(message.equals("exit")) {
                    break;
                }
                // Send message to client
                output.println("You said: " + message);
            }
        } catch (IOException e) {
            System.out.println("Lost connection with client");
        }
    }
}
