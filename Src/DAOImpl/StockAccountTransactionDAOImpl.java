package Src.DAOImpl;

import Src.DAO.StockAccountTransactionDAO;
import Src.DbConnection;
import Src.StockAccountTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class StockAccountTransactionDAOImpl implements StockAccountTransactionDAO {

    public static void main(String[] args) {
        StockAccountTransactionDAO stockAccountTransactionDAO = new StockAccountTransactionDAOImpl();
        StockAccountTransaction stockAccountTransaction = new StockAccountTransaction("BCL", 1, 10, "buy", "2020-12-12", 100);
//        stockAccountTransactionDAO.addTransaction(stockAccountTransaction);
//        stockAccountTransactionDAO.cancelTransaction(1);
        stockAccountTransactionDAO.clearAllTransactions();
    }

    @Override
    public boolean createStockAccountTransaction(StockAccountTransaction stockAccountTransaction) {
        String query = "INSERT INTO StockAccountTransaction(stock, mkta_id, shares, type, transaction_date, profit)VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, stockAccountTransaction.getStock());
            statement.setInt(2, stockAccountTransaction.getMkta_id());
            statement.setFloat(3, stockAccountTransaction.getShares());
            statement.setString(4, stockAccountTransaction.getType());
            statement.setDate(5, java.sql.Date.valueOf(stockAccountTransaction.getDate()));
            statement.setFloat(6, stockAccountTransaction.getProfit());
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
    public boolean cancelTransaction(int mkta_id) {
        String query = "DELETE FROM StockAccountTransaction where transaction_id = (select max(transaction_id) from StockAccountTransaction WHERE mkta_id = ?)";
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

}
