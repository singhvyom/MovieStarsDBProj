package Src.DAOImpl;

public class ManagerInterfaceDAOImpl implements ManagerInterfaceDAO {
    // functions for the manager interface
    void addInterest();
    void generateMonthlyStatement();
    void listActiveCustomers();
    void generateDTER();
    void customerReport();
    void deleteTransactions();


    public static void main(String[] args) {
        // prompt manager for options and call the appropriate function
        System.out.println("Welcome to the Manager Interface!");
        System.out.println("What would you like to do?");
        System.out.println("1. Add Interest");
        System.out.println("2. Generate Monthly Statement");
        System.out.println("3. List Active Customers");
        System.out.println("4. Generate DTER");
        System.out.println("5. Customer Report");
        System.out.println("6. Delete Transactions");
    }
    
}
