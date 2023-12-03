package Src;
public class Customer {
    private String name;
    private String state;
    private String phoneNum;
    private String email;
    private String taxID;
    private String password;
    private String username;

    public Customer(String name, String state, String phoneNum, String email, String taxID, String password, String username){
        this.name = name;
        this.state = state;
        this.phoneNum = phoneNum;
        this.email = email;
        this.taxID = taxID;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getState() {
        return state;
    }

    public String getTaxID() {
        return taxID;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
