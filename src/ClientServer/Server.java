package ClientServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import databank.*;


public class Server {


    static public Bank bank;
    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("in the main method of server: ");
        System.out.println(Thread.currentThread().getName());

        Server server= new Server();

        bank= new Bank();
        bank.init();
        bank.initTable();
        bank.insertInitialDataUsingStatements();

        server.runServer();
    }

    public void runServer() throws IOException {
        try(ServerSocket ss= new ServerSocket(5000)){
            while(true){
                Socket socket = ss.accept();
                //now make a new thread
                new Thread(new ServerHelperClass(socket)).start();
            }
        }
    }

}
