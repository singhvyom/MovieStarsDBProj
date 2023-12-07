package Src.DAO;

public interface SysInfoDAO {
    boolean setDate(String date);
    boolean openMarket(String newDate);
    boolean closeMarket();
    //either function to get the current month/date 
    //or we do impl in here but better done in manager interface
}
