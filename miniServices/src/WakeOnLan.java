import java.io.IOException;

public class WakeOnLan {

    public static void main(String[] args) throws Exception {
        String wolcmdPath = "C:\\Users\\Saif\\Downloads\\wolcmd\\WolCmd.exe"; // Replace this with the full path to wolcmd.exe
        String macAddress = "38:F3:AB:91:3E:2D"; // Replace with the MAC address of the target device
        String broadcastAddress = "192.168.100.3"; // Replace with the broadcast IP address of the network
        String portNumber = "9"; // Port number for Wake-on-LAN

        // Use the WakeMeOnLan tool to wake the device
            String command = wolcmdPath + " " + macAddress + " " + broadcastAddress + " 255.255.255.0 " + portNumber;
            CommandRunner.executeCommand(command);

    }
}