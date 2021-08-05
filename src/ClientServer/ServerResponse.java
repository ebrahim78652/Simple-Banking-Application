package ClientServer;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    double moneyInAccount;

    public double getMoneyInAccount() {
        return moneyInAccount;
    }

    public void setMoneyInAccount(double moneyInAccount) {
        this.moneyInAccount = moneyInAccount;
    }
}
