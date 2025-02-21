import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    private final Map<String, ChatClient> clients = new HashMap<>();
    private final Map<String, String> groupAdmins = new HashMap<>();
    private final Map<String, List<String>> groupMembers = new HashMap<>();
    private final Map<String, List<String>> groupMessages = new HashMap<>();

    public ChatServerImpl() throws RemoteException {}

    @Override
    public void registerUser(String username, ChatClient client) throws RemoteException {
        clients.put(username, client);
        client.receiveNotification("✅ You are registered successfully!");
    }

    @Override
    public void createGroup(String groupName, String admin) throws RemoteException {
        if (groupMembers.containsKey(groupName)) {
            clients.get(admin).receiveNotification("❌ Group already exists.");
            return;
        }
        groupAdmins.put(groupName, admin);
        groupMembers.put(groupName, new ArrayList<>(Collections.singletonList(admin)));
        groupMessages.put(groupName, new ArrayList<>());
        clients.get(admin).receiveNotification("✅ Group '" + groupName + "' created successfully!");
    }

    @Override
    public void addMember(String groupName, String admin, String member) throws RemoteException {
        if (!groupMembers.containsKey(groupName) || !groupAdmins.get(groupName).equals(admin)) {
            clients.get(admin).receiveNotification("❌ You are not allowed to add members.");
            return;
        }
        groupMembers.get(groupName).add(member);
        if (clients.containsKey(member)) {
            clients.get(member).receiveNotification("🔔 You have been added to group '" + groupName + "'");
        }
    }

    @Override
    public void removeMember(String groupName, String admin, String member) throws RemoteException {
        if (!groupMembers.containsKey(groupName) || !groupAdmins.get(groupName).equals(admin)) {
            clients.get(admin).receiveNotification("❌ You are not allowed to remove members.");
            return;
        }
        groupMembers.get(groupName).remove(member);
        if (clients.containsKey(member)) {
            clients.get(member).receiveNotification("🔔 You have been removed from group '" + groupName + "'");
        }
    }

    @Override
    public void sendMessage(String groupName, String sender, String message) throws RemoteException {
        if (!groupMembers.containsKey(groupName) || !groupMembers.get(groupName).contains(sender)) {
            clients.get(sender).receiveNotification("❌ You are not in this group.");
            return;
        }
        String msg = "🔔 " + sender + ": " + message;
        groupMessages.get(groupName).add(msg);
        for (String member : groupMembers.get(groupName)) {
            if (clients.containsKey(member)) {
                clients.get(member).receiveNotification(msg);
            }
        }
    }

    @Override
    public void viewMessages(String groupName, String user) throws RemoteException {
        if (!groupMessages.containsKey(groupName)) {
            clients.get(user).receiveNotification("❌ No messages found.");
            return;
        }
        clients.get(user).receiveNotification("📩 Messages:\n" + String.join("\n", groupMessages.get(groupName)));
    }
}
