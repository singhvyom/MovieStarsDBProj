package Src.DAOImpl;

import Src.DAO.StockAccountDAO;
import Src.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    @Override
    public boolean stockAccountExists(String stock, int mkta_id) {
        String query = "SELECT * FROM stockaccount WHERE stock = ? AND mkta_id = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, stock);
            statement.setInt(2, mkta_id);
            return statement.executeQuery().next();
        } catch (Exception e) {
            System.out.println("ERROR: stock account check failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }

    @Override
    public float getShares(String stock, int mkta_id) {
        String query = "SELECT shares_owned FROM stockaccount WHERE stock = ? AND mkta_id = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, stock);
            statement.setInt(2, mkta_id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getFloat("shares_owned");
            }

        } catch (Exception e) {
            System.out.println("ERROR: stock account check failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return -1;
    }

    @Override
    public void showAllShares(int mkta_id) {
        String query = "SELECT * FROM StockAccount WHERE mkta_id = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, mkta_id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String stock = resultSet.getString("stock");
                float shares = resultSet.getFloat("shares");
                System.out.println(stock + ": " + shares);
            }
        } catch (Exception e) {
            System.out.println("ERROR: stock account check failed.");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @Override
    public void showEarnings(int mkta_id) {
        //make sure its only for the current month
        //so go get each stock acc transaction within the month
        //then just sum the profits
        //then have to account for difference in shares the customer owns
        //+ interest in marketaccount
        SysInfoDAOImpl sysInfoDAO = new SysInfoDAOImpl();
        String marketDate = sysInfoDAO.getMarketDate();
        
    }
}
