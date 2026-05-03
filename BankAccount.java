import java.util.*;

public class BankAccount {

    static final String Bank_Name="PK BANK";
    private static int accountCounter=20260001;

    private String name;
    private int age;
    private int accountNumber;
    private double balance;
    private String password;

    private ArrayList<String> history = new ArrayList<>();

    // constructor 1
    public BankAccount(String name,int age,String password){
        if(age<18){
            throw new IllegalArgumentException("=================================\nAgeLimit : 18\nWait for "+(18-age)+" years...\n=================================");
        }
        this.age=age;
        this.name=name;
        this.password=password;
        this.accountNumber=accountCounter++;
    }

    // constructor 2
    public BankAccount(String name,int age,String password,double balance){
        if(age<18){
            throw new IllegalArgumentException("=================================\nAgeLimit : 18\nWait for "+(18-age)+" years...\n=================================");
        }
        this.age=age;
        this.name=name;
        this.password=password;
        this.accountNumber=accountCounter++;
        this.balance=balance;
    }

    // constructor for loading
    public BankAccount(int accNo,String name,int age,String password,double balance){
        this.accountNumber = accNo;
        this.name = name;
        this.age = age;
        this.password = password;
        this.balance = balance;
    }

    public static void setCounter(int value){
        accountCounter = value;
    }

    public String getName(){
        return name;
    }

    public int getAccountNumber(){
        return accountNumber;
    }

    public double getBalance(){
        return balance;
    }

    // deposit
    public void deposit(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("##### Invalid Amount! #####");
        }
        balance+=amount;
        history.add("Deposited: +" + amount);
    }

    // withdraw
    public void withdraw(double amount) throws InsufficientBalanceException{
        if(amount <= 0){
            throw new IllegalArgumentException("##### Invalid Amount! #####");
        }
        if(amount>balance){
            throw new InsufficientBalanceException("##### Insufficient Bank Balance! #####");
        }
        balance-=amount;
        history.add("Withdrawn: -" + amount);
    }

    public void display(){
        System.out.println("=================================\nBank : "+Bank_Name+"\nAccount Holder : "+name+"\nAge : "+age+"\nAccount Number : "+accountNumber+"\nTotal Balance = Rs."+balance+"\n=================================");
    }

    // transfer
    public void transfer(BankAccount rec,double amount) throws InsufficientBalanceException{
        if(rec == null){
            System.out.println("##### Receiver's account not found! #####");
            return;
        }
        if(this.accountNumber == rec.accountNumber){
            System.out.println("##### Can not transfer to same account! #####");
            return;
        }

        this.withdraw(amount);
        rec.deposit(amount);

        this.history.add("Transferred to " + rec.accountNumber + ": -" + amount);
        rec.history.add("Received from " + this.accountNumber + ": +" + amount);

        System.out.println("Successfully Transferred.......");
    }

    public boolean checkPass(String pass){
        return this.password.equals(pass);
    }

    public void changePass(String oldPass,String newPass){
        if(this.password.equals(oldPass)){
            this.password=newPass;
            System.out.println("Password Changed Successfully.......");
        }else{
            System.out.println("##### Incorrect Password! #####");
        }
    }

    // show history
    public void showHistory(){
        System.out.println("========= TRANSACTION HISTORY =========");

        if(history.isEmpty()){
            System.out.println("No transactions yet.");
        } else {
            for(String h : history){
                System.out.println(h);
            }
        }

        System.out.println("=======================================");
    }

    // CSV save
    public String fileToString(){
        String hist = String.join("|", history);
        return accountNumber + "," + name + "," + age + "," + password + "," + balance + "," + hist;
    }

    // CSV load
    public static BankAccount stringToFile(String line){
        String[] data=line.split(",",6);

        BankAccount acc = new BankAccount(
                Integer.parseInt(data[0]),
                data[1],
                Integer.parseInt(data[2]),
                data[3],
                Double.parseDouble(data[4])
        );

        if(data.length == 6 && !data[5].isEmpty()){
            String[] histArr = data[5].split("\\|");
            acc.history = new ArrayList<>(Arrays.asList(histArr));
        }

        return acc;
    }
}