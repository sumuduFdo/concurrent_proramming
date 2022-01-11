package concurrent.classes.resource;

public class Grandmother extends Thread{

    // contains information about the thread group
    private String name;
    private String studentName;
    private ThreadGroup threadGroup;
    private BankAccount studentAccount;

    public Grandmother(String name, String studentName, BankAccount stdAccount, ThreadGroup threadGroup) {
        super(threadGroup, name);
        this.name = name;
        this.studentName = studentName;
        this.studentAccount = stdAccount;
        this.threadGroup = threadGroup;
    }

    public void run() {
        System.out.println("Starting thread: " + this.getName());
        int[] deposits = {2000, 3000};
        for (int i = 0; i < deposits.length; i++) {
            this.depositMoney(deposits[i]);
            try{
                sleep((int)(Math.random()*1000));
            } catch (InterruptedException ex) {}
        }
        System.out.println("Terminating thread: " + this.getName());
    }

    public void depositMoney(int value) {
        Transaction tr = new Transaction(this.name, value);
        this.studentAccount.deposit(tr);
    }
}
