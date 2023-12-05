package Src.DAOImpl;

import java.util.Scanner;

import Src.DAO.TraderInterfaceDAO;

public class TraderInteraceDAOImpl implements TraderInterfaceDAO {
    //implement functions in TraderInterfaceDAO
    public void registerCustomer(String username, String password){
        // TODO Create a new customer in the database
    };

    public boolean login(String username, String password){
        // TODO Check if the username and password match
        return false;
    }

    public void deposit(double amount){
        // TODO Deposit the given amount into the market account
    }

    public void withdrawal(double amount){
        // TODO Withdraw the given amount from the market account
    }

    public void buy(String stockSymbol, int quantity){
        // TODO Buy the given quantity of the given stock
    }

    public void sell(String stockSymbol, int quantity){
        // TODO Sell the given quantity of the given stock
    }

    public void cancel(String transactionId){
        // TODO Cancel the given transaction
    }

    public double showMarketAccountBalance(){
        // TODO Return the current balance of the market account
        return 0.0;
    }

    public void showTransactionHistory(){
        
    }

    public double getCurrentStockPrice(String stockSymbol){
        // TODO Return the current price of the given stock
        return 0.0;
    }

    public void showActorProfile(String actorName){
        // TODO Show the profile of the given actor
    }

    public void showMovieInformation(String movieTitle){
        // TODO Show information about the given movie
    }

    public void listTopMovies(int startYear, int endYear){
        // TODO List the top movies within the given year range
    }

    public void displayMovieReviews(String movieTitle){
        // TODO Display reviews for the given movie
    }

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
        scanner.close();
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
