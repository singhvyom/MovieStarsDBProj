package Src.DAOImpl;
import Src.DAO.CustomerDAO;
import Src.DAOImpl.CustomerDAOImpl;
import Src.DAO.ManagerInterfaceDAO;
import Src.Customer;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import Src.DbConnection;

public class ManagerInterfaceDAOImpl implements ManagerInterfaceDAO{
    // functions for the manager interface
    public boolean login(String username, String password) {
        //TODO: check if the username and password are correct
        // query the db for the username and password
        String query = "SELECT * FROM ADMINISTRATOR WHERE username = " + username + " AND password = " + password;
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }

        }catch(Exception e){
            System.out.println("ERROR: login failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }
    public void addInterest() {
        // TODO: For all market accounts, add the appropriate amount of monthly interest to the balance.
        // This can only be done at the last business day of a month.
        // 
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(currentDay != lastDay){
            System.out.println("ERROR: cannot add interest until the last day of the month.");
            return;
        }

    }
    
    public void generateMonthlyStatement(Customer customer) {
        // TODO: Given a customer, do the following for each account she/he owns: generate a list of all transactions
        // that have occurred in the current month. This statement should list the name and email address of the
        // customer.
        // The initial and final account balance is to be included, so are the total earning/loss (including interest)
        // this month and the total amount of commissions paid.
        // The statement will be displayed in your interface.

    }
    
    public void listActiveCustomers(){
        //TODO: Generate a list of all customers who have traded (buy or sell) at least 1,000 shares in the current month
        //need to sum up the number of shares bought and sold for each customer
        //query the db for all customers who have traded at least 1,000 shares in the current month

        Calendar calendar = Calendar.getInstance(); 
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        String query = "SELECT customer_id, SUM(buy_shares) AS total_buy_shares, SUM(sell_shares) AS total_sell_shares " +
                       "FROM Trades " +
                       "WHERE MONTH(trade_date) = ? AND YEAR(trade_date) = ? " +
                       "GROUP BY customer_id " +
                       "HAVING total_buy_shares + total_sell_shares >= 1000";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, currentMonth);
            statement.setInt(2, currentYear);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                System.out.println("Customer ID: " + resultSet.getString("customer_id"));
                System.out.println("Total Buy Shares: " + resultSet.getString("total_buy_shares"));
                System.out.println("Total Sell Shares: " + resultSet.getString("total_sell_shares"));
                System.out.println("--------------------");
            }
        }catch(Exception e){
            System.out.println("ERROR: listing active customers failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        
    }
    
    public void customerReport(){
        //TODO:Generate a list of all accounts associated with a particular customer and the current balance
    }
    
    public void deleteTransactions(){
        //TODO: Delete the list of transactions from each of the accounts, in preparation for a new month of processing.
        
        System.out.println("All transactions have been deleted.");
    }

    public void generateDTER(){
        //TODO: According to the law, each customer who earns more than $10,000 within one month must be reported
        // to the government. Generate a list of all customers who have made more than $10,000 in the last month,
        // including earnings from buying/selling stocks and interest. The residence state of each customer should
        // also be listed
        // query the db for all customers who have made more than $10,000 in the last month
        // print out the list of customers
    }

    public static void main(String[] args) {
        // prompt manager for options and call the appropriate function
        // first manager needs to login
        ManagerInterfaceDAOImpl manager = new ManagerInterfaceDAOImpl();
        System.out.println("Welcome to the Manager Interface!");
        System.out.println("Please enter your username: ");
        Scanner scanner = new Scanner(System.in);
        while(true){
            String username = scanner.nextLine();   
            System.out.println("Please enter your password: "); 
            String password = scanner.nextLine();   
            if(manager.login(username, password)){
                System.out.println("Login successful!");
                break;
            }
            else{
                System.out.println("Login failed. Try again.");
            }
        }

        Scanner scanner2 = new Scanner(System.in);
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        System.out.println("What would you like to do?");
        System.out.println("1. Add Interest");
        System.out.println("2. Generate Monthly Statement");
        System.out.println("3. List Active Customers");
        System.out.println("4. Generate DTER");
        System.out.println("5. Customer Report");
        System.out.println("6. Delete Transactions");
        System.out.println("7. Exit");
        int choice = scanner2.nextInt();
        while(choice != 7){
            switch(choice){
                case 1:
                    manager.addInterest();
                    break;
                case 2:
                    System.out.println("Enter the customer username: ");
                    String customerUsername = scanner2.nextLine();
                    Customer customer = customerDAO.getCustomer(customerUsername);
                    manager.generateMonthlyStatement(customer);
                    break;
                case 3:
                    manager.listActiveCustomers();
                    break;
                case 4:
                    manager.generateDTER();
                    break;
                case 5:
                    manager.customerReport();
                    break;
                case 6:
                    manager.deleteTransactions();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println("What would you like to do?");
            System.out.println("1. Add Interest");
            System.out.println("2. Generate Monthly Statement");
            System.out.println("3. List Active Customers");
            System.out.println("4. Generate DTER");
            System.out.println("5. Customer Report");
            System.out.println("6. Delete Transactions");
            System.out.println("7. Exit");
            choice = scanner.nextInt();
        }
        //close scanner when 7 is selected
        scanner.close();
        scanner2.close();

    }
}
    
