package Src;

public class MarketAccountTransaction {

    private int mkta_id;
    private float amount;
    private String type;

    public MarketAccountTransaction(int mkta_id, float amount, String type) {
        this.mkta_id = mkta_id;
        this.amount = amount;
        this.type = type;
    }

    public MarketAccountTransaction() {}

    public int getMkta_id() {
        return mkta_id;
    }

    public void setMkta_id(int mkta_id) {
        this.mkta_id = mkta_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
