package Src;

public class StockAccountTransaction {

    private String stock;
    private int mkta_id;
    private float shares;
    private String type;
    private float profit;

    public StockAccountTransaction(String stock, int mkta_id, float shares, String type, float profit) {
        this.stock = stock;
        this.mkta_id = mkta_id;
        this.shares = shares;
        this.type = type;
        this.profit = profit;
    }

    public StockAccountTransaction() {}

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getMkta_id() {
        return mkta_id;
    }

    public void setMkta_id(int mkta_id) {
        this.mkta_id = mkta_id;
    }

    public float getShares() {
        return shares;
    }

    public void setShares(float shares) {
        this.shares = shares;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }
}
