package Src.DAO;

import Src.MarketAccountTransaction;

public interface MarketAccountTransactionDAO {
    boolean createMarketAccountTransaction(MarketAccountTransaction marketAccountTransaction);
    boolean cancelMarketAccountTransaction(int mkta_id);
    boolean clearAllMarketAccountTransactions();
}
