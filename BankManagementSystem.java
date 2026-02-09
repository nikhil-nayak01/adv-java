
import java.sql.*;
import java.util.Scanner;

public class BankManagementSystem {

    static final String URL = "jdbc:mysql://localhost:3306/bank_management";
    static final String USER = "root";
    static final String PASSWORD = "2005";

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createAccount() {
        try {
            System.out.print("Enter Name: ");
            String name = sc.next();
            System.out.print("Enter Email: ");
            String email = sc.next();
            System.out.print("Enter Initial Balance: ");
            double balance = sc.nextDouble();

            String sql = "INSERT INTO accounts(name, email, balance) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setDouble(3, balance);

            ps.executeUpdate();
            System.out.println("Account Created Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewAccounts() {
        try {
            String sql = "SELECT * FROM accounts";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\nAccNo  Name  Email  Balance");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("account_no") + "   " +
                                rs.getString("name") + "   " +
                                rs.getString("email") + "   " +
                                rs.getDouble("balance")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deposit() {
        try {
            System.out.print("Enter Account No: ");
            int accNo = sc.nextInt();
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, accNo);

            ps.executeUpdate();
            System.out.println("Amount Deposited");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void withdraw() {
        try {
            System.out.print("Enter Account No: ");
            int accNo = sc.nextInt();
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            String checkSql = "SELECT balance FROM accounts WHERE account_no=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, accNo);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String sql = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setDouble(1, amount);
                    ps.setInt(2, accNo);
                    ps.executeUpdate();
                    System.out.println("Amount Withdrawn");
                } else {
                    System.out.println("Insufficient Balance");
                }
            } else {
                System.out.println("Account Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount() {
        try {
            System.out.print("Enter Account No: ");
            int accNo = sc.nextInt();

            String sql = "DELETE FROM accounts WHERE account_no=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, accNo);

            ps.executeUpdate();
            System.out.println("Account Deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connect();
        int choice;

        do {
            System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. View Accounts");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Delete Account");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: createAccount(); break;
                case 2: viewAccounts(); break;
                case 3: deposit(); break;
                case 4: withdraw(); break;
                case 5: deleteAccount(); break;
                case 6: System.out.println("Thank You!"); break;
                default: System.out.println("Invalid Choice");
            }
        } while (choice != 6);
    }
}