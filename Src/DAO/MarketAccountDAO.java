package Src.DAO;

import Src.MarketAccount;

public interface MarketAccountDAO {
    boolean createMarketAccount(MarketAccount marketAccount);
    boolean updateBalance(String username, float amount);
    int getMarketAccountId(String username);
    boolean accrueInterest(String username);
    float getBalance(String username);
}
