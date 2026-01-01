package domain;

import java.time.LocalDateTime;

public class Transaction {

    private String id;
    private String accountNumber;
    private Type type;
    private Double amount;
    private LocalDateTime timestamp;
    private String note;


    public Transaction(LocalDateTime timestamp, String id, String accountNumber, Type type, Double amount, String note) {
        this.timestamp = timestamp;
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.note = note;
    }
}
