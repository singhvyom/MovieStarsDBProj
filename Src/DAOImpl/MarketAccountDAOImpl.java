package Src.DAOImpl;

import Src.Customer;
import Src.DAO.MarketAccountDAO;
import Src.DbConnection;
import Src.MarketAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MarketAccountDAOImpl implements MarketAccountDAO {
    public static void main(String[] args) {
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        MarketAccount marketAccount = new MarketAccount("alfred", 1, 1000);
        // marketAccountDAO.createMarketAccount(marketAccount);
        marketAccountDAO.deposit("alfred", 100);
        marketAccountDAO.withdraw("alfred", 50);
        marketAccountDAO.accrueInterest("alfred");
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
    public boolean deposit(String username, float amount) {
//        String query = "UPDATE MarketAccount SET balance = balance + ? WHERE username = ?";
        String query = "UPDATE MarketAccount SET balance = balance + " + amount + " WHERE username = '" + username + "'";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setFloat(1, amount);
//            statement.setString(2, username);
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
    public boolean withdraw(String username, float amount) {
        String query = "UPDATE MarketAccount SET balance = balance - ? WHERE username = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setFloat(1, amount);
            statement.setString(2, username);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: withdrawal failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }
}
