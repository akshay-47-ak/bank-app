package service.service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository = new AccountRepository();

    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();

    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();

        Customer c  = new Customer(customerId,name,email);
        customerRepository.save(c);

        //Change Later
       // String accountNumber =UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();

        Account account = new Account(accountNumber,customerId,(double)0,accountType);
        accountRepository.save(account);
        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(()->new RuntimeException("Account Not Found: "));
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                amount,UUID.randomUUID().toString(),note, Type.DEPOSIT,LocalDateTime.now());
        transactionRepository.add(transaction);

    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(()->new RuntimeException("Account Not Found: "));

        if(account.getBalance().compareTo(amount)<0)
            throw new RuntimeException("Insufficiant Balance");


        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                amount,UUID.randomUUID().toString(),note, Type.WITHDRAW,LocalDateTime.now());
        transactionRepository.add(transaction);
    }

    @Override
    public void transfer(String fromAcc, String  tooAcc, Double amount, String note) {
    if(fromAcc.equals(tooAcc))
        throw new RuntimeException("Can't Tranfer to Your Own account");
    Account Fromaccount = accountRepository.findByNumber(fromAcc)
            .orElseThrow(()->new RuntimeException("Account Not Found: "));
        Account Toaccount = accountRepository.findByNumber(tooAcc)
                .orElseThrow(()->new RuntimeException("Account Not Found: "));

        if(Fromaccount.getBalance().compareTo(amount)<0)
            throw new RuntimeException("Insufficiant Balance");
        Fromaccount.setBalance(Fromaccount.getBalance() - amount);
        Toaccount.setBalance(Toaccount.getBalance() + amount);


        transactionRepository.add(new Transaction( Fromaccount.getAccountNumber(),
                amount,UUID.randomUUID().toString(),
                note,Type.TRANSFER_OUT,LocalDateTime.now()));

        transactionRepository.add(new Transaction( Toaccount.getAccountNumber(),
                amount,UUID.randomUUID().toString(),
                note,Type.TRANSFER_IN,LocalDateTime.now()));

    }

    @Override
    public List<Transaction> getStatement(String account) {
        return transactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountByCustomerName(String q) {
        String query = (q == null) ?"":q.toLowerCase();

        List<Account> result = new ArrayList<>();
          for(Customer c : customerRepository.findAll()){
              if(c.getName().toLowerCase().contains(query))
                  result.addAll(accountRepository.findByCustomerId(c.getId()));
          }
          result.sort(Comparator.comparing(Account::getAccountNumber));
            return result;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size() + 1;
        return String.format("AC%06d",size);
    }
}
