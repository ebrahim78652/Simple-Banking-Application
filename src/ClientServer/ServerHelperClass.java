package ClientServer;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import Exceptions.*;

public class ServerHelperClass implements Runnable {

    Socket socket;
    String username;
    public ServerHelperClass(Socket socket){
        this.socket= socket;
    }
    @Override
    public void run() {
        System.out.println("in the run method: ");
        System.out.println(Thread.currentThread().getName());
        try (OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os);
             ObjectInputStream ios = new ObjectInputStream(is);
        ) {

            while (true) {
                Action action = (Action) ios.readObject();
                if (action == Action.LOGIN_REQUEST) {
                    ClientRequest clientRequest1 = (ClientRequest) ios.readObject();
                    username= clientRequest1.getUsername();
                    String password= clientRequest1.getPassword();
                    if(Server.bank.checkUsernameInDatabase(username, password)){
                        ServerResponse response= new ServerResponse();
                        response.setMoneyInAccount(Server.bank.getBalance(username));
                        System.out.println(response.moneyInAccount);
                        oos.writeObject(Action.LOGIN_ACCEPTED);
                        System.out.println("Sent the action");
                        oos.writeObject(response);
                        System.out.println("Sent the response with the amount in bank");
                        oos.flush();
                    }
                    else{
                        //now we will have to tell the client that the username or password is false
                        oos.writeObject(Action.ERROR);
                        oos.flush();
                    }
                }

                else if(action== Action.DETAILS_OF_RECEIVER){
                    System.out.println("The table before the transaction");
                    Server.bank.printTable();
                    ClientRequest clientRequest2= (ClientRequest)ios.readObject();
                    String receiver= clientRequest2.getReceiver();
                    Double amount=  clientRequest2.getAmountToSend();
                    try{
                        Server.bank.doTransfer(username, receiver,amount);
                    }
                    catch(InsufficientFundsException Isf){
                        oos.writeObject(Action.ERROR);
                    }

                    System.out.println("the table after the transaction");
                    Server.bank.printTable();


                    //just testing if the server correctly gets the values of the strings or no
                    ServerResponse response= new ServerResponse();
                    response.setMoneyInAccount(Server.bank.getBalance(username));
                    oos.writeObject(Action.DETAILS_OF_RECEIVER_CORRECT);
                    oos.writeObject(response);
                }
            }

        } catch (IOException e) {

        } catch (ClassNotFoundException c) {
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
