package GUI;

import ClientServer.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends VBox {


    Text welcomeLabel;
    Label usernameLabel;
    TextField usernameEnteringField;
    Label passwordLabel;
    TextField passwordEnteringField;
    Button logInButton;
    Client client;



    public View( Client client ){
        System.out.println("in the constructor of the View class: ");
        System.out.println(Thread.currentThread().getName());
        this.client= client;
        client.view=this;
        welcomeWindow();
    }


    public void welcomeWindow(){
        System.out.println("in the welcome method of the View class: ");
        System.out.println(Thread.currentThread().getName());

        String welcomeText= "Hello User, please enter your username and password below";


        welcomeLabel= new Text(welcomeText);
        usernameLabel= new Label("Username: ");
        usernameEnteringField= new TextField();
        passwordLabel= new Label ("Password: ");
        passwordEnteringField= new PasswordField();
        logInButton= new Button("Login");
        this.getChildren().addAll(welcomeLabel, usernameLabel,usernameEnteringField, passwordLabel
                ,passwordEnteringField, logInButton);



        logInButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            System.out.println("Mouse Clicked: ");
            System.out.println(Thread.currentThread().getName());
            client.user= usernameEnteringField.getText();
            client.password= passwordEnteringField.getText();
            try {
                client.connectServer();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

    }

    public void jumpToNextWindow(){
        View2 view2= new View2(this, client);
        client.view2=view2;
        ((Stage)getScene().getWindow()).setScene(new Scene(view2));
    }
}


