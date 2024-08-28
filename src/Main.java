import java.util.Scanner;
import java.util.InputMismatchException;

class AccountDetails {
    String firstName;
    String lastName;
    String fullName;
    String accountType;
    long accountNumber;
    float balance;

    public AccountDetails(String firstName, String lastName, String accountType, long accountNumber, float balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountDetails[] accounts = new AccountDetails[3];
        int numAccounts = 0;

        System.out.println("Welcome to STSave!");

        for (int i = 0; i < accounts.length; i++) {
            try {
                System.out.println("Please enter details for account " + (i + 1));
                System.out.print("First Name: ");
                String firstName = scanner.next();
                System.out.print("Last Name: ");
                String lastName = scanner.next();
                System.out.print("Account Type: ");
                String accountType = scanner.next();
                System.out.print("Enter your 14-digit account number: ");
                long accountNumber = scanner.nextLong();
                System.out.print("Enter initial deposit: ");
                float balance = scanner.nextFloat();

                accounts[i] = new AccountDetails(firstName, lastName, accountType, accountNumber, balance);
                numAccounts++;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please try again!");
                scanner.nextLine();
                i--;
            }
        }
        // Display account details
        displayAccountDetails(accounts, numAccounts);

        while (true) {
            try {
                System.out.print("\nEnter your 14-digit account number: ");
                long accNum = scanner.nextLong();
                int indexOfAcc = findAccount(accounts, accNum);

                if (indexOfAcc == -1) {
                    System.out.println("Account not found! Please try again.");
                } else {
                    AccountDetails targetAccount = accounts[indexOfAcc];
                    System.out.println("Dear," + targetAccount.fullName);

                    char transactionType = getTransactionType(scanner);
                    if (transactionType == '-') {
                        System.out.print("How much do you want to withdraw? ");
                        float transaction = scanner.nextFloat();
                        if (transaction > targetAccount.balance) {
                            System.out.println("Insufficient funds!");
                            if (performAnotherTransaction(scanner)) {
                                continue;
                            }
                        } else {
                            targetAccount.balance -= transaction;
                            System.out.printf("Transaction successful! Your balance is GHS %.2f%n", targetAccount.balance);
                        }
                    } else if (transactionType == '+') {
                        System.out.print("How much do you want to deposit? ");
                        float transaction = scanner.nextFloat();
                        targetAccount.balance += transaction;
                        System.out.printf("Your account balance is: GHS %.2f%n", targetAccount.balance);
                    } else {
                        System.out.println("Invalid transaction type!");
                    }

                    if (!performAnotherTransaction(scanner)) {
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please try again!");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

        //Find all methods below
        //get transaction type
        private static char getTransactionType(Scanner scanner) {
            System.out.print("\nWhat transaction do you want to perform?\nEnter '-' to withdraw or '+' to deposit: ");
            return scanner.next().charAt(0);
        }

        //display account details
        private static void displayAccountDetails(AccountDetails[] accounts, int numAccounts) {
            System.out.println("\n---------------");
            System.out.println("Account Details:");
            for (int i = 0; i < numAccounts; i++) {
                System.out.println("\nName: " + accounts[i].fullName);
                System.out.println("Account Type: " + accounts[i].accountType);
                System.out.println("Account Number: " + accounts[i].accountNumber);
                System.out.printf("Balance: GHS %.2f%n", accounts[i].balance);
            }
        }

    //perform another transaction
    private static boolean performAnotherTransaction(Scanner scanner) {
        while (true) {
            System.out.print("Do you want to perform another transaction? (y/n): ");
            char anotherTransaction = scanner.next().charAt(0);
            if (anotherTransaction == 'y' || anotherTransaction == 'Y') {
                return true;
            } else if (anotherTransaction == 'n' || anotherTransaction == 'N') {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    //find user account
    private static int findAccount(AccountDetails[] accounts, long accNum) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].accountNumber == accNum) {
                return i;
            }
        }
        return -1;
    }


}