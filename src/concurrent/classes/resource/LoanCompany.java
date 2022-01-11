package concurrent.classes.resource;


public class LoanCompany extends Thread{

    private String name;
    private String studentName;
    private BankAccount studentAccount;
    private ThreadGroup threadGroup;

    public LoanCompany(String name, String stdName, BankAccount stdAccount, ThreadGroup threadGroup) {
        super(threadGroup, name);
        this.studentName = stdName;
        this.studentAccount = stdAccount;
        this.threadGroup = threadGroup;
    }

    public void run() {
        System.out.println("Starting thread: " + this.getName());
        int[] loans = {5000, 10000, 6000};
        for(int i = 0; i < loans.length; i++) {
            this.depositLoan(loans[i]);
            try{
                sleep((int)(Math.random()*1000));
            } catch (InterruptedException ex) {}
        }
    }

    public void depositLoan(int value) {
        Transaction tr = new Transaction(this.name, value);
        this.studentAccount.deposit(tr);
    }
}
