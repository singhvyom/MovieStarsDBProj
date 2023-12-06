package Src.DAO;

public interface StockAccountDAO {
    boolean createStockAccount(String stock, int mkta_id);
    boolean updateShares(String stock, int mkta_id, float shares);
}
