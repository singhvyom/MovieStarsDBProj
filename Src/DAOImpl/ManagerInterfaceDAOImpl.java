package Src.DAOImpl;

public class ManagerInterfaceDAOImpl implements ManagerInterfaceDAO {
    // functions for the manager interface
     
    public void addInterest() {
        // TODO: For all market accounts, add the appropriate amount of monthly interest to the balance.
        // This can only be done at the last business day of a month.
        // 3

    }
    
    public void generateMonthlyStatement() {
        // TODO: Given a customer, do the following for each account she/he owns: generate a list of all transactions
        // that have occurred in the current month. This statement should list the name and email address of the
        // customer.
        // The initial and final account balance is to be included, so are the total earning/loss (including interest)
        // this month and the total amount of commissions paid.
        // The statement will be displayed in your interface.
    }
    
    public void listActiveCustomers(){
        //TODO: Generate a list of all customers who have traded (buy or sell) at least 1,000 shares in the current month
    }
    
    public void customerReport(){
        //TODO:Generate a list of all accounts associated with a particular customer and the current balance
    }
    
    public void deleteTransactions(){
        //TODO: Delete the list of transactions from each of the accounts, in preparation for a new month of processing.
        // (Actually they will be archived, but we will not worry about them in this project.)
        System.out.println("All transactions have been deleted.");
    }

    public void generateDTER(){
        //TODO: According to the law, each customer who earns more than $10,000 within one month must be reported
        // to the government. Generate a list of all customers who have made more than $10,000 in the last month,
        // including earnings from buying/selling stocks and interest. The residence state of each customer should
        // also be listed
    }

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
    
