package concurrent.classes.resource;

public class University extends Thread{

    private String name;
    private String studentName;
    private BankAccount studentAccount;
    private ThreadGroup threadGroup;

    public University(String name, String stdName, BankAccount stdAccount, ThreadGroup threadGroup) {
        super(threadGroup, name);
        this.name = name;
        this.studentName = stdName;
        this.studentAccount = stdAccount;
        this.threadGroup = threadGroup;
    }

    public void run() {
        System.out.println("Starting thread: " + this.name);
        int[] fees = { 8000, 6000, 4000};
        for (int i = 0; i < fees.length; i++){
            this.withdrawCourseFees(fees[i]);
            try{
                sleep((int)(Math.random()*1000));
            } catch (InterruptedException ex) {}
        }
    }

    public void withdrawCourseFees(int value) {
        Transaction tr = new Transaction(this.name, value);
        this.studentAccount.withdrawal(tr);
    }
}
