package RemoteControl.Server;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
/* Used to recieve server commands then execute them at the client side*/

class ReceiveEvents extends Thread{
    DataInputStream dataInputStream;
    Robot robot = null;
    boolean continueLoop = true;

    public ReceiveEvents(DataInputStream dataInputStream, Robot robot){

        this.dataInputStream=dataInputStream;
        this.robot = robot;
        start(); //Start the thread and hence calling run method
    }



    public void run(){
        Scanner scanner = null;
        scanner = new Scanner(dataInputStream);
        while(continueLoop){
            //recieve commands and respond accordingly

            //while(!scanner.hasNext()) { }
            int command = scanner.nextInt();
            switch(command){
                case-1:
                    robot.mousePress(scanner.nextInt());
                    break;
                case-2:
                    robot.mouseRelease(scanner.nextInt());
                    break;
                case-3:
                    robot.keyPress(scanner.nextInt());
                    break;
                case-4:
                    robot.keyRelease(scanner.nextInt());
                    break;
                case-5:
                    robot.mouseMove(scanner.nextInt(),scanner.nextInt());
                    break;
            }

        }
    }//end function

}//end class
