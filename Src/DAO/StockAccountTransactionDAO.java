package Src.DAO;

import Src.StockAccountTransaction;

import java.util.ArrayList;

public interface StockAccountTransactionDAO {
    boolean createStockAccountTransaction(StockAccountTransaction stockAccountTransaction);
    StockAccountTransaction cancelStockAccountTransaction(int mkta_id);
    boolean clearAllTransactions();
    ArrayList<StockAccountTransaction> getStockAccountTransactions(int mkta_id);
}
