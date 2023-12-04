package Src.DAO;

import Src.Customer;

//import java.sql.Connection;

public interface CustomerDAO {
    public boolean createCustomer(Customer customer);
    public String login(String username, String password);
    public Customer getCustomerByUsername(String username);
    public boolean updateCustomer(Customer customer);
    public boolean deleteCustomer(Customer customer);
    public Customer getCustomer(String username);
}
