package Src.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Src.DbConnection;
//MARKET OPEN IS 0, MARKET CLOSED IS 1

public interface SysInfoDAO {
    boolean setDate(String date);
    boolean openMarket(String newDate);
    boolean closeMarket();
    //either function to get the current month/date 
    //or we do impl in here but better done in manager interface
    String getMarketDate();

    
}
