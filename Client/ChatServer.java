import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
    void registerUser(String username, ChatClient client) throws RemoteException;
    void createGroup(String groupName, String admin) throws RemoteException;
    void addMember(String groupName, String admin, String member) throws RemoteException;
    void removeMember(String groupName, String admin, String member) throws RemoteException;
    void sendMessage(String groupName, String sender, String message) throws RemoteException;
    void viewMessages(String groupName, String user) throws RemoteException;
}
