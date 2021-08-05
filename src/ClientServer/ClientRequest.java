package ClientServer;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    String username;
    String password;
    String receiver;
    Double amountToSend;

    public ClientRequest(String username, String password){
        this.username= username;
        this.password= password;
    }

    public ClientRequest(String receiver, double amountToSend  ){
        this.receiver= receiver;
        this.amountToSend= amountToSend;
    }

    public String getReceiver() {
        return receiver;
    }

    public Double getAmountToSend() {
        return amountToSend;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
