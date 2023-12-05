package Src.DAO;

import Src.StockAccountTransaction;

public interface StockAccountTransactionDAO {
    boolean addTransaction(StockAccountTransaction stockAccountTransaction);
    boolean cancelTransaction(int mkta_id);
    boolean clearAllTransactions();
}
