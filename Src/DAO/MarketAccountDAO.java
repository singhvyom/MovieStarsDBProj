package Src.DAO;

import Src.MarketAccount;

public interface MarketAccountDAO {
    public boolean createMarketAccount(MarketAccount marketAccount);
    public boolean deposit(String username, float amount);
    public boolean withdraw(String username, float amount);
    public boolean accrueInterest(String username);

}
