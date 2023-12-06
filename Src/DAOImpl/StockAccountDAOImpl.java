package Src.DAOImpl;

import Src.DAO.StockAccountDAO;
import Src.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class StockAccountDAOImpl implements StockAccountDAO {

    public static void main(String[] args) {
        StockAccountDAO stockAccountDAO = new StockAccountDAOImpl();
//        stockAccountDAO.createStockAccount("BCL", 1);
        stockAccountDAO.updateShares("BCL", 1, 10);
    }

    @Override
    public boolean createStockAccount(String stock, int mkta_id) {
        String query = "INSERT INTO stockaccount(stock, mkta_id, shares_owned) VALUES (?,?,0)";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, stock);
            statement.setInt(2, mkta_id);
            statement.setFloat(3, 0);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: insertion failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean updateShares(String stock, int mkta_id, float shares) {
        String query = "UPDATE stockaccount SET shares_owned = shares_owned + ? WHERE stock = ? AND mkta_id = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setFloat(1, shares);
            statement.setString(2, stock);
            statement.setInt(3, mkta_id);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: deposit failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }
}
