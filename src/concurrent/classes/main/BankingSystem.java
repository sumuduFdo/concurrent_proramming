package concurrent.classes.main;

import concurrent.classes.resource.*;

public class BankingSystem {

    public static void main(String[] args) {

        CurrentAccount studentAccount = new CurrentAccount(100, 1234, "Student_1");

        ThreadGroup humans = new ThreadGroup("Humans");
        ThreadGroup companies = new ThreadGroup("Companies");

        Student student = new Student("Student", "std1234", studentAccount, humans);
        Grandmother grandmother = new Grandmother("Grandmother", "Student_1", studentAccount, humans);

        LoanCompany loanCompany = new LoanCompany("Loan_Company", "Student_1", studentAccount, companies);
        University university = new University("University", "Student_1", studentAccount, companies);

        System.out.println("");
        student.start();
        grandmother.start();
        loanCompany.start();
        university.start();

        while(humans.activeCount() > 0 || companies.activeCount() > 0) {
            // do nothing
            continue;
        }

        // print final account status
        System.out.println("Final account statement after transactions:");
        studentAccount.printStatement();

    }

}
