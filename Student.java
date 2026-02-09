import java.sql.*;
import java.util.*;

public class Student {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/student_management";
        String user = "root";
        String password = "2005";

        Scanner sc = new Scanner(System.in);

        int id, age;
        String name;
        char choice = 'y';
        char choice2 = 'y';

        //for storing multiple inputs
        List<Integer> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Integer> ages = new ArrayList<>();
        System.out.print("Do you want to enter a student record? (y/n): ");
        choice2 = sc.next().charAt(0);
        if(choice2=='y' || choice2 == 'Y'){
            choice = 'y';
            while (choice == 'y' || choice == 'Y') {

                System.out.print("Enter roll no: ");
                id = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter name: ");
                name = sc.nextLine();

                System.out.print("Enter age: ");
                age = sc.nextInt();

                // store inputs
                ids.add(id);
                names.add(name);
                ages.add(age);

                System.out.print("Do you want to enter another record? (y/n): ");
                choice = sc.next().charAt(0);  //Takes the first character of that token.
            }

        }



        try {
            Connection con = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO student (id,stdname,age) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            for(int i = 0;i<ids.size();i++)
            {
                ps.setInt(1, ids.get(i));
                ps.setString(2, names.get(i));
                ps.setInt(3, ages.get(i));
                ps.executeUpdate();
            }


            String sql1 = "select * from student";
            PreparedStatement ps1 = con.prepareStatement(sql1);

            ResultSet rs = ps1.executeQuery();

            System.out.println("----TABLE DATA----");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " " +
                                rs.getString("stdname") + " " +
                                rs.getInt("age")
                );

            }
            sc.nextLine();
            System.out.print("Do you want to update a student record? (y/n): ");
            choice2 = sc.next().charAt(0);
            choice = 'y';
            if(choice2=='y' || choice2 == 'Y') {
                choice = 'y';
                while (choice == 'y' || choice == 'Y'){
                    System.out.print("Enter roll no, to make changes to that student details: ");
                    int Fid = sc.nextInt();
                    sc.nextLine();



                    System.out.print("Enter UPDATED name: ");
                    String Uname = sc.nextLine();

                    System.out.print("Enter UPDATED age: ");
                    int Uage = sc.nextInt();

                    String sql2 = "UPDATE student SET stdname = ?,age = ? WHERE id = ?";
                    PreparedStatement ps2 = con.prepareStatement(sql2);

                    ps2.setString(1, Uname);
                    ps2.setInt(2, Uage);
                    ps2.setInt(3, Fid);
                    ps2.executeUpdate();

                    System.out.print("Do you want to update another record? (y/n): ");
                    choice = sc.next().charAt(0);
                }
            }



            String sql3 = "select * from student";
            PreparedStatement ps4 = con.prepareStatement(sql1);

            ResultSet rs1 = ps4.executeQuery();

            System.out.println("----TABLE DATA----");

            while (rs1.next()) {
                System.out.println(
                        rs1.getInt("id") + " " +
                                rs1.getString("stdname") + " " +
                                rs1.getInt("age")
                );

            }
            sc.nextLine();
            System.out.print("Do you want to delete a student record? (y/n): ");
            choice2 = sc.next().charAt(0);
            choice = 'y';
            if(choice2=='y' || choice2 == 'Y') {
                choice = 'y';
                while (choice == 'y' || choice == 'Y'){
                    System.out.print("Enter id to delete the student details: ");
                    int Did = sc.nextInt();

                    String sql5 = "DELETE FROM student WHERE id = ?";
                    PreparedStatement ps5 = con.prepareStatement(sql5);
                    ps5.setInt(1, Did);
                    ps5.executeUpdate();

                    System.out.print("Do you want to delete another record? (y/n): ");
                    choice = sc.next().charAt(0);

                }
            }


            String sql6 = "select * from student";
            PreparedStatement ps6 = con.prepareStatement(sql6);

            ResultSet rs6 = ps6.executeQuery();

            System.out.println("----TABLE DATA----");

            while (rs6.next()) {
                System.out.println(
                        rs6.getInt("id") + " " +
                                rs6.getString("stdname") + " " +
                                rs6.getInt("age")
                );

            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
