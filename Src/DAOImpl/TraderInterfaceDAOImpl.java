package Src.DAOImpl;
import Src.*;
import Src.DAO.*;

import java.util.ArrayList;
import java.util.Scanner;

public class TraderInterfaceDAOImpl implements TraderInterfaceDAO {
    //implement functions in TraderInterfaceDAO
    public void registerCustomer(String username, Scanner scanner){
        // TODO Create a new customer in the database
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
        Customer customer = new Customer(name, state, phoneNum, email, taxID, password, username);
        CustomerDAO customerDAO = new CustomerDAOImpl();
        
        boolean cust = customerDAO.createCustomer(customer);
        if(cust){
            System.out.println("Customer created successfully!");
        }
        else{
            System.out.println("Customer creation failed.");
            return;
        }

        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();

        System.out.println("Now creating your market account...");
        System.out.println("Please enter a unique market account id: ");
        int mkta_id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter your initial balance (minimum $1000): ");
        float balance = scanner.nextFloat();
        scanner.nextLine();
        while(balance < 1000.0) {
            System.out.println("Initial balance must be at least $1000. Please enter a new balance: ");
            balance = scanner.nextFloat();
            scanner.nextLine();
        }
        MarketAccount marketAccount = new MarketAccount(username, mkta_id, balance);
        boolean mktaSuccess = marketAccountDAO.createMarketAccount(marketAccount);
        if(mktaSuccess){
            System.out.println("Market account created successfully!");
        }
        else{
            System.out.println("Market account creation failed.");
        }

    };

    public void login(String username, String password) {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        String customerName = customerDAO.login(username, password);
        if(customerName == null){
            System.out.println("Login failed.");
            return;
        }
        else{
            System.out.println("Login successful.");
        }
        System.out.println("Welcome " + customerName + "!");
    }

    public void deposit(float amount, String username){
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        MarketAccountTransactionDAO marketAccountTransactionDDO = new MarketAccountTransactionDAOImpl();
        marketAccountDAO.updateBalance(username, amount);
        int mkta_id = marketAccountDAO.getMarketAccountId(username);
        MarketAccountTransaction transaction = new MarketAccountTransaction(mkta_id, amount, "deposit");
        marketAccountTransactionDDO.createMarketAccountTransaction(transaction);
    }

    public void withdrawal(float amount, String username){
        // TODO Change how date is set
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        MarketAccountTransactionDAO marketAccountTransactionDDO = new MarketAccountTransactionDAOImpl();
        marketAccountDAO.updateBalance(username, -amount);
        int mkta_id = marketAccountDAO.getMarketAccountId(username);
        MarketAccountTransaction transaction = new MarketAccountTransaction(mkta_id, amount, "withdrawal");
        marketAccountTransactionDDO.createMarketAccountTransaction(transaction);
    }

    public void buy(String stockSymbol, float quantity, String username){
        
        StockAccountDAO stockAccountDAO = new StockAccountDAOImpl();
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        ActorProfileStockDAO actorProfileStockDAO = new ActorProfileStockDAOImpl();
        MarketAccountTransactionDAO marketAccountTransactionDDO = new MarketAccountTransactionDAOImpl();
        StockAccountTransactionDAO stockAccountTransactionDAO = new StockAccountTransactionDAOImpl();

        int mkta_id = marketAccountDAO.getMarketAccountId(username);

        if(!stockAccountDAO.stockAccountExists(stockSymbol, mkta_id)) {
            stockAccountDAO.createStockAccount(stockSymbol, mkta_id);
        }

        float currentPrice = actorProfileStockDAO.getActorProfileStock(stockSymbol).getCurrentPrice();
        float totalCost = currentPrice * quantity;
        if(marketAccountDAO.getBalance(username) < totalCost+20) {
            System.out.println("Insufficient funds.");
            return;
        }

        marketAccountDAO.updateBalance(username, -totalCost-20);
        stockAccountDAO.updateShares(stockSymbol, mkta_id, quantity);
        MarketAccountTransaction marketAccountTransaction = new MarketAccountTransaction(mkta_id, totalCost, "buy");
        marketAccountTransactionDDO.createMarketAccountTransaction(marketAccountTransaction);
        MarketAccountTransaction commission = new MarketAccountTransaction(mkta_id, 20, "commission");
        marketAccountTransactionDDO.createMarketAccountTransaction(commission);
        StockAccountTransaction stockAccountTransaction = new StockAccountTransaction(stockSymbol, mkta_id, quantity, "buy", 0);
        stockAccountTransactionDAO.createStockAccountTransaction(stockAccountTransaction);
    }

    public void sell(String stockSymbol, float quantity, String username, float purchasePrice){

        StockAccountDAO stockAccountDAO = new StockAccountDAOImpl();
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        ActorProfileStockDAO actorProfileStockDAO = new ActorProfileStockDAOImpl();
        MarketAccountTransactionDAO marketAccountTransactionDDO = new MarketAccountTransactionDAOImpl();
        StockAccountTransactionDAO stockAccountTransactionDAO = new StockAccountTransactionDAOImpl();

        int mkta_id = marketAccountDAO.getMarketAccountId(username);

        float currentPrice = actorProfileStockDAO.getActorProfileStock(stockSymbol).getCurrentPrice();
        float totalAmount = currentPrice * quantity;
        if(stockAccountDAO.getShares(stockSymbol, mkta_id) < quantity) {
            System.out.println("Insufficient funds.");
            return;
        }

        marketAccountDAO.updateBalance(username, totalAmount -20);
        stockAccountDAO.updateShares(stockSymbol, mkta_id, -quantity);
        MarketAccountTransaction marketAccountTransaction = new MarketAccountTransaction(mkta_id, totalAmount, "sell");
        marketAccountTransactionDDO.createMarketAccountTransaction(marketAccountTransaction);
        MarketAccountTransaction commission = new MarketAccountTransaction(mkta_id, 20, "commission");
        marketAccountTransactionDDO.createMarketAccountTransaction(commission);
        StockAccountTransaction stockAccountTransaction = new StockAccountTransaction(stockSymbol, mkta_id, quantity, "sell", totalAmount -purchasePrice);
        stockAccountTransactionDAO.createStockAccountTransaction(stockAccountTransaction);
    }

    @Override
    public void cancelMarketTransaction(String username) {
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        MarketAccountTransactionDAO marketAccountTransactionDDO = new MarketAccountTransactionDAOImpl();
        int mkta_id = marketAccountDAO.getMarketAccountId(username);

        MarketAccountTransaction deletedTransaction = marketAccountTransactionDDO.cancelMarketAccountTransaction(mkta_id);
        if(deletedTransaction == null){
            System.out.println("No transactions to cancel.");
            return;
        }
        else {
            System.out.println("Transaction cancelled.");
        }

        if(deletedTransaction.getType().equals("deposit") || deletedTransaction.getType().equals("sell")){
            marketAccountDAO.updateBalance(username, -deletedTransaction.getAmount());
        }
        else if(deletedTransaction.getType().equals("withdrawal") || deletedTransaction.getType().equals("buy")){
            marketAccountDAO.updateBalance(username, deletedTransaction.getAmount());
        }
        MarketAccountTransaction commission = new MarketAccountTransaction(mkta_id, 20, "commission");
        marketAccountTransactionDDO.createMarketAccountTransaction(commission);
        marketAccountDAO.updateBalance(username, -20);
    }

    @Override
    public void cancelStockTransaction(String username) {
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        StockAccountDAO stockAccountDAO = new StockAccountDAOImpl();
        StockAccountTransactionDAO stockAccountTransactionDAO = new StockAccountTransactionDAOImpl();

        int mkta_id = marketAccountDAO.getMarketAccountId(username);
        StockAccountTransaction deletedTransaction = stockAccountTransactionDAO.cancelStockAccountTransaction(mkta_id);
        if(deletedTransaction == null){
            System.out.println("No transactions to cancel.");
            return;
        }
        else{
            System.out.println("Transaction cancelled.");
        }

        String stock = deletedTransaction.getStock();
        if(deletedTransaction.getType().equals("buy")){
            stockAccountDAO.updateShares(stock, mkta_id, -deletedTransaction.getShares());
        }
        else if(deletedTransaction.getType().equals("sell")){
            stockAccountDAO.updateShares(stock, mkta_id, deletedTransaction.getShares());
        }
        cancelMarketTransaction(username);
    }

    public void showMarketAccountBalance(String username){
        // TODO Return the current balance of the market account for the customer
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        //get market account by customer username with new function
        float marketAccountBalance = marketAccountDAO.getBalance(username);
        System.out.println("Your market account balance is: " + marketAccountBalance);
    }

    public void showTransactionHistory(String username){
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        int mkta_id = marketAccountDAO.getMarketAccountId(username);
        StockAccountTransactionDAO stockAccountTransactionDAO = new StockAccountTransactionDAOImpl();
        ArrayList<StockAccountTransaction> stockAccountTransactions = stockAccountTransactionDAO.getStockAccountTransactions(mkta_id);
        for(StockAccountTransaction stockAccountTransaction : stockAccountTransactions){
            System.out.println(stockAccountTransaction);
        }
    }

    public void getCurrentStockPrice(String stockSymbol){
        ActorProfileStockDAO actorProfileStockDAO = new ActorProfileStockDAOImpl();
        ActorProfileStock actorProfileStock = actorProfileStockDAO.getActorProfileStock(stockSymbol);
        if(actorProfileStock == null){
            System.out.println("Stock not found.");
        }
        else{
            System.out.println("The current price of " + stockSymbol + " is: " + actorProfileStock.getCurrentPrice());
        }

    }

    public void showActorProfile(String stockSymbol){
        //shows current price of their stock and their name, dob, and stock symbol
        ActorProfileStockDAO actorProfileStockDAO = new ActorProfileStockDAOImpl();
        ActorProfileStock actorProfileStock = actorProfileStockDAO.getActorProfileStock(stockSymbol);
        if(actorProfileStock == null){
            System.out.println("Actor not found.");
        }
        else{
            System.out.println(actorProfileStock);
        }
        
    }

    public void showMovieInformation(String movieTitle, int year, Scanner scanner){
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
        
        System.out.println("Would you like to see the top movies in a given time interval? (y/n)");
        
        String choice = scanner.nextLine();
        //scanner.nextLine();
        if(choice.equals("y")){
            System.out.println("What year range would you like to see the top movies of?");
            System.out.println("Start year: ");
            int startYear = scanner.nextInt();
            scanner.nextLine();
            System.out.println("End year: ");
            int endYear = scanner.nextInt();
            scanner.nextLine();
            ArrayList<Movie> movies = movieDAO.getTopMoviesInTimeInterval(startYear, endYear);
            for(Movie m : movies) {
                System.out.println(m.getTitle());
            }
            if(movies.size() == 0){
                System.out.println("No movies found in that time interval.");
            }
        }
        if(choice.equals("n")){
            //do nothing
        }
        System.out.println("Would you like to see the reviews for this movie? (y/n)");
        String choice2 = scanner.nextLine();
        if(choice2.equals("y")){
            ArrayList<String> reviews = movieDAO.getAllMovieReviews(movie);
            for(String review : reviews) {
                System.out.println(review);
            }
            if(reviews.isEmpty()){
                System.out.println("No reviews found for this movie.");
            }
        }

        

    }

    public static void main(String[] args) {
        // TODO Prompt user to make choices and call the appropriate functions
        TraderInterfaceDAO traderInterfaceDAO = new TraderInterfaceDAOImpl();
        Scanner scanner = new Scanner(System.in);
        String username = ""; //used to access the customer that we want
        System.out.println("Welcome to the Trader Interface!");
        System.out.println("If you are a new user, please register.");
        System.out.println("Type 1 to register, or 2 to login.");
        int newUser = scanner.nextInt();
        scanner.nextLine();
        while(newUser != 1 && newUser != 2){
            System.out.println("Invalid choice. Please try again.");
            System.out.println("Type 1 to register, or 2 to login.");
            newUser = scanner.nextInt();
            scanner.nextLine();
        }
        if(newUser ==1){
            //register a new user
            //log them in too
            //prompts in registerCustomer()
            System.out.println("Please create your username: ");
            username = scanner.nextLine();
            traderInterfaceDAO.registerCustomer(username, scanner);
            
        }
        else if(newUser == 2){
            System.out.println("Please enter your username: ");
            username = scanner.nextLine();
            System.out.println("Please enter your password: ");
            String password = scanner.nextLine();
            traderInterfaceDAO.login(username, password);
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
        System.out.println("11.Exit");
        int choice = scanner.nextInt();
        scanner.nextLine();
        //scanner2.nextLine();
        
        
        

        while(choice != 11){
            switch(choice){
                case 1:
                    System.out.println("How much would you like to deposit?");
                    float amount = scanner.nextFloat();
                    traderInterfaceDAO.deposit(amount, username);
                    break;
                case 2:
                    System.out.println("How much would you like to withdraw?");
                    float amount2 = scanner.nextFloat();
                    traderInterfaceDAO.withdrawal(amount2, username);
                    break;
                case 3:
                    System.out.println("What stock would you like to buy?");
                    String stockSymbol = scanner.nextLine();
                    System.out.println("How many shares would you like to buy?");
                    float quantity = scanner.nextFloat();
                    scanner.nextLine();
                    traderInterfaceDAO.buy(stockSymbol, quantity, username);
                    break;
                case 4:
                    System.out.println("What stock would you like to sell?");
                    String stockSymbol2 = scanner.nextLine();
                    System.out.println("How many shares would you like to sell?");
                    float quantity2 = scanner.nextFloat();
                    scanner.nextLine();
                    System.out.println("What was the purchase price of the stock?");
                    float purchasePrice = scanner.nextFloat();
                    scanner.nextLine();
                    traderInterfaceDAO.sell(stockSymbol2, quantity2, username, purchasePrice);
        
                    break;
                case 5:

                    System.out.println("Would you like to cancel a stock transaction or a market transaction?");
                    System.out.println("Type 1 for stock transaction, or 2 for market transaction.");
                    int cancelChoice = scanner.nextInt();
                    if(cancelChoice == 1){
                        traderInterfaceDAO.cancelStockTransaction(username);
                    }
                    else if(cancelChoice == 2){
                        traderInterfaceDAO.cancelMarketTransaction(username);
                    }
                    else{
                        System.out.println("Invalid choice. Please try again.");
                        break;
                    }
                
                    break;
                case 6:
                    traderInterfaceDAO.showMarketAccountBalance(username);
                    break;
                case 7:
                    traderInterfaceDAO.showTransactionHistory(username);
                    break;
                case 8:
                    System.out.println("What stock would you like to get the current price of?");
                    String stockSymbol3 = scanner.nextLine();
                    traderInterfaceDAO.getCurrentStockPrice(stockSymbol3);
                    break;
                case 9:
                    System.out.println("What actor would you like to see the profile of? (Enter stock symbol)");
                    String actorName = scanner.nextLine();
                    traderInterfaceDAO.showActorProfile(actorName);
                    break;
                case 10:
                    System.out.println("What movie would you like to see information about?");
                    String movieTitle = scanner.nextLine();
                    System.out.println("What year was the movie released?");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    traderInterfaceDAO.showMovieInformation(movieTitle, year, scanner);
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
            System.out.println("11.Exit");
            choice = scanner.nextInt();
            scanner.nextLine();

        }
        System.out.println("Thank you for using the Trader Interface!");
        scanner.close();

        
    }

}
