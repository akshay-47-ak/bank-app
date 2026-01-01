package app;

import java.util.Scanner;

public class Main {

    static void main() {

        Scanner sc = new Scanner(System.in);

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

                case "0" ->running=false;

            }
        }

    }
}
