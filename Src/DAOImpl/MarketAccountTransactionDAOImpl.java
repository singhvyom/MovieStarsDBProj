package Src.DAOImpl;

import Src.DAO.MarketAccountTransactionDAO;
import Src.DbConnection;
import Src.MarketAccountTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class MarketAccountTransactionDAOImpl implements MarketAccountTransactionDAO {

    public static void main(String[] args) {
        MarketAccountTransactionDAO marketAccountTransactionDAO = new MarketAccountTransactionDAOImpl();
        MarketAccountTransaction marketAccountTransaction = new MarketAccountTransaction(1, 100, "deposit");

//        marketAccountTransactionDAO.createMarketAccountTransaction(marketAccountTransaction);
        MarketAccountTransaction m = marketAccountTransactionDAO.cancelMarketAccountTransaction(1);
        System.out.println(m.getMkta_id());
//        marketAccountTransactionDAO.clearAllMarketAccountTransactions();
    }

    @Override
    public boolean createMarketAccountTransaction(MarketAccountTransaction marketAccountTransaction) {
        String query = "INSERT INTO MarketAccountTransaction(mkta_id, amount, type, transaction_date)VALUES (?, ?, ?, (SELECT market_date FROM SysInfo))";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, marketAccountTransaction.getMkta_id());
            statement.setFloat(2, marketAccountTransaction.getAmount());
            statement.setString(3, marketAccountTransaction.getType());
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
    public MarketAccountTransaction cancelMarketAccountTransaction(int mkta_id) {
        String selectQuery = "SELECT * FROM MarketAccountTransaction where transaction_id = (select max(transaction_id) from " +
                "marketaccounttransaction WHERE mkta_id = ? AND transaction_date = (SELECT market_date FROM SysInfo) AND type <> 'commission')";
        String deleteQuery = "DELETE FROM MarketAccountTransaction where transaction_id = (select max(transaction_id) from " +
                "marketaccounttransaction WHERE mkta_id = ? AND transaction_date = (SELECT market_date FROM SysInfo) AND type <> 'commission')";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, mkta_id);
            ResultSet resultSet = selectStatement.executeQuery();
            MarketAccountTransaction marketAccountTransaction = new MarketAccountTransaction();
            while(resultSet.next()) {
                marketAccountTransaction.setMkta_id(resultSet.getInt("mkta_id"));
                marketAccountTransaction.setAmount(resultSet.getFloat("amount"));
                marketAccountTransaction.setType(resultSet.getString("type"));
            }

            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, mkta_id);
            deleteStatement.executeUpdate();
            return marketAccountTransaction;
        } catch (Exception e) {
            System.out.println("ERROR: cancellation failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
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
