import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServerMain {
    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServerImpl();
            ChatServer stub = (ChatServer) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(4000);
            registry.rebind("ChatServer", stub);

            System.out.println("Chat server is running on 10.10.32.115:4000...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
