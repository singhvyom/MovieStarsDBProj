package Src.DAO;

import Src.StockAccountTransaction;

public interface StockAccountTransactionDAO {
    boolean createStockAccountTransaction(StockAccountTransaction stockAccountTransaction);
    boolean cancelTransaction(int mkta_id);
    boolean clearAllTransactions();
}
