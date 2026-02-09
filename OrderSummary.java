import java.sql.*;

public class OrderSummary {

    static final String URL = "jdbc:mysql://localhost:3306/ytrewq";
    static final String USER = "root";
    static final String PASS = "2005";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            CallableStatement cs = conn.prepareCall("{call order_summary()}");
            boolean hasResults = cs.execute();

            int resultSetNumber = 1;

            while (hasResults) {
                ResultSet rs = cs.getResultSet();
                System.out.println("\nResult Set " + resultSetNumber);

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rsmd.getColumnName(i) + ": " + rs.getString(i) + "  ");
                    }
                    System.out.println();
                }

                resultSetNumber++;
                hasResults = cs.getMoreResults();
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
