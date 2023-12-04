package Src.DAOImpl;

import Src.Customer;
import Src.DAO.CustomerDAO;
import Src.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CustomerDAOImpl implements CustomerDAO {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAOImpl();
//        Customer customer = new Customer("Alfred Hitchcock","CA","(805)2574499","a1lfred@hotmail.com","000001022","hi","alfred1");
//        customerDAO.createCustomer(customer);
        customerDAO.login("alfred", "hi");
    }
    
    @Override
    public boolean createCustomer(Customer customer) {
        String query = "INSERT INTO Customer(name, username, passwd, state, phone, email, tax_id) VALUES (?,?,?,?,?,?,?)";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getUsername());
            statement.setString(3, customer.getPassword());
            statement.setString(4, customer.getState());
            statement.setString(5, customer.getPhoneNum());
            statement.setString(6, customer.getEmail());
            statement.setString(7, customer.getTaxID());
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
    public String login(String username, String password) {
//        String query = "SELECT username FROM Customer WHERE username = ? AND passwd = ?";
        String query = "SELECT username FROM Customer WHERE username = '" + username + "' AND passwd = '" + password + "'";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, "alfred");
//            statement.setString(2, "hi");
            ResultSet resultSet = statement.executeQuery();
            String name = null;
            while(resultSet.next()) {
                name = resultSet.getString("username");
            }
            System.out.println(name);
            return name;
        } catch (Exception e) {
            System.out.println("ERROR: login failed.");
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return null;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer getCustomer(String username) {
        return null;
    }
}