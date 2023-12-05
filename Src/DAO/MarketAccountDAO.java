package Src.DAO;

import Src.MarketAccount;

public interface MarketAccountDAO {
    boolean createMarketAccount(MarketAccount marketAccount);
    boolean updateBalance(String username, float amount);
    boolean accrueInterest(String username);

}
