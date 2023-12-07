package Src.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Src.DbConnection;

public interface SysInfoDAO {
    boolean setDate(String date);
    boolean openMarket(String newDate);
    boolean closeMarket();
    //either function to get the current month/date 
    //or we do impl in here but better done in manager interface
    private String getMarketDate() {
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
