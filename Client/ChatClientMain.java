import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClientMain {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("10.10.32.115", 4000);
            ChatServer server = (ChatServer) registry.lookup("ChatServer");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            ChatClientImpl client = new ChatClientImpl(username, server);
            client.startChat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
