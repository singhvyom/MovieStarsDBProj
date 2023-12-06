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
    public void cancel(String transactionId);
    public double showMarketAccountBalance();
    public void showTransactionHistory();
    public double getCurrentStockPrice(String stockSymbol);
    public void showActorProfile(String actorName);
    public void showMovieInformation(String movieTitle);
    //these bottom two functions should be implemented in show movie information i think, 
    //since its available by request
    private void listTopMovies(int startYear, int endYear);
    private void displayMovieReviews(String movieTitle);
    
}
