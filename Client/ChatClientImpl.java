import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
    private final ChatServer server;
    private final String username;
    private final Scanner scanner;

    public ChatClientImpl(String username, ChatServer server) throws RemoteException {
        this.username = username;
        this.server = server;
        this.scanner = new Scanner(System.in);
        server.registerUser(username, this);
    }

    @Override
    public void receiveNotification(String message) throws RemoteException {
        System.out.println("\n" + message);
    }

    public void startChat() throws RemoteException {
        while (true) {
            System.out.println("\n1. Create Group\n2. Add Member\n3. Remove Member\n4. Send Message\n5. View Messages\n6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> server.createGroup(scanner.nextLine(), username);
                case 2 -> server.addMember(scanner.nextLine(), username, scanner.nextLine());
                case 3 -> server.removeMember(scanner.nextLine(), username, scanner.nextLine());
                case 4 -> server.sendMessage(scanner.nextLine(), username, scanner.nextLine());
                case 5 -> server.viewMessages(scanner.nextLine(), username);
                case 6 -> System.exit(0);
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }
}
