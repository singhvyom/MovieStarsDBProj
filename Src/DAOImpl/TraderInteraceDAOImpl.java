package Src.DAOImpl;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Src.Customer;
import Src.DAO.CustomerDAO;
import Src.DbConnection;
import Src.DAO.TraderInterfaceDAO;

public class TraderInteraceDAOImpl implements TraderInterfaceDAO {
    //implement functions in TraderInterfaceDAO
    public void registerCustomer(){
        // TODO Create a new customer in the database
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter your state: ");
        String state = scanner.nextLine();
        System.out.println("Please enter your phone number: ");
        String phoneNum = scanner.nextLine();
        System.out.println("Please enter your email: ");
        String email = scanner.nextLine();
        //auto generate a taxid?
        System.out.println("Please enter your tax ID: ");
        String taxID = scanner.nextLine();
        System.out.println("Please create your password: ");
        String password = scanner.nextLine();
        System.out.println("Please create your username: ");
        String username = scanner.nextLine();
        Customer customer = new Customer(name, state, phoneNum, email, taxID, password, username);
        CustomerDAO customerDAO = new CustomerDAOImpl();
        
        boolean cust = customerDAO.createCustomer(customer);
        if(cust){
            System.out.println("Customer created successfully!");
        }
        else{
            System.out.println("Customer creation failed.");
        }
        scanner.close();
    };

    public boolean login(String username, String password){
        // TODO Check if the username and password match any of them in the database
        //if they do, return true
        String  query = "SELECT * FROM Customer WHERE username = ? AND password = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return false;
    }

    public void deposit(double amount){
        // TODO Deposit the given amount into the market account
        String query = "UPDATE MarketAccount SET balance = balance + ? WHERE username = ?";
    }

    public void withdrawal(double amount){
        // TODO Withdraw the given amount from the market account
        String query = "UPDATE MarketAccount SET balance = balance - ? WHERE username = ?";
    }

    public void buy(String stockSymbol, int quantity){
        // TODO Buy the given quantity of the given stock
        //if first time buying stock need to create a stock account
    }

    public void sell(String stockSymbol, int quantity){
        // TODO Sell the given quantity of the given stock
    }

    public void cancel(String transactionId){
        // TODO Cancel the given transaction
        String query = "DELETE FROM Transaction WHERE transactionId = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, transactionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("Transaction cancelled successfully!");
            }
            else{
                System.out.println("Transaction cancellation failed.");
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public double showMarketAccountBalance(){
        // TODO Return the current balance of the market account
        String query = "SELECT balance FROM MarketAccount WHERE username = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "username");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble("balance");
            }
            else{
                System.out.println("Market account not found.");
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return 0.0;
    }

    public void showTransactionHistory(){
        //would show the entire list of transactions for stock account
        //change this query, can't access transactions for a customer like this
        String query = "SELECT * FROM StockAccountTransaction WHERE username = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "username");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("Transaction ID: " + resultSet.getString("transactionId"));
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Stock Symbol: " + resultSet.getString("stock_symbol"));
                System.out.println("Quantity: " + resultSet.getInt("quantity"));
                System.out.println("Price: " + resultSet.getDouble("price"));
                System.out.println("Type: " + resultSet.getString("type"));
                System.out.println("Timestamp: " + resultSet.getTimestamp("timestamp"));
            }
            else{
                System.out.println("Transaction history not found.");
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
    }

    public double getCurrentStockPrice(String stockSymbol){
        // TODO Return the current price of the given stock
        String query = "SELECT price FROM Stock WHERE stockSymbol = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, stockSymbol);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble("price");
            }
            else{
                System.out.println("Stock not found.");
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return 0.0;
    }

    public void showActorProfile(String actorName){
        // TODO Show the profile of the given actor
        //shows current price of their stock and their name, dob, and stock symbol
        String query = "SELECT * FROM Actor WHERE name = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, actorName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("DOB: " + resultSet.getDate("dob"));
                System.out.println("Stock Symbol: " + resultSet.getString("stock_symbol"));
                System.out.println("Current Stock Price: " + resultSet.getFloat("current_price"));
            }
            else{
                System.out.println("Actor not found.");
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public void showMovieInformation(String movieTitle){
        // TODO Show information about the given movie
        ///then ask if they wanna see the top movies and if they want to display movie reviews
        String query = "SELECT * FROM Movie WHERE title = ?";
        //if we want to display other info like the actors?
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, movieTitle);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("Title: " + resultSet.getString("title"));
                System.out.println("Year: " + resultSet.getInt("year"));
            }
            else{
                System.out.println("Movie not found.");
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }

        System.out.println("Would you like to see the top movies?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if(choice == 1){
            //display the top movies
        }
        else if(choice == 2){
            //do nothing
        }
        System.out.println("Would you like to see movie reviews for a movie?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int choice2 = scanner.nextInt();
        if(choice2 == 1){
            //display movie reviews
        }
        else if(choice2 == 2){
            //do nothing
        }
        scanner.close();
    }

    private void listTopMovies(int startYear, int endYear){
        // TODO List the top movies within the given year range
    }

    private void displayMovieReviews(String movieTitle){
        // TODO Display reviews for the given movie
    }

    public void main(String[] args) {
        // TODO Prompt user to make choices and call the appropriate functions
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Trader Interface!");
        System.out.println("If you are a new user, please register.");
        System.out.println("Type 1 to register, or 2 to login.");
        int newUser = scanner.nextInt();
        while(newUser != 1 && newUser != 2){
            System.out.println("Invalid choice. Please try again.");
            System.out.println("Type 1 to register, or 2 to login.");
            newUser = scanner.nextInt();
        }
        if(newUser ==1){
            //register a new user
            //log them in too
            //prompts in registerCustomer()
            registerCustomer();

        }
        else if(newUser == 2){
            System.out.println("Please enter your username: ");
            String username = scanner.nextLine();   

            while(true){
                System.out.println("Please enter your password: "); 
                String password = scanner.nextLine();   
                if(login(username, password)){
                    System.out.println("Login successful!");
                    break;
                }
                else{
                    System.out.println("Login failed. Try again.");
                }
            }
        }
        
        scanner.close();
        //get the customer so we can access them anywhere
    
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("What would you like to do?");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        System.out.println("3. Buy");
        System.out.println("4. Sell");
        System.out.println("5. Cancel");
        System.out.println("6. Show Market Account Balance");
        System.out.println("7. Show Transaction History");
        System.out.println("8. Get Current Stock Price");
        System.out.println("9. Show Actor Profile");
        System.out.println("10. Show Movie Information");
        System.out.println("11. List Top Movies");
        System.out.println("12.Exit");
        int choice = scanner2.nextInt();

        while(choice != 12){
            switch(choice){
                case 1:
                    System.out.println("How much would you like to deposit?");
                    double amount = scanner2.nextDouble();
                    deposit(amount);
                    break;
                case 2:
                    System.out.println("How much would you like to withdraw?");
                    double amount2 = scanner2.nextDouble();
                    withdrawal(amount2);
                    break;
                case 3:
                    System.out.println("What stock would you like to buy?");
                    String stockSymbol = scanner2.nextLine();
                    System.out.println("How many shares would you like to buy?");
                    int quantity = scanner2.nextInt();
                    buy(stockSymbol, quantity);
                    break;
                case 4:
                    System.out.println("What stock would you like to sell?");
                    String stockSymbol2 = scanner2.nextLine();
                    System.out.println("How many shares would you like to sell?");
                    int quantity2 = scanner2.nextInt();
                    sell(stockSymbol2, quantity2);
                    break;
                case 5:
                    System.out.println("What transaction would you like to cancel?");
                    String transactionId = scanner2.nextLine();
                    cancel(transactionId);
                    break;
                case 6:
                    System.out.println("Your current market account balance is: " + showMarketAccountBalance());
                    break;
                case 7:
                    showTransactionHistory();
                    break;
                case 8:
                    System.out.println("What stock would you like to get the current price of?");
                    String stockSymbol3 = scanner2.nextLine();
                    System.out.println("The current price of " + stockSymbol3 + " is: " + getCurrentStockPrice(stockSymbol3));
                    break;
                case 9:
                    System.out.println("What actor would you like to see the profile of?");
                    String actorName = scanner2.nextLine();
                    showActorProfile(actorName);
                    break;
                case 10:
                    System.out.println("What movie would you like to see information about?");
                    String movieTitle = scanner2.nextLine();
                    showMovieInformation(movieTitle);
                    break;
                case 11:
                    System.out.println("What year range would you like to see the top movies of?");
                    System.out.println("Start year: ");
                    int startYear = scanner2.nextInt();
                    System.out.println("End year: ");
                    int endYear = scanner2.nextInt();
                    listTopMovies(startYear, endYear);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println("What would you like to do?");
            System.out.println("1. Deposit");
            System.out.println("2. Withdrawal");
            System.out.println("3. Buy");
            System.out.println("4. Sell");
            System.out.println("5. Cancel");
            System.out.println("6. Show Market Account Balance");
            System.out.println("7. Show Transaction History");
            System.out.println("8. Get Current Stock Price");
            System.out.println("9. Show Actor Profile");
            System.out.println("10. Show Movie Information");
            System.out.println("11. List Top Movies");
            System.out.println("12.Exit");
            choice = scanner2.nextInt();

        }
        System.out.println("Thank you for using the Trader Interface!");
        scanner2.close();

        
    }

}
