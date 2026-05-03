import java.io.*;
import java.util.*;

public class Main {

    static ArrayList<BankAccount> accounts=new ArrayList<>();
    static final String File_Name="accounts.txt";

    static {
        System.out.println("=================================");
        System.out.println("\nWelcome to "+BankAccount.Bank_Name+".....\n");
        System.out.println("=================================");
    }

    public static void main(String[] args) {

        loadAccs();
        syncCounter();

        Scanner sc=new Scanner(System.in);

        while(true){
            System.out.println("=================================");
            System.out.println("Press : ");
            System.out.println("1. Create Account.");
            System.out.println("2. Login.");
            System.out.println("3. Exit.");
            System.out.println("=================================");
            int choice=sc.nextInt();

            try {
                switch (choice) {

                    case 1: {
                        System.out.println("Enter first name : ");
                        String name = sc.next();
                        System.out.println("Enter Age : ");
                        int age = sc.nextInt();
                        System.out.println("Create password : ");
                        String password=sc.next();

                        BankAccount acc = new BankAccount(name, age,password);
                        accounts.add(acc);

                        System.out.println("=================================");
                        System.out.println("Account created Successfully...\nYour unique account number is " + acc.getAccountNumber() + ".");
                        System.out.println("=================================");

                        saveAccs();
                        break;
                    }

                    case 2: {
                        BankAccount acc = getAcc(sc);

                        if (acc != null) {
                            System.out.println("Enter password : ");
                            String pass=sc.next();

                            if (acc.checkPass(pass)) {

                                System.out.println("=================================");
                                System.out.println("Welcome " + acc.getName().toUpperCase() + "...");
                                System.out.println("=================================");

                                int choice2 = 0;

                                while (choice2 != 10) {

                                    System.out.println("=================================");
                                    System.out.println("Press : ");
                                    System.out.println("1. Deposit.");
                                    System.out.println("2. Withdraw.");
                                    System.out.println("3. Account Details.");
                                    System.out.println("4. Online Transaction.");
                                    System.out.println("5. Check Balance");
                                    System.out.println("6. Change Password");
                                    System.out.println("7. Transaction History");
                                    System.out.println("10. Bank portal.");
                                    System.out.println("11. Exit.");
                                    System.out.println("=================================");

                                    choice2 = sc.nextInt();

                                    try {
                                        switch (choice2) {

                                            case 1: {
                                                System.out.println("Amount : ");
                                                acc.deposit(sc.nextDouble());
                                                saveAccs();
                                                System.out.println("Successfully Deposited.......");
                                                break;
                                            }

                                            case 2: {
                                                System.out.println("Amount : ");
                                                acc.withdraw(sc.nextDouble());
                                                saveAccs();
                                                System.out.println("Transaction Successful.......");
                                                break;
                                            }

                                            case 3: {
                                                acc.display();
                                                break;
                                            }

                                            case 4: {
                                                System.out.println("Receiver's account number : ");
                                                int recNo = sc.nextInt();
                                                BankAccount rec = findAcc(recNo);

                                                System.out.println("Amount : ");
                                                double amount = sc.nextDouble();

                                                acc.transfer(rec, amount);
                                                saveAccs();
                                                break;
                                            }

                                            case 5: {
                                                System.out.println("=================================\nBank Balance : Rs." + acc.getBalance()+"\n=================================");
                                                break;
                                            }

                                            case 6:{
                                                System.out.println("Old password : ");
                                                String oldPass=sc.next();
                                                System.out.println("New password : ");
                                                acc.changePass(oldPass,sc.next());
                                                saveAccs();
                                                break;
                                            }

                                            case 7:{
                                                acc.showHistory();
                                                break;
                                            }

                                            case 10: {
                                                break;
                                            }

                                            case 11: {
                                                System.out.println("=================================");
                                                System.out.println("Thanks! for visiting "+BankAccount.Bank_Name+"\nPunaah aagman ki pratiksha...");
                                                System.out.println("=================================");
                                                return;
                                            }

                                            default:
                                                System.out.println("##### Enter valid operation! #####");
                                        }

                                    } catch (InsufficientBalanceException | IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            } else {
                                System.out.println("##### Incorrect password! #####");
                            }
                        }
                        break;
                    }

                    case 3: {
                        System.out.println("=================================");
                        System.out.println("Thanks! for visiting "+BankAccount.Bank_Name+"\nPunaah aagman ki pratiksha...");
                        System.out.println("=================================");
                        return;
                    }

                    default:
                        System.out.println("##### Enter valid operation! #####");
                }

            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    static BankAccount findAcc(int accNo){
        for(BankAccount acc:accounts){
            if(acc.getAccountNumber()==accNo){
                return acc;
            }
        }
        return null;
    }

    static BankAccount getAcc(Scanner sc){
        System.out.println("Account number : ");
        int accNo=sc.nextInt();
        BankAccount acc=findAcc(accNo);

        if(acc==null) {
            System.out.println("##### Account does not exists! #####");
        }
        return acc;
    }

    static void saveAccs(){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(File_Name))){
            for(BankAccount acc:accounts){
                bw.write(acc.fileToString());
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("##### Error in Saving files! #####");
        }
    }

    static void loadAccs(){
        try(BufferedReader br=new BufferedReader(new FileReader(File_Name))){
            String line;
            while((line=br.readLine())!=null){
                accounts.add(BankAccount.stringToFile(line));
            }
        } catch (IOException e) {
            System.out.println("##### No previous data! #####");
        }
    }

    static void syncCounter(){
        int max = 20260000;
        for(BankAccount acc:accounts){
            if(acc.getAccountNumber() > max){
                max = acc.getAccountNumber();
            }
        }
        BankAccount.setCounter(max + 1);
    }
}