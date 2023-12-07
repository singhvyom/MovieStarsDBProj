package Src.DAOImpl;

import Src.Customer;
import Src.DAO.MarketAccountDAO;
import Src.DbConnection;
import Src.MarketAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MarketAccountDAOImpl implements MarketAccountDAO {
    public static void main(String[] args) {
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        MarketAccount marketAccount = new MarketAccount("alfred", 1, 1000);
//         marketAccountDAO.createMarketAccount(marketAccount);
        marketAccountDAO.updateBalance("alfred", -200);
//        marketAccountDAO.withdraw("alfred", 50);
//        marketAccountDAO.accrueInterest("alfred");
    }

    @Override
    public boolean createMarketAccount(MarketAccount marketAccount) {
        // TODO Check that initial balance is >= 1000 idk where to do this
        String query = "INSERT INTO MarketAccount(username, mkta_id, balance) VALUES (?,?,?)";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, marketAccount.getUsername());
            statement.setInt(2, marketAccount.getMkta_id());
            statement.setFloat(3, marketAccount.getBalance());
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
    public boolean accrueInterest(String username) {
        // Need to calculate the average daily balance for the past month
        // For now just add 2% to the balance
        String query = "UPDATE MarketAccount SET balance = balance * 1.02 WHERE username = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: interest accrual failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean updateBalance(String username, float amount) {
        String query = "UPDATE MarketAccount SET balance = balance + ? WHERE username = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setFloat(1, amount);
            statement.setString(2, username);
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
    public int getMarketAccountId(String username) {
        String query = "SELECT mkta_id FROM MarketAccount WHERE username = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            int mkta_id = -1;
            while(resultSet.next()) {
                mkta_id = resultSet.getInt("mkta_id");
            }
            return mkta_id;
        } catch (Exception e) {
            System.out.println("ERROR: login failed.");
            System.out.println(e);
        }

        return -1;
    }

    @Override
    public float getBalance(String username) {
        String query = "SELECT balance FROM MarketAccount WHERE username = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            float balance = -1;
            while(resultSet.next()) {
                balance = resultSet.getFloat("balance");
            }
            return balance;
        } catch (Exception e) {
            System.out.println("ERROR: login failed.");
            System.out.println(e);
        }
        return -1;
    }
}
