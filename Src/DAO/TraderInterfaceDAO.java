package Src.DAO;
import Src.Customer;
import Src.DAOImpl.CustomerDAOImpl;
public interface TraderInterfaceDAO {
    //implementation in TraderInterfaceImpl.java
    public Customer registerCustomer();
    public void login(CustomerDAO customerDAO);
    public void deposit(double amount);
    public void withdrawal(double amount);
    public void buy(String stockSymbol, int quantity);
    public void sell(String stockSymbol, int quantity);
    public void cancel(int transactionId);
    public void showMarketAccountBalance();
    public void showTransactionHistory();
    public void getCurrentStockPrice(String stockSymbol);
    public void showActorProfile(String actorName);
    public void showMovieInformation(String movieTitle, int year);
    
}
