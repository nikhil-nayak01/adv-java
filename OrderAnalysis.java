

import java.sql.*;

public class OrderAnalysis {

    static final String URL = "jdbc:mysql://localhost:3306/order_system";
    static final String USER = "root";
    static final String PASS = "2005";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            String sql = """
                SELECT c.customer_name,
                       COUNT(o.order_id) AS total_orders,
                       COUNT(DISTINCT o.product_id) AS unique_items,
                       CASE
                           WHEN COUNT(DISTINCT o.product_id) = 1 THEN 'Ordered One Item'
                           WHEN COUNT(DISTINCT o.product_id) > 1 THEN 'Ordered Multiple Items'
                       END AS order_type,
                       CASE
                           WHEN COUNT(o.product_id) > COUNT(DISTINCT o.product_id)
                           THEN 'Duplicate Item Ordered'
                           ELSE 'No Duplicate'
                       END AS duplicate_status
                FROM customer c
                JOIN orders o ON c.customer_id = o.customer_id
                GROUP BY c.customer_name
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("Name | Total Orders | Order Type | Duplicate");

            while (rs.next()) {
                System.out.println(
                        rs.getString("customer_name") + " | " +
                                rs.getInt("total_orders") + " | " +
                                rs.getString("order_type") + " | " +
                                rs.getString("duplicate_status")
                );
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}