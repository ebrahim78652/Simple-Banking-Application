package GUI;

import ClientServer.Client;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View2 extends VBox {

    public View view1;
    public Client client;
    public StringProperty receiverOfMoney= new SimpleStringProperty();
    public DoubleProperty amountToSend= new SimpleDoubleProperty();

    public double getAmountToSend() {
        return amountToSend.get();
    }

    public DoubleProperty amountToSendProperty() {
        return amountToSend;
    }

    public void setAmountToSend(double amountToSend) {
        this.amountToSend.set(amountToSend);
    }

    public String getReceiverOfMoney() {
        return receiverOfMoney.get();
    }

    public StringProperty receiverOfMoneyProperty() {
        return receiverOfMoney;
    }

    public void setReceiverOfMoney(String receiverOfMoney) {
        this.receiverOfMoney.set(receiverOfMoney);
    }


    String balance = "Your balance is: *enter balance of the user here* ";
    String transferRequest = "Please enter the IBAN of the user who you want to transfer the money to below:";
    String amountToTransfer = "Please enter the amount you want to transfer";
    String logoutText= "Logout";

    public Label welcomeTextLabel = new Label();
    public Label balanceLabel = new Label(balance);
    public Label transferRequestLabel = new Label(transferRequest);
    public TextField transferRequestTextField = new TextField();
    public Label amountToTransferLabel = new Label(amountToTransfer);
    public TextField amountToTransferTextField= new TextField();
    public Button transferButton= new Button("Transfer Money");
    public Button logoutButton= new Button(logoutText);



    public View2(View view, Client client){
        this.view1= view;
        this.client= client;
        window2();
    }

    private void window2() {

        this.getChildren().addAll(welcomeTextLabel, balanceLabel, transferRequestLabel,
                transferRequestTextField, amountToTransferLabel, amountToTransferTextField, transferButton, logoutButton);


        logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    ((Stage) getScene().getWindow()).setScene(new Scene(new View(client)));
                });


    }
}
