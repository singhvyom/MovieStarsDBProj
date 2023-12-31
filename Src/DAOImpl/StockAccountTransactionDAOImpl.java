package Src.DAOImpl;

import Src.DAO.StockAccountTransactionDAO;
import Src.DbConnection;
import Src.StockAccountTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class StockAccountTransactionDAOImpl implements StockAccountTransactionDAO {

    public static void main(String[] args) {
        StockAccountTransactionDAO stockAccountTransactionDAO = new StockAccountTransactionDAOImpl();
        StockAccountTransaction stockAccountTransaction = new StockAccountTransaction("BCL", 1, 10, "buy", 0);
//        stockAccountTransactionDAO.createStockAccountTransaction(stockAccountTransaction);
        StockAccountTransaction s = stockAccountTransactionDAO.cancelStockAccountTransaction(1);
        System.out.println(s.getShares() + " " + s.getStock());
    }

    @Override
    public boolean createStockAccountTransaction(StockAccountTransaction stockAccountTransaction) {
        String query = "INSERT INTO StockAccountTransaction(stock, mkta_id, shares, type, profit, transaction_date)VALUES (?, ?, ?, ?, ?, (SELECT market_date FROM SysInfo))";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, stockAccountTransaction.getStock());
            statement.setInt(2, stockAccountTransaction.getMkta_id());
            statement.setFloat(3, stockAccountTransaction.getShares());
            statement.setString(4, stockAccountTransaction.getType());
            statement.setFloat(5, stockAccountTransaction.getProfit());
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
    public StockAccountTransaction cancelStockAccountTransaction(int mkta_id) {
        String selectQuery = "SELECT * FROM StockAccountTransaction where transaction_id = (select max(transaction_id) from " +
                "StockAccountTransaction WHERE mkta_id = ? AND transaction_date = (SELECT market_date FROM SysInfo))";
        String deleteQuery = "DELETE FROM StockAccountTransaction where transaction_id = (select max(transaction_id) from " +
                "StockAccountTransaction WHERE mkta_id = ? AND transaction_date = (SELECT market_date FROM SysInfo))";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, mkta_id);
            ResultSet resultSet = selectStatement.executeQuery();
            StockAccountTransaction stockAccountTransaction = new StockAccountTransaction();
            while(resultSet.next()) {
                stockAccountTransaction.setStock(resultSet.getString("stock"));
                stockAccountTransaction.setMkta_id(resultSet.getInt("mkta_id"));
                stockAccountTransaction.setShares(resultSet.getFloat("shares"));
                stockAccountTransaction.setType(resultSet.getString("type"));
            }

            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, mkta_id);
            deleteStatement.executeUpdate();
            return stockAccountTransaction;
        } catch (Exception e) {
            System.out.println("ERROR: cancellation failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
    }

    @Override
    public boolean clearAllTransactions() {
        String query = "DELETE FROM StockAccountTransaction";
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

    @Override
    public ArrayList<StockAccountTransaction> getStockAccountTransactions(int mkta_id) {
        String query = "SELECT * FROM StockAccountTransaction WHERE mkta_id = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, mkta_id);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<StockAccountTransaction> stockAccountTransactions = new ArrayList<>();
            while(resultSet.next()) {
                String stock = resultSet.getString("stock");
                int mkta_id1 = resultSet.getInt("mkta_id");
                float shares = resultSet.getFloat("shares");
                String type = resultSet.getString("type");
                float profit = resultSet.getFloat("profit");
                StockAccountTransaction stockAccountTransaction = new StockAccountTransaction(stock, mkta_id1, shares, type, profit);
                stockAccountTransactions.add(stockAccountTransaction);
            }
            return stockAccountTransactions;
        } catch (Exception e) {
            System.out.println("ERROR: getStockAccountTransactions failed.");
            System.out.println(e);
        }
        return null;
    }

}
