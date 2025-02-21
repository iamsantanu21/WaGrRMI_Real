import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServerMain {
    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServerImpl();
            Registry registry = LocateRegistry.createRegistry(5000);
            registry.rebind("ChatServer", server);
            System.out.println("Chat server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
