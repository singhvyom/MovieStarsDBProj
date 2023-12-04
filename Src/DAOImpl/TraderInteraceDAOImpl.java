package Src.DAOImpl;

import java.util.Scanner;

import Src.DAO.TraderInterfaceDAO;

public class TraderInteraceDAOImpl implements TraderInterfaceDAO {
    //implement functions in TraderInterfaceDAO
    void registerCustomer(String username, String password){};
    boolean login(String username, String password){}
    void deposit(double amount);
    void withdrawal(double amount);
    void buy(String stockSymbol, int quantity);
    void sell(String stockSymbol, int quantity);
    void cancel(String transactionId);
    double showMarketAccountBalance();
    void showTransactionHistory();
    double getCurrentStockPrice(String stockSymbol);
    void showActorProfile(String actorName);
    void showMovieInformation(String movieTitle);
    void listTopMovies(int startYear, int endYear);
    void displayMovieReviews(String movieTitle);

    public void main(String[] args) {
        // TODO Prompt user to make choices and call the appropriate functions
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Trader Interface!");
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


    }

}
