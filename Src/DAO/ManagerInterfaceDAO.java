package Src.DAO;
import Src.Customer;

public interface ManagerInterfaceDAO {
    //functions for the manager interface
    boolean login(String username, String password);
    void addInterest(float interestRate);
    void generateMonthlyStatement(Customer customer);
    void listActiveCustomers();
    void generateDTER();
    void customerReport(Customer customer);
    void deleteTransactions();
    void openMarket();
    void closeMarket();
    void setStockPrice(String stockSymbol, float price);
    void setDate(String date);
}
