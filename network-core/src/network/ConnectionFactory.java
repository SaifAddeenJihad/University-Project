package network;

public class ConnectionFactory {

    public static IConnection getIConnection(String connectionType){
        return switch (connectionType) {
            case "TCPClient" -> new TCPClient();
            case "TCPServer" -> new TCPServer();
            case "UDPClient" -> new UDPClient();
            case "UDPServer" -> new UDPServer();
            case "multicastSender" -> new MulticastSender();
            case "multicastReceiver" -> new MulticastReceiver();
            default -> null;
        };
    }
}
