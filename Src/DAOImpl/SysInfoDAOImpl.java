package Src.DAOImpl;

import Src.DAO.SysInfoDAO;
import Src.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
    public boolean openMarket() {
        return false;
    }

    @Override
    public boolean closeMarket() {
        return false;
    }
}