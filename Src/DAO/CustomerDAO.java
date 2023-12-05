package Src.DAO;

import Src.Customer;

//import java.sql.Connection;

public interface CustomerDAO {
    boolean createCustomer(Customer customer);
    String login(String username, String password);
    Customer getCustomerByUsername(String username);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(Customer customer);
    Customer getCustomer(String username);
}
