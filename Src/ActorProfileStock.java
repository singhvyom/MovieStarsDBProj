package Src;

public class ActorProfileStock {

    private String name;
    private String stockSymbol;
    private float currentPrice;
    private float closingPrice;
    private String dob;
    public ActorProfileStock(String name, String stockSymbol, float currentPrice, float closingPrice, String dob) {
        this.name = name;
        this.stockSymbol = stockSymbol;
        this.currentPrice = currentPrice;
        this.closingPrice = closingPrice;
        this.dob = dob;
    }

    @Override
    public String toString() {
        // TODO Format actor profile
        return "ActorProfileStock{" +
                "name='" + name + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", currentPrice=" + currentPrice +
                ", closingPrice=" + closingPrice +
                ", dob='" + dob + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(float closingPrice) {
        this.closingPrice = closingPrice;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
