import java.sql.*;
import java.util.Scanner;

public class Quizjdbc {

    static Connection getCon() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/quizdb",
                "root",
                "2005" 
        );
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        Connection con = getCon();

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();

        System.out.print("Enter Password: ");
        String pass = sc.next();

        PreparedStatement ps =
                con.prepareStatement("SELECT * FROM student WHERE roll_no=? AND password=?");

        ps.setInt(1, roll);
        ps.setString(2, pass);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            System.out.println("Invalid Login ❌");
            return;
        }

        Statement st = con.createStatement();
        ResultSet qs = st.executeQuery("SELECT * FROM questions");

        int marks = 0;

        while (qs.next()) {
            System.out.println("\n" + qs.getString("question"));
            System.out.println("1. " + qs.getString("option1"));
            System.out.println("2. " + qs.getString("option2"));
            System.out.println("3. " + qs.getString("option3"));
            System.out.println("4. " + qs.getString("option4"));

            System.out.print("Enter Answer: ");
            int ans = sc.nextInt();

            if (ans == qs.getInt("correct_option")) {
                marks++;
            }
        }

        String result = (marks >= 3) ? "PASS" : "FAIL";

        PreparedStatement ps2 =
                con.prepareStatement("INSERT INTO result VALUES (?, ?, ?)");

        ps2.setInt(1, roll);
        ps2.setInt(2, marks);
        ps2.setString(3, result);
        ps2.executeUpdate();

        System.out.println("\n✅ MARKS: " + marks);
        System.out.println("🎓 RESULT: " + result);
    }
}