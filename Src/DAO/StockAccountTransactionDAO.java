package Src.DAO;

import Src.StockAccountTransaction;

public interface StockAccountTransactionDAO {
    boolean createStockAccountTransaction(StockAccountTransaction stockAccountTransaction);
    StockAccountTransaction cancelStockAccountTransaction(int mkta_id);
    boolean clearAllTransactions();
}
