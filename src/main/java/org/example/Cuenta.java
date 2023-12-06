package org.example;

public class Cuenta {
    private int accountid;
    private String iban;
    private double balance;
    private int customerid;


    public Cuenta() {
    }

    public Cuenta(int accountid, String iban, double balance, int customerid) {
        this.accountid = accountid;
        this.iban = iban;
        this.balance = balance;
        this.customerid = customerid;
    }

    public Cuenta(String iban, double balance, int customerid) {
        this.iban = iban;
        this.balance = balance;
        this.customerid = customerid;
    }

    // Getters y Setters
    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "accountid=" + accountid +
                ", iban='" + iban + '\'' +
                ", balance=" + balance +
                ", customerid=" + customerid +
                '}';
    }
}
