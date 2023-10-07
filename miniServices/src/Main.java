public class Main {
    public static void main(String[] args) throws Exception {
        CommandRunner.executeCommand("netsh advfirewall firewall add rule name=\"Block All Internet Access\" dir=out action=block enable=yes");
        CommandRunner.executeCommand("netsh advfirewall firewall delete rule name=\"Block All Internet Access\"");
        CommandRunner.executeCommand("start chrome \"https://www.youtube.com/\"");
        CommandRunner.executeCommand("start C:\\Users\\Saif\\AppData\\Local\\Microsoft\\Teams\\Update.exe --processStart \"Teams.exe\"");
    }
}