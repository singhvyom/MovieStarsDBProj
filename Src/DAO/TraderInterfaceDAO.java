package Src.DAO;
import java.util.Scanner;

public interface TraderInterfaceDAO {
    //implementation in TraderInterfaceImpl.java
    public void registerCustomer(String username, Scanner scanner);
    public void login(String username, String password);
    public void deposit(float amount, String username);
    public void withdrawal(float amount, String username);
    public void buy(String stockSymbol, float quantity, String username);
    public void sell(String stockSymbol, float quantity, String username, float purchasePrice);
    public void cancelMarketTransaction(String username);
    public void cancelStockTransaction(String username);
    public void showMarketAccountBalance(String username);
    public void showTransactionHistory(String username);
    public void getCurrentStockPrice(String stockSymbol);
    public void showActorProfile(String stockSymbol);
    public void showMovieInformation(String movieTitle, int year, Scanner scanner);
    
    
}
