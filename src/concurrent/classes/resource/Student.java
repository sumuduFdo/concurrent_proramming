package concurrent.classes.resource;

public class Student extends Thread{

    private String name;
    private BankAccount account;
    private ThreadGroup threadGroup;

    public Student(String name, String id, BankAccount account, ThreadGroup threadGroup) {
        super(threadGroup, name);
        // initialize student with bank account
        this.name = name;
        this.account = account;
        this.threadGroup = threadGroup;
    }

    public void  run() {
        System.out.println("Starting thead: " + this.getName());
        int[] amounts = {100, 500, -600, 455, -130, -400};
        for (int i = 0; i < 6; i++) {
            if(amounts[i] > 0) {
                this.depositMoney(amounts[i]);
            } else {
                this.withdrawMoney(amounts[i]);
            }
            try{
                sleep((int)(Math.random()*1000));
            } catch (InterruptedException ex) {}
        }
        this.account.printStatement();
        System.out.println("Terminating thread: " + this.getName());
    }

    public void depositMoney(int value) {
        Transaction studentTr = new Transaction(this.getName(), value);
        this.account.deposit(studentTr);
    }

    public void withdrawMoney(int value) {
        Transaction studentTr = new Transaction(this.getName(), value);
        this.account.withdrawal(studentTr);
    }

}

