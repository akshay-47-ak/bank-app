package app;

import service.BankService;
import service.service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {

    static void main() {

        Scanner sc = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
        boolean running = true;

        System.out.println("WELCOME TO BANK");
        while (running) {
            System.out.println("""
                    1)Open Account 
                    2)Deposit
                    3)Withdraw
                    4)Transfer 
                    5)Account Statement 
                    6)List Accounts
                    7)Search Accounts By Customer Name 
                    """);
            System.out.println("CHOOSE: ");
            String choice = sc.nextLine().trim();
            System.out.println("CHOICE :" + choice);


            switch (choice){

                case "1" ->openAccount(sc , bankService);
                case "2" ->deposit(sc , bankService);
                case "3" ->withdraw(sc ,bankService);
                case "4" ->transfer(sc);
                case "5" ->statement(sc);
                case "6" ->listAccounts(sc ,bankService);
                case "7" ->searchAccounts(sc);
                case "0" -> running =false;

            }
        }

    }

    private static void openAccount(Scanner sc , BankService bankService) {
        System.out.println("Customer Name : ");
        String name = sc.nextLine().trim();

        System.out.println("Customer Email : ");
        String email = sc.nextLine().trim();

        System.out.println("Account Type (SAVING/CURRENT): ");
        String type = sc.nextLine().trim();

        System.out.println("Initial Deposit (optional, blank for 0): ");
        String amountStr = sc.nextLine().trim();
        Double inital = Double.valueOf(amountStr);
      String accountNumber =  bankService.openAccount(name,email,type);
        if(inital >0){
          bankService.deposit(accountNumber,inital,"Initial Deposit");
        }
        System.out.println("Account Opened : "+accountNumber);
    }

    private static void deposit(Scanner sc ,BankService bankService) {
        System.out.println("AccountNumber: ");
        String accountNumber = sc.nextLine().trim();
        System.out.println("Amount: ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.deposit(accountNumber,amount,"Deposit");
        System.out.println("Deposited");

    }

    private static void withdraw(Scanner sc ,BankService bankService) {

        System.out.println("AccountNumber: ");
        String accountNumber = sc.nextLine().trim();
        System.out.println("Amount: ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.withdraw(accountNumber,amount,"Withdrawal");
        System.out.println("Withdran");

    }

    private static void transfer(Scanner sc) {
    }

    private static void statement(Scanner sc) {
    }

    private static void listAccounts(Scanner sc ,BankService bankService) {
        bankService.listAccounts().forEach(a ->{
            System.out.println(a.getAccountNumber() + " | " + a.getAccountType() + " | " + a.getBalance());
        });
    }

    private static void searchAccounts(Scanner sc) {
    }
}
