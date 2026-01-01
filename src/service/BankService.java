package service;

import domain.Account;

import java.util.List;

public interface BankService {

    public String openAccount(String name, String email, String accountType);
    List<Account> listAccounts();

    void deposit(String accountNumber, Double amount, String note);

    void withdraw(String accountNumber, Double amount, String note);

    void transfer(String accountNumber, String accountNumber2, Double amount, String transfer);
}
