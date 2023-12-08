package Src.DAO;

public interface StockAccountDAO {
    boolean createStockAccount(String stock, int mkta_id);
    boolean stockAccountExists(String stock, int mkta_id);
    boolean updateShares(String stock, int mkta_id, float shares);
    float getShares(String stock, int mkta_id);
    void showAllShares(int mkta_id);
    void showEarnings(int mkta_id);
}
