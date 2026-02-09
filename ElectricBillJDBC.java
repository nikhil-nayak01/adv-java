import java.sql.*;
import java.util.Scanner;

public class ElectricBillJDBC {

    static final String URL = "jdbc:mysql://localhost:3306/electricity_db";
    static final String USER = "root";
    static final String PASS = "2005";

    public static double calculateBill(int units) {
        double bill = 0;

        if (units <= 100) {
            bill = units * 5;
        } else if (units <= 200) {
            bill = (100 * 5) + ((units - 100) * 7);
        } else {
            bill = (100 * 5) + (100 * 7) + ((units - 200) * 10);
        }

        return bill;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Consumer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Units Consumed: ");
        int units = sc.nextInt();

        double amount = calculateBill(units);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            String sql = "INSERT INTO electric_bill (consumer_name, units_consumed, bill_amount) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, units);
            ps.setDouble(3, amount);

            ps.executeUpdate();

            System.out.println("\n--- Electric Bill Generated ---");
            System.out.println("Name: " + name);
            System.out.println("Units: " + units);
            System.out.println("Bill Amount: ₹" + amount);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}