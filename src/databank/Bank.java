package databank;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import Exceptions.*;


//bank stores the usernames and password
//bank2 stores the usernames and balances

public class Bank {

    private Connection connection2;
    private List<Customer> customersList= new ArrayList<>();

    //now we will get a request from a server to transfer money from one account into another

    public boolean checkUsernameInDatabase(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = connection2.prepareStatement("SELECT*  FROM customers2" +
                " WHERE username= ? AND password = ?  ");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet rs= preparedStatement.executeQuery();
        int count=0;
        while(rs.next()){
            count++;
        }
        if(count<1){
            return false;
        }
        return true;
    }

    public void doTransfer(String from, String to, double amountToTransfer) throws SQLException {
        //first we will deduct that amount of money from the "from" person;
        //for that we will first need to do a subtraction, and for that we will first need the the amount itself
        connection2.setAutoCommit(false);
        double amountInSendersAcc= getBalance(from);
        if(amountInSendersAcc<amountToTransfer){
            throw new InsufficientFundsException();
        }
        double newAmountInSendersAcc= amountInSendersAcc-amountToTransfer;
        setBalance(from, newAmountInSendersAcc );


        double amountInReceiversAccount= getBalance(to);
        double newAmountinReceiverAccount= amountInReceiversAccount+ amountToTransfer;
        setBalance(to, newAmountinReceiverAccount);
        connection2.commit();
        connection2.setAutoCommit(true);
    }

    private void setBalance(String from, double newAmountInSendersAcc) throws SQLException {
        PreparedStatement preparedStatement=
                connection2.prepareStatement("UPDATE customers2 SET balance=? WHERE username=?");
        preparedStatement.setDouble(1, newAmountInSendersAcc);
        preparedStatement.setString(2, from);
        preparedStatement.executeUpdate();
    }

    public double getBalance(String from) throws SQLException {
        PreparedStatement preparedstatement=
                connection2.prepareStatement("SELECT (balance) FROM customers2 WHERE username= ?");
        preparedstatement.setString(1, from);
        ResultSet rs= preparedstatement.executeQuery();
        double amountInSenderAccount=0;
        while(rs.next()){
            amountInSenderAccount= rs.getDouble("balance");
        }
        return amountInSenderAccount;
    }

    public void insertInitialDataUsingStatements() throws SQLException {
        //planning to enter 2 users for now, and test the interactions between them
        Customer customer1 = new Customer("user1", "pass1", 1000);
        Customer customer2 = new Customer("user2", "pass2", 2000);
        this.addCustomer(customer1);
        this.addCustomer(customer2);

        Statement statement2= connection2.createStatement();
        for(Customer c: customersList){
            StringBuilder bldr= new StringBuilder();

            bldr.append("INSERT INTO customers2 (username, balance, password) VALUES ('");
            bldr.append(c.getUsername()).append("',");
            bldr.append(c.getBalance()).append(",'");
            bldr.append(c.getPassword()).append("')");
            statement2.executeUpdate(bldr.toString());
        }
    }

    public void printTable() throws SQLException {

        Statement statement2= connection2.createStatement();
        StringBuilder bldr= new StringBuilder();
        bldr.append("SELECT* from customers1");

        ResultSet rs2= statement2.executeQuery("SELECT* from customers2");

        while(rs2.next()){
            String username= rs2.getString("username");

            double balance= rs2.getDouble("balance");
            System.out.println(username+ " "+ " "+ balance + " "+ rs2.getString("password"));
        }
    }

    public void initTable() throws SQLException {

        Statement statement2= connection2.createStatement();
        statement2.executeUpdate("DROP TABLE IF EXISTS customers2");
        statement2.executeUpdate("CREATE TABLE customers2 (username STRING PRIMARY KEY" +
                ", balance MONEY, password STRING)");
    }

    public void init() throws SQLException {

        connection2= DriverManager.getConnection("jdbc:sqlite:bank2.db");
    }

    public void addCustomer(String username, String password, double balance ){
        customersList.add(new Customer(username, password, balance));
    }

    public void addCustomer(Customer customer){
        customersList.add(customer);
    }
}
