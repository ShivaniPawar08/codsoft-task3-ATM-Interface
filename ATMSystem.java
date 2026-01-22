import java.util.*;

public class ATMSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankAccount account = new BankAccount(1001, "Shivani Pawar", 5000, 1234); 
        ATM atm = new ATM(account, sc);

        System.out.println(" Welcome to the ATM System");

    
        int attempts = 0;
        boolean authenticated = false;
        while (attempts < 3) {
            System.out.print("Enter your PIN: ");
            if (sc.hasNextInt()) {
                int inputPin = sc.nextInt();
                if (account.validatePIN(inputPin)) {
                    authenticated = true;
                    break;
                } else {
                    System.out.println(" Invalid PIN");
                    attempts++;
                }
            } else {
                sc.next();
                System.out.println(" Enter numbers only");
            }
        }

        if (!authenticated) {
            System.out.println(" Too many failed attempts. Exiting...");
            sc.close();
            return;
        }

        boolean running = true;

        while (running) {

            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = atm.getIntInput();

            switch (choice) {
                case 1:
                    atm.checkBalance();
                    break;
                case 2:
                    atm.deposit();
                    break;
                case 3:
                    atm.withdraw();
                    break;
                case 4:
                    atm.showTransactions();
                    break;
                case 5:
                    running = false;
                    System.out.println(" Thank you for using ATM!");
                    break;
                default:
                    System.out.println(" Invalid choice");
            }
        }

        sc.close();
    }
}

class ATM {

    private BankAccount account;
    private Scanner sc;

    public ATM(BankAccount account, Scanner sc) {
        this.account = account;
        this.sc = sc;
    }

    public void checkBalance() {
        System.out.println(" Current Balance: ₹" + account.getBalance());
    }

    public void deposit() {
        System.out.print("Enter amount to deposit: ₹");
        double amount = getDoubleInput();
        if (amount <= 0) {
            System.out.println(" Deposit must be greater than 0");
            return;
        }
        account.deposit(amount);
        System.out.println(" ₹" + amount + " deposited successfully");
    }

    public void withdraw() {
        System.out.print("Enter amount to withdraw: ₹");
        double amount = getDoubleInput();
        if (amount <= 0) {
            System.out.println(" Withdrawal must be greater than 0");
            return;
        }
        if (account.withdraw(amount)) {
            System.out.println(" ₹" + amount + " withdrawn successfully");
        } else {
            System.out.println(" Insufficient balance");
        }
    }

    public void showTransactions() {
        System.out.println("\n Transaction History:");
        if (account.getTransactions().isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String t : account.getTransactions()) {
                System.out.println(t);
            }
        }
    }

    public int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        int num = sc.nextInt();
        sc.nextLine(); 
        return num;
    }

    public double getDoubleInput() {
        while (!sc.hasNextDouble()) {
            System.out.print("Enter a valid amount: ");
            sc.next();
        }
        double num = sc.nextDouble();
        sc.nextLine();
        return num;
    }
}



class BankAccount {

    private int accountNo;
    private String holderName;
    private double balance;
    private int pin;
    private ArrayList<String> transactions = new ArrayList<>();

    public BankAccount(int accountNo, String holderName, double balance, int pin) {
        this.accountNo = accountNo;
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
    }

    public boolean validatePIN(int inputPin) {
        return inputPin == pin;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited: ₹" + amount + ", Balance: ₹" + balance);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add("Withdrawn: ₹" + amount + ", Balance: ₹" + balance);
            return true;
        }
        return false;
    }
}
