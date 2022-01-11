package concurrent.classes.resource;

import java.util.concurrent.locks.ReentrantLock;

public class CurrentAccount implements BankAccount {

    private final String accountHolder;
    private final int accountNumber;
    private int balance;
    private final Statement accountStatement;

    // state variables
    private boolean isLocked = false;

    public CurrentAccount(int balance, int accountNumber, String name) {
        this.balance = balance;               // opening account balance
        this.accountNumber = accountNumber;   // unique account number
        this.accountHolder = name;            // account holder name
        this.accountStatement = new Statement(this.accountHolder, this.accountNumber);
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int value) {
        this.balance = value;
    }

    @Override
    public int getAccountNumber() {
        return this.accountNumber;
    }

    @Override
    public String getAccountHolder() {
        return this.accountHolder;
    }

    @Override
    public synchronized void deposit(Transaction t) {
        int newBalance = this.balance + t.getAmount();
        this.setBalance(newBalance);
        // update account statement
        this.accountStatement.addTransaction(t.getCID(), t.getAmount(), newBalance);
        System.out.println("----------------------------------------------------------------");
        System.out.println("Deposit of RS." + t.getAmount() + " by "+ t.getCID() +" is successful.");
        System.out.println("Your updated account balance: " + newBalance);
        System.out.println("----------------------------------------------------------------");
    }

    @Override
    public synchronized void withdrawal(Transaction t) {
        int trVal = t.getAmount();
        while(trVal > this.balance) {
            try {
                System.out.println("----------------------------------------------------------------");
                System.out.println("Transaction failed due to insufficient funds..");
                System.out.println("Transaction type: withdrawal ");
                System.out.println("Transaction amount: " + trVal);
                System.out.println("Available amount of funds: " + this.getBalance());
                System.out.println("Waiting for sufficient funds..........");
                System.out.println("----------------------------------------------------------------");
                wait();
            } catch (InterruptedException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        this.balance += trVal;
        this.accountStatement.addTransaction(t.getCID(), trVal, this.getBalance());
        System.out.println("----------------------------------------------------------------");
        System.out.println("Withdrawal of RS." + t.getAmount() + " by "+ t.getCID() +" is successful.");
        System.out.println("Your updated account balance: " + this.getBalance());
        System.out.println("----------------------------------------------------------------");

        notifyAll();
    }


    @Override
    public boolean isOverdrawn() {
        return (this.balance < 0);
    }

    @Override
    public void printStatement() {
        this.accountStatement.print();
    }
}
