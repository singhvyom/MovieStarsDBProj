package Src.DAOImpl;

import Src.DAO.SysInfoDAO;
import Src.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SysInfoDAOImpl implements SysInfoDAO {
    @Override
    public boolean setDate(String date) {
        String query = "UPDATE SysInfo SET market_date = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(date));
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
    public boolean openMarket(String newDate) {
        String query = "UPDATE SysInfo SET is_open = 0 SET market_date = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(newDate));
            statement.executeUpdate();
            System.out.println("Market has been opened.");
            return true;
        }catch(Exception e){
            System.out.println("ERROR: opening market failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean closeMarket() {
        //set all closing prices for each stock to current price
        String query = "INSERT INTO DailyStockPrice(stock_symbol, stock_price_date, closing_price) SELECT " +
                "aps.stock_symbol, si.market_date, aps.current_price FROM actorprofilestock aps, sysinfo si;";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }catch(Exception e){
            System.out.println("ERROR: Updating closing prices failed.");
            e.printStackTrace();
            System.out.println(e);
            return false;
        }

        String query2 = "UPDATE SysInfo SET is_open = 1";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query2);
            statement.executeUpdate();
            System.out.println("Market has been closed.");
            return true;
        }catch(Exception e){
            System.out.println("ERROR: closing market failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }

    @Override
    public String getMarketDate() {
        String date = "";
        String  query = "SELECT market_date FROM SysInfo";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                date = resultSet.getString("market_date");
            }
            return date;
        }catch(Exception e){
            System.out.println("ERROR: getting market date failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return date;
    }
}