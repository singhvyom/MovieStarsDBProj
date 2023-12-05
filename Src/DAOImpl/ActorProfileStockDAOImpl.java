package Src.DAOImpl;

import Src.ActorProfileStock;
import Src.DAO.ActorProfileStockDAO;
import Src.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class ActorProfileStockDAOImpl implements ActorProfileStockDAO {

    public static void main(String[] args) {
        ActorProfileStockDAO actorProfileStockDAO = new ActorProfileStockDAOImpl();
        ActorProfileStock actorProfileStock = actorProfileStockDAO.getActorProfileStock("AFH");
        System.out.println(actorProfileStock);
    }

    @Override
    public ActorProfileStock getActorProfileStock(String stockSymbol) {
        String query = "SELECT * FROM ActorProfileStock WHERE stock_symbol = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, stockSymbol);
            ResultSet resultSet = statement.executeQuery();
            ActorProfileStock actorProfileStock = null;
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                float currentPrice = resultSet.getFloat("current_price");
                float closingPrice = resultSet.getFloat("closing_price");
                java.sql.Date dob = resultSet.getDate("dob");
                java.util.Date dobUtil = new java.util.Date(dob.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                String dobString = sdf.format(dobUtil);
                actorProfileStock = new ActorProfileStock(name, stockSymbol, currentPrice, closingPrice, dobString);
            }
            return actorProfileStock;
        } catch (Exception e) {
            System.out.println("ERROR: getActorProfileStock failed.");
            System.out.println(e);
        }
        return null;
    }
}
