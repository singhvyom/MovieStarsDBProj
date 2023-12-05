package Src.DAOImpl;

import Src.DAO.MarketAccountTransactionDAO;
import Src.DbConnection;
import Src.MarketAccountTransaction;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class MarketAccountTransactionDAOImpl implements MarketAccountTransactionDAO {

    public static void main(String[] args) {
        MarketAccountTransactionDAO marketAccountTransactionDAO = new MarketAccountTransactionDAOImpl();
        MarketAccountTransaction marketAccountTransaction = new MarketAccountTransaction(1, 100, "deposit", "2020-12-12");

//        marketAccountTransactionDAO.createMarketAccountTransaction(marketAccountTransaction);
//        marketAccountTransactionDAO.cancelMarketAccountTransaction(1);
        marketAccountTransactionDAO.clearAllMarketAccountTransactions();
    }

    @Override
    public boolean createMarketAccountTransaction(MarketAccountTransaction marketAccountTransaction) {
        String query = "INSERT INTO MarketAccountTransaction(mkta_id, amount, type, transaction_date)VALUES (?, ?, ?, ?)";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, marketAccountTransaction.getMkta_id());
            statement.setFloat(2, marketAccountTransaction.getAmount());
            statement.setString(3, marketAccountTransaction.getType());
            statement.setDate(4, java.sql.Date.valueOf(marketAccountTransaction.getDate()));
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
    public boolean cancelMarketAccountTransaction(int mkta_id) {
        String query = "DELETE FROM MarketAccountTransaction where transaction_id = (select max(transaction_id) from marketaccounttransaction WHERE mkta_id = ?)";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, mkta_id);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: cancellation failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean clearAllMarketAccountTransactions() {
        String query = "DELETE FROM MarketAccountTransaction";
        try {
            Connection connection = DbConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: deletion failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }
}
