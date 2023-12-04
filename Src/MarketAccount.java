package Src;

public class MarketAccount {

    private String username;
    private int mkta_id;
    private float balance;

    public MarketAccount(String username, int mkta_id, float balance) {
        this.username = username;
        this.mkta_id = mkta_id;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMkta_id() {
        return mkta_id;
    }

    public void setMkta_id(int mkta_id) {
        this.mkta_id = mkta_id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
