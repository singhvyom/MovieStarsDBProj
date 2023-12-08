package Src.DAO;

public interface TraderInterfaceDAO {
    //implementation in TraderInterfaceImpl.java
    public void registerCustomer(String username);
    public void login(String username, String password);
    public void deposit(float amount, String username);
    public void withdrawal(float amount, String username);
    public void buy(String stockSymbol, float quantity, String username);
    public void sell(String stockSymbol, float quantity, String username, float purchasePrice);
    public void cancelMarketTransaction(String username);
    public void cancelStockTransaction(String username);
    public void showMarketAccountBalance(String username);
    public void showTransactionHistory();
    public void getCurrentStockPrice(String stockSymbol);
    public void showActorProfile(String actorName);
    public void showMovieInformation(String movieTitle, int year);
    
    
}
