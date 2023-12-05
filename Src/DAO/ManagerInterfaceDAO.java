package Src.DAO;
import Src.Customer;

public interface ManagerInterfaceDAO {
    //functions for the manager interface
    public boolean login(String username, String password);
    public void addInterest();
    public void generateMonthlyStatement(Customer customer);
    public void listActiveCustomers();
    public void generateDTER();
    public void customerReport();
    public void deleteTransactions();
    
}
