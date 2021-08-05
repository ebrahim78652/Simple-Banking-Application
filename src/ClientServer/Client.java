
package ClientServer;
import GUI.*;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;


//this is where the meat of our code is going to be
//first the client is going to get the information from the View.

public class Client  {


    public String user;
    public String password;
    public View view;
    public View2 view2;

    public void connectServer() throws IOException {
        System.out.println("ConnectServer method called: ");
        System.out.println(Thread.currentThread().getName());

        try{Socket socket = new Socket(InetAddress.getLocalHost(), 5000);
            OutputStream os= socket.getOutputStream();
            InputStream is= socket.getInputStream();
            ObjectOutputStream oos= new ObjectOutputStream(os);
            ObjectInputStream ois= new ObjectInputStream(is);

            //first we will send a request to check if the login info is correct;
            ClientRequest clientRequest= new ClientRequest(user, password);
            oos.writeObject(Action.LOGIN_REQUEST);
            oos.writeObject(clientRequest);
            oos.flush();

            //now we will receive the first response from the server.
            //if its an error then we will do the following.
            Action action= (Action)ois.readObject();
            if(action==Action.ERROR){
                showError();
            }
            else if(action==Action.LOGIN_ACCEPTED){
                view.jumpToNextWindow();
                double balance=0;
                try {
                    ServerResponse response= (ServerResponse)ois.readObject();
                    balance= response.getMoneyInAccount();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

                view2.welcomeTextLabel.setText("Welcome "+ user);
                view2.balanceLabel.setText("Your balance is:"+ Double.toString(balance));
                // ////////////////////////////////////////////////////////////////////////////////////

                view2.transferButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
                    String receiver = view2.transferRequestTextField.getText();
                    double amount= Double.parseDouble(view2.amountToTransferTextField.getText());
                    //stopped here.


                    //sending the IBAN and the amount To transfer to the server
                    ClientRequest clientRequest2= new ClientRequest(receiver, amount);
                    try{
                        oos.writeObject(Action.DETAILS_OF_RECEIVER);
                        oos.writeObject(clientRequest2);

                        Action action2= (Action)ois.readObject();
                        if(action2==Action.DETAILS_OF_RECEIVER_CORRECT){
                            ServerResponse response2= (ServerResponse) ois.readObject();
                            view2.balanceLabel.setText(Double.toString(response2.getMoneyInAccount()));
                            showSuccess();
                            // ///////////////////////////////////////////////////////////////////////////////
                        }

                        else if(action2==Action.ERROR){
                            showInsufficientFundsError();
                        }
                    } catch (Exception ioException) {
                        ioException.printStackTrace();
                    }


                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showError(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText("Username or password not valid!");
        errorAlert.showAndWait();
    }
    public void showSuccess(){
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setHeaderText("TRANSFER STATUS");
        infoAlert.setContentText("TRANSFER COMPLETE!");
        infoAlert.showAndWait();
    }
    public void showInsufficientFundsError(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Insufficient funds");
        errorAlert.setContentText("Funds too low, please transfer a smaller amount");
        errorAlert.showAndWait();
    }
}
