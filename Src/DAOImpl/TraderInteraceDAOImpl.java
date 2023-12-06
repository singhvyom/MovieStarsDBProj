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
import Src.DAO.MarketAccountDAO;
import Src.DAOImpl.MarketAccountDAOImpl;
import Src.DAO.MarketAccountTransactionDAO;
import Src.DAOImpl.MarketAccountTransactionDAOImpl;
import Src.DAO.ActorProfileStockDAO;
import Src.DAOImpl.ActorProfileStockDAOImpl;
import Src.ActorProfileStock;
import Src.DAO.MovieDAO;
import Src.DAOImpl.MovieDAOImpl;
import Src.Movie;
import java.util.ArrayList;

public class TraderInteraceDAOImpl implements TraderInterfaceDAO {
    //implement functions in TraderInterfaceDAO
    public Customer registerCustomer(){
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
        scanner.close();
        return customer;
        
    };

    public void login(CustomerDAO customerDAO) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        String customerName = customerDAO.login(username, password);
        if (customerName == null) {
            while (true) {
                System.out.println("Login failed. Please try again.");
                System.out.println("Please enter your username: ");
                username = scanner.nextLine();
                System.out.println("Please enter your password: ");
                password = scanner.nextLine();
                customerName = customerDAO.login(username, password);
                if (customerName != null) {
                    break;
                }
            }
        }
        scanner.close();
        System.out.println("Welcome " + customerName + "!");
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

    public void cancel(int transactionId){
        // TODO Cancel the given transaction
        MarketAccountTransactionDAO marketAccountTransaction = new MarketAccountTransactionDAOImpl();
        boolean cancel = marketAccountTransaction.cancelMarketAccountTransaction(transactionId);
        if(cancel){
            System.out.println("Transaction cancelled.");
        }
        else{
            System.out.println("Transaction not found.");
        }
        
    }

    public void showMarketAccountBalance(){
        // TODO Return the current balance of the market account
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        //need show balance function for the market account?
    }

    public void showTransactionHistory(){
        //would show the entire list of transactions for stock account
        //change this query, can't access transactions for a customer like this
        
        
    }

    public void getCurrentStockPrice(String stockSymbol){
        // TODO Return the current price of the given stock
        ActorProfileStockDAO actorProfileStockDAO = new ActorProfileStockDAOImpl();
        ActorProfileStock actorProfileStock = actorProfileStockDAO.getActorProfileStock(stockSymbol);
        if(actorProfileStock == null){
            System.out.println("Stock not found.");
        }
        else{
            System.out.println("The current price of " + stockSymbol + " is: " + actorProfileStock.getCurrentPrice());
        }

    }

    public void showActorProfile(String actorName){
        // TODO Show the profile of the given actor
        //should this be asking for the stock symbol instead?
        //currently implemented as if it was a stock symbol
        //shows current price of their stock and their name, dob, and stock symbol
        ActorProfileStockDAO actorProfileStockDAO = new ActorProfileStockDAOImpl();
        ActorProfileStock actorProfileStock = actorProfileStockDAO.getActorProfileStock(actorName); 
        if(actorProfileStock == null){
            System.out.println("Actor not found.");
        }
        else{
            actorProfileStock.toString();
        }
        
    }

    public void showMovieInformation(String movieTitle, int year){
        // TODO Show information about the given movie
        ///then ask if they wanna see the top movies and if they want to display movie reviews
        MovieDAO movieDAO = new MovieDAOImpl();
        Movie movie = new Movie(movieTitle, year);
        ArrayList<String> actors = movieDAO.getActorsForMovie(movie);
        ArrayList<String> directors = movieDAO.getDirectorsForMovie(movie);
        float rating = movieDAO.getMovieRating(movie);
        System.out.println(movieTitle);
        System.out.println(year);
        System.out.println("Rating: " + rating);   
        System.out.print("Actors: ");
        for(String actor : actors){
            System.out.print(actor + ", ");
        }
        System.out.println();
        System.out.print("Directors: ");
        for(String director : directors){
            System.out.print(director + ", ");
        }
        System.out.println();
        
        System.out.println("Would you like to see the top movies in this time interval? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if(choice.equals("y")){
            System.out.println("What year range would you like to see the top movies of?");
            System.out.println("Start year: ");
            int startYear = scanner.nextInt();
            System.out.println("End year: ");
            int endYear = scanner.nextInt();
            ArrayList<Movie> movies = movieDAO.getTopMoviesInTimeInterval(startYear, endYear);
            for(Movie m : movies) {
                System.out.println(m.getTitle());
            }
        }
        System.out.println("Would you like to see the reviews for this movie? (y/n)");
        String choice2 = scanner.nextLine();
        if(choice2.equals("y")){
            ArrayList<String> reviews = movieDAO.getAllMovieReviews(movie);
            for(String review : reviews) {
                System.out.println(review);
            }
        }

        scanner.close();


    }


    public void main(String[] args) {
        // TODO Prompt user to make choices and call the appropriate functions
        Scanner scanner = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAOImpl();

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
            Customer customer = registerCustomer();
            customerDAO.createCustomer(customer);

        }
        else if(newUser == 2){
            //login 
            login(customerDAO);
        }
        
        scanner.close();
        //get the customer and relevant info so we can access them anywhere
        //need to get the customer's username, mkta_id
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
        System.out.println("11.Exit");
        int choice = scanner2.nextInt();

        while(choice != 11){
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
                    System.out.println("What transaction would you like to cancel? Enter id: ");
                    int transactionId = scanner2.nextInt();
                    cancel(transactionId);
                    break;
                case 6:
                    showMarketAccountBalance();
                    break;
                case 7:
                    showTransactionHistory();
                    break;
                case 8:
                    System.out.println("What stock would you like to get the current price of?");
                    String stockSymbol3 = scanner2.nextLine();
                    getCurrentStockPrice(stockSymbol3);
                    break;
                case 9:
                    System.out.println("What actor would you like to see the profile of?");
                    String actorName = scanner2.nextLine();
                    showActorProfile(actorName);
                    break;
                case 10:
                    System.out.println("What movie would you like to see information about?");
                    String movieTitle = scanner2.nextLine();
                    System.out.println("What year was the movie released?");
                    int year = scanner2.nextInt();
                    showMovieInformation(movieTitle, year);
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
