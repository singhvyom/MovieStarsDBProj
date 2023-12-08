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
import Src.ActorProfileStock;
import Src.DAO.ActorProfileStockDAO;
import Src.DAOImpl.ActorProfileStockDAOImpl;
import Src.DAO.SysInfoDAO;
import Src.DAOImpl.SysInfoDAOImpl;
import Src.DAO.MarketAccountTransactionDAO;
import Src.DAOImpl.MarketAccountTransactionDAOImpl;
import Src.DAO.StockAccountTransactionDAO;
import Src.DAOImpl.StockAccountTransactionDAOImpl;
import Src.DAO.MarketAccountDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        SysInfoDAO sysInfoDAO = new SysInfoDAOImpl();
        String marketDate = sysInfoDAO.getMarketDate();
        LocalDate date = LocalDate.parse(marketDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int currentDay = date.getDayOfMonth();
        int lastDay = date.lengthOfMonth();
        if(currentDay != lastDay){
            System.out.println("ERROR: cannot add interest until the last day of the month.");
            return;
        }
        String query = "UPDATE MarketAccount SET balance = balance * (1 + ?)";
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
    private ResultSet getMarketAccountTransactions(String username){
       
        SysInfoDAO sysInfoDAO = new SysInfoDAOImpl();
        String marketDate = sysInfoDAO.getMarketDate();
        LocalDate date = LocalDate.parse(marketDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int month = date.getMonthValue();
        String monthString = String.valueOf(month);
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        int mkta_id = marketAccountDAO.getMarketAccountId(username);

        String query = "SELECT * FROM MarketAccountTransaction WHERE mkta_id = ? AND EXTRACT(MONTH FROM transaction_date) = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, mkta_id);
            statement.setString(2, monthString);
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        }catch(Exception e){
            System.out.println("ERROR: getting market account transactions failed.");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    private ResultSet getStockAccountTransactions(String username){
        SysInfoDAO sysInfoDAO = new SysInfoDAOImpl();
        String marketDate = sysInfoDAO.getMarketDate();
        LocalDate date = LocalDate.parse(marketDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int month = date.getMonthValue();
        String monthString = String.valueOf(month);
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        int mkta_id = marketAccountDAO.getMarketAccountId(username);

        String query = "SELECT * FROM StockAccountTransaction WHERE mkta_id = ? AND EXTRACT(MONTH FROM transaction_date) = ?";
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);   
            statement.setInt(1, mkta_id);
            statement.setString(2, monthString);
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        }catch(Exception e){
            System.out.println("ERROR: getting stock account transactions failed.");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void generateMonthlyStatement(Customer customer) {

        //first check if the customer has a market account
        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        int mkta_id = marketAccountDAO.getMarketAccountId(customer.getUsername());
        if(mkta_id == -1){
            System.out.println("No market account found for this customer.");
            return;
        }
        // TODO: Given a customer, do the following for each account she/he owns: generate a list of all transactions
        // that have occurred in the current month. This statement should list the name and email address of the
        // customer.
        // The initial and final account balance is to be included, so are the total earning/loss (including interest)
        // this month and the total amount of commissions paid.
        // The statement will be displayed in your interface.
        
        SysInfoDAO sysInfoDAO = new SysInfoDAOImpl();
        String marketDate = sysInfoDAO.getMarketDate();
        LocalDate date = LocalDate.parse(marketDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int month = date.getMonthValue();
        String monthString = String.valueOf(month);
        String username = customer.getUsername();
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Customer Email: " + customer.getEmail());
        System.out.println("Account Statements: ");
        //TODO: first get the market account initial balance
        //then get the market account final balance
        //then get the total earnings/loss (so just profit), will sum up all profits of all stocks

        //then get the total amount of commissions paid
        //then list each transaction (id, amount, type)
        ResultSet marketAccountTransactions = getMarketAccountTransactions(username);
        while(marketAccountTransactions.next()){
            int transaction_id = marketAccountTransactions.getInt("transaction_id");
            float amount = marketAccountTransactions.getFloat("amount");
            String type = marketAccountTransactions.getString("type");
            System.out.println("Transaction ID: " + transaction_id);
            System.out.println("Amount: " + amount);
            System.out.println("Type: " + type);
        }

        //now show the tranasctions for each stock account

        ResultSet stockAccountTransactions = getStockAccountTransactions(username);
        while(stockAccountTransactions.next()){
            int transaction_id = stockAccountTransactions.getInt("transaction_id");
            float shares = stockAccountTransactions.getFloat("shares");
            String stock = stockAccountTransactions.getString("stock");
            String type = stockAccountTransactions.getString("type");
            String date = stockAccountTransactions.getString("transaction_date");
            System.out.println("Transaction ID: " + transaction_id);
            System.out.println("Stock: " + stock);
            System.out.println("Shares: " + shares);
            System.out.println("Type: " + type);
            System.out.println("Date: " + date);
        }

        //now to get total amount of commission

    }
    
    public void listActiveCustomers(){
        //TODO: Generate a list of all customers who have traded (buy or sell) at least 1,000 shares in the current month
        //need to sum up the number of shares bought and sold for each customer
        //query the db for all customers who have traded at least 1,000 shares in the current month
        SysInfoDAO sysInfoDAO = new SysInfoDAOImpl();
        String marketDate = sysInfoDAO.getMarketDate();
        LocalDate date = LocalDate.parse(marketDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int month = date.getMonthValue();
        String monthString = String.valueOf(month);
        //now for every customer, sum up numbrt of 'shares' for every
        //stock market transaction. display names if sum is greater than 1000
        Map<String, Float> customerShares = new HashMap<String, Float>();

        String query = "SELECT sa.username, SUM(sat.shares) AS total_traded_shares " +
                        "FROM StockAccountTransaction sat " +
                        "JOIN StockAccount sa ON sat.stock = sa.stock AND sat.mkta_id = sa.mkta_id " +
                        "WHERE EXTRACT(MONTH FROM sat.transaction_date) = ? " +
                        "GROUP BY sa.username"
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, monthString);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String username = resultSet.getString("username");
                float totalShares = resultSet.getFloat("total_traded_shares");
                customerShares.put(username, totalShares);
            }
            //now we have a map of all customers and their total shares traded
            for(Map.Entry<String, Float> entry : customerShares.entrySet()){
                if(entry.getValue() >= 1000){
                    CustomerDAO customerDAO = new CustomerDAOImpl();
                    Customer customer = customerDAO.getCustomer(entry.getKey());
                    System.out.println("Customer Name: " + customer.getName());
                    System.out.println("--------------------");
                }
            }
        }catch(Exception e){
            System.out.println("ERROR: listing active customers failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        
        
    }
    
    public void customerReport(Customer customer){
        //TODO:Generate a list of all accounts associated with a particular customer and the current balance

        String username = customer.getUsername(); //needed for account balances
        System.out.println("Customer Name: " + customer.getName());

        MarketAccountDAO marketAccountDAO = new MarketAccountDAOImpl();
        int mkta_id = marketAccountDAO.getMarketAccountId(username);
        if(mkta_id == -1){
            System.out.println("No market account found for this customer.");
            return;
        }
        else{
            //get the market account balance
            float marketAccountBalance = marketAccountDAO.getBalance(username);
            System.out.println("Market Account Balance: " + marketAccountBalance);
            //now for stock acc
            System.out.println("Stock Account Balances: ");
            StockAccountDAOImpl stockAccountDAO = new StockAccountDAOImpl();
            stockAccountDAO.showAllShares(mkta_id);
        }
    }
    
    public void deleteTransactions(){
        //TODO: Delete the list of transactions from each of the accounts, in preparation for a new month of processing.
        //should just be deleting every transaction 
        MarketAccountTransactionDAO marketAccountTransactionDAO = new MarketAccountTransactionDAOImpl();
        boolean marketClear = marketAccountTransactionDAO.clearAllMarketAccountTransactions();
        if(marketClear){
            System.out.println("Market account transactions have been cleared.");
        }
        StockAccountTransactionDAO stockAccountTransactionDAO = new StockAccountTransactionDAOImpl();
        boolean stockClear = stockAccountTransactionDAO.clearAllTransactions();

        if(stockClear){
            System.out.println("Stock account transactions have been cleared.");
        }
    
    }

    public void generateDTER(){
        //TODO: According to the law, each customer who earns more than $10,000 within one month must be reported
        // to the government. Generate a list of all customers who have made more than $10,000 in the last month,
        // including earnings from buying/selling stocks and interest. The residence state of each customer should
        // also be listed
        // query the db for all customers who have made more than $10,000 in the last month
        SysInfoDAO sysInfoDAO = new SysInfoDAOImpl();
        String marketDate = sysInfoDAO.getMarketDate();
        LocalDate date = LocalDate.parse(marketDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int month = date.getMonthValue();
        String monthString = String.valueOf(month);
        
        Map<String, Float> customerEarnings = new HashMap<String, Float>();
        String query = "SELECT sa.username, SUM(sat.profit) AS total_earnings " + 
                        "FROM StockAccountTransaction sat " +
                        "JOIN StockAccount sa ON sat.stock = sa.stock AND sat.mkta_id = sa.mkta_id " + 
                        "WHERE EXTRACT(MONTH FROM sat.transaction_date) " +
                        "GROUP BY sa.username"
        
        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, monthString);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String username = resultSet.getString("username");
                float totalEarnings = resultSet.getFloat("total_earnings");
                customerEarnings.put(username, totalEarnings);
            }

            //now we have a map of all customers and their total earnings
            for(Map.Entry<String, Float> entry : customerEarnings.entrySet()){
                if(entry.getValue() >= 10000){
                    CustomerDAO customerDAO = new CustomerDAOImpl();
                    Customer customer = customerDAO.getCustomer(entry.getKey());
                    System.out.println("Customer Name: " + customer.getName());
                    System.out.println("Customer State: " + customer.getState());
                    System.out.println("--------------------");
                }
            }
        }catch(Exception e){
            System.out.println("ERROR: generating DTER failed.");
            e.printStackTrace();
            System.out.println(e);
        }
        
    }
    public void openMarket(String newDate){
        // open market on date. set is_open to true, set market_date to date
        SysInfoDAO sysInfoDAO = new SysInfoDAOImpl();
        boolean open = sysInfoDAO.openMarket(newDate);
        if(open){
            System.out.println("Market has been opened.");
        }
        
    }
    public void closeMarket(){
        //set all stock current prices to stock closing prices
        //then set is_open to false
        SysInfoDAO sysInfoDAO = new SysInfoDAOImpl();
        sysInfoDAO.closeMarket();

    }

    public void setStockPrice(String stockSymbol, float newPrice){
        ActorProfileStockDAO actorProfileStockDAO = new ActorProfileStockDAOImpl();
        ActorProfileStock actorProfileStock = actorProfileStockDAO.getActorProfileStock(stockSymbol);
        actorProfileStock.setCurrentPrice(newPrice);
        System.out.println("Stock price has been updated.");
    }

    public void setCurrentDate(String newDate){
        SysInfoDAO sysInfoDAO = new SysInfoDAOImpl();
        boolean date = sysInfoDAO.setDate(newDate);
        if(date){
            System.out.println("Date has been updated.");
        }
    }

    public void main(String[] args) {
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
        System.out.println("Test, Debug, Demo Operations:");
        System.out.println("7. Open market");
        System.out.println("8. Close market");
        System.out.println("9. Set a new price for the stock");
        System.out.println("10. Exit");
        int choice = scanner2.nextInt();
        while(choice != 10){
            switch(choice){
                case 1:
                    System.out.println("Enter the interest rate as a decimal: ");
                    float interestRate = scanner2.nextFloat();
                    addInterest(interestRate);
                    break;
                case 2:
                    System.out.println("Enter the customer username: ");
                    String customerUsername = scanner2.nextLine();
                    Customer customer = customerDAO.getCustomer(customerUsername);
                    generateMonthlyStatement(customer);
                    break;
                case 3:
                    listActiveCustomers();
                    break;
                case 4:
                    generateDTER();
                    break;
                case 5:
                    System.out.println("Enter the customer username: ");
                    String customerUsername2 = scanner2.nextLine(); 
                    Customer customer2 = customerDAO.getCustomer(customerUsername2);
                    customerReport(customer2);
                    break;
                case 6:
                    System.out.println("Deleting all transactions...");
                    deleteTransactions();
                    break;
                case 7:
                    System.out.println("What day would you like to open the market for?");
                    String newDate = scanner2.nextLine();
                    openMarket(newDate);
                    break;
                case 8:
                    System.out.println("Closing market...");
                    closeMarket();
                    break;
                case 9:
                    System.out.println("Enter the stock symbol: ");
                    String stockSymbol = scanner2.nextLine();
                    System.out.println("Enter the new price for the stock: ");
                    float newPrice = scanner2.nextFloat();
                    
                    setStockPrice(stockSymbol, newPrice);
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
            System.out.println("Test, Debug, Demo Operations:");
            System.out.println("7. Open market for the day");
            System.out.println("8. Close market for the day");
            System.out.println("9. Set a new price for the stock");
            System.out.println("10. Exit");
            choice = scanner.nextInt();
        }
        System.out.println("Goodbye!");
        //close scanner when 10 is selected
        scanner.close();
        scanner2.close();

    }
}
    
