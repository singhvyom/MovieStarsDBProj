package Src;

public class Transaction {
    
    public void deposit(){};
    public void withdraw(){};
    public void buy(){};
    public void sell(){};
    public void cancel(){};

//     Amt added = monthly interest rate times the average daily
//     balance for the month (e.g., an account with balance $30 for 10 days and $60 for 20 days in a 30-day
//     month has an average daily balance of $50, not the simple average of $45!). Interest is added at the end
//     of each month.
    public void accrueInterest(){};
}
