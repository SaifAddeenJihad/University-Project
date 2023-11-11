package FileTransfer;

public class Main {

    public static void main(String[] args)
    {
        String result = "haha";
        switch (result)
        {
            case "haha" :
                System.out.println("haha");
                break;
            case "lala" :
                System.out.println("lala");
                    break;
        }
        System.out.println("Hello world!");
        //sender
        String savePath = "D:\\shared";
        String sendPath = "C:\\Users\\Saif\\Downloads\\network-core\\network-core.7z";
       // FileSender fileSender = new FileSender(savePath);
       // fileSender.start();
        //receiver
        //InetAddress ip = InetAddress.getByName("127.0.0.1");
        //FileReceiver fileReceiver=new FileReceiver(ip);
        //fileReceiver.start();

    }

}