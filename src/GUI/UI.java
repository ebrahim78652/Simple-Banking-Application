package GUI;

import ClientServer.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application {

    private View view;

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception{
        System.out.println("Starting the app!");
        System.out.println(Thread.currentThread().getName());
        Client client= new Client();
        view= new View( client);


        Scene scene = new Scene(view);


        stage.setTitle("Banking Application");
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(500);
        stage.show();

    }

}
