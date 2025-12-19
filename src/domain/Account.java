package domain;

public class Account {

    private String accountNO;
    private String email;
    private double balance;

    public Account(String accountNO , String email, double balance){
        this.accountNO = accountNO;
        this.email = email;
        this.balance = balance;
    }

    public String getAccountNO() {
        return accountNO;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

}
