package Src;

public interface TraderInterface {
    //implementation in TraderInterfaceImpl.java
    void registerCustomer(String username, String password);
    boolean login(String username, String password);
    void deposit(double amount);
    void withdrawal(double amount);
    void buy(String stockSymbol, int quantity);
    void sell(String stockSymbol, int quantity);
    void cancel(String transactionId);
    double getMarketAccountBalance();
    void showTransactionHistory();
    double getCurrentStockPrice(String stockSymbol);
    void showActorProfile(String actorName);
    void showMovieInformation(String movieTitle);
    void listTopMovies(int startYear, int endYear);
    void displayMovieReviews(String movieTitle);
}
