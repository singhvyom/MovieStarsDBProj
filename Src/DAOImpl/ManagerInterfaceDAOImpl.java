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
    public void addInterest(float interestRate) {
        // TODO: For all market accounts, add the appropriate amount of monthly interest to the balance.
        // This can only be done at the last business day of a month.
        // manager gets to set the interest rate
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(currentDay != lastDay){
            System.out.println("ERROR: cannot add interest until the last day of the month.");
            return;
        }
        String query = "UPDATE Market_Accounts SET balance = balance * (1 + ?)";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setFloat(1, interestRate);
            statement.executeUpdate();
            System.out.println("Interest has been added to all market accounts.");
        }catch(Exception e){
            System.out.println("ERROR: adding interest failed.");
            e.printStackTrace();
            System.out.println(e);
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
                System.out.println("Customer ID: " + resultSet.getInt("customer_id"));
                System.out.println("Total Buy Shares: " + resultSet.getString("total_buy_shares"));
                System.out.println("Total Sell Shares: " + resultSet.getString("total_sell_shares"));
                System.out.println("--------------------");
            }
            if(!resultSet.next()){
                System.out.println("No active customers who have bought and sold 1000 shares found.");
            }
        }catch(Exception e){
            System.out.println("ERROR: listing active customers failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        
    }
    
    public void customerReport(Customer customer){
        //TODO:Generate a list of all accounts associated with a particular customer and the current balance
        //query the db for all accounts associated with a particular customer and the current balance
        String query = "SELECT * FROM Accounts WHERE username = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, customer.getUsername());

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                System.out.println("Customer Name: " + resultSet.getString("name"));
                System.out.println("Account Type: " + resultSet.getString("account_type"));
                System.out.println("Balance: " + resultSet.getString("balance"));
                System.out.println("--------------------");
            }
            if(!resultSet.next()){
                System.out.println("No accounts associated with this customer found.");
            }
        }catch(Exception e){   
            System.out.println("ERROR: generating customer report failed.");
            e.printStackTrace();
            System.out.println(e);
        }
    }
    
    public void deleteTransactions(){
        //TODO: Delete the list of transactions from each of the accounts, in preparation for a new month of processing.
        //should just be deleting every transaction 
        
        System.out.println("All transactions have been deleted.");
    }

    public void generateDTER(){
        //TODO: According to the law, each customer who earns more than $10,000 within one month must be reported
        // to the government. Generate a list of all customers who have made more than $10,000 in the last month,
        // including earnings from buying/selling stocks and interest. The residence state of each customer should
        // also be listed
        // query the db for all customers who have made more than $10,000 in the last month
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        String query = "SELECT c.customer_id, c.name, c.state, SUM(t.buy_shares) AS total_buy_shares, SUM(t.sell_shares) AS total_sell_shares, SUM(t.buy_shares * t.buy_price) AS total_buy_price, SUM(t.sell_shares * t.sell_price) AS total_sell_price " +
                       "FROM Trades t " +
                       "JOIN Customers c ON t.customer_id = c.customer_id " +
                       "WHERE MONTH(t.trade_date) = ? AND YEAR(t.trade_date) = ? " +
                       "GROUP BY c.customer_id " +
                       "HAVING total_buy_price + total_sell_price >= 10000";
        
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setInt(1, currentMonth);
            statement.setInt(2, currentYear);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                System.out.println("Customer ID: " + resultSet.getInt("customer_id"));
                System.out.println("Customer Name: " + resultSet.getString("name"));
                System.out.println("Residence State: " + resultSet.getString("state"));
                System.out.println("--------------------");
            }
            if(!resultSet.next()){
                System.out.println("No customers have made more than $10,000 in the last month.");
            }
        }catch(Exception e){
            System.out.println("ERROR: generating DTER failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        
    }

    public static void main(String[] args) {
        // prompt manager for options and call the appropriate function
        // first manager needs to login
        ManagerInterfaceDAOImpl manager = new ManagerInterfaceDAOImpl();
        System.out.println("Welcome to the Manager Interface!");
        System.out.println("---------------------------------");
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
                    System.out.println("Enter the interest rate as a decimal: ");
                    float interestRate = scanner2.nextFloat();
                    manager.addInterest(interestRate);

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
                    System.out.println("Enter the customer username: ");
                    String customerUsername2 = scanner2.nextLine(); 
                    Customer customer2 = customerDAO.getCustomer(customerUsername2);
                    manager.customerReport(customer2);
                    break;
                case 6:
                    System.out.println("Deleting all transactions...");
                    manager.deleteTransactions();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println("What would you like to do next?");
            System.out.println("1. Add Interest");
            System.out.println("2. Generate Monthly Statement");
            System.out.println("3. List Active Customers");
            System.out.println("4. Generate DTER");
            System.out.println("5. Customer Report");
            System.out.println("6. Delete Transactions");
            System.out.println("7. Exit");
            choice = scanner.nextInt();
        }
        System.out.println("Goodbye!");
        //close scanner when 7 is selected
        scanner.close();
        scanner2.close();

    }
}
    