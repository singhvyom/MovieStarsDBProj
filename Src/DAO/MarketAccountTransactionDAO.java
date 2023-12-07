package Src.DAO;

import Src.MarketAccountTransaction;

public interface MarketAccountTransactionDAO {
    boolean createMarketAccountTransaction(MarketAccountTransaction marketAccountTransaction);
    MarketAccountTransaction cancelMarketAccountTransaction(int mkta_id);
    boolean clearAllMarketAccountTransactions();
}
