import java.sql.*;
public class DataConsistency {
    private static final String url = "jdbc:mysql://localhost:3306/transaction";
    private static final String userName = "root";
    private static final String pwd = "Pdk@12398";
    public static void main(String args[]) throws Exception
    {
        Connection con = DriverManager.getConnection(url, userName, pwd);
        con.setAutoCommit(false);
        String debitQuery = "Update accone set balance = balance-? where accno = ?";
        String creditQuery = "Update acctwo set balance = balance+? where accno = ?";
        PreparedStatement pstmt = con.prepareStatement(debitQuery);
        PreparedStatement pstmt1 = con.prepareStatement(creditQuery);
        pstmt.setDouble(1, 5000);
        pstmt.setInt(2, 101);
        pstmt1.setDouble(1, 5000);
        pstmt1.setInt(2, 101);

        pstmt.executeUpdate();
        pstmt1.executeUpdate();

        if (isBalanceAvailable(con, 101, 50000)) {
            con.commit();
            System.out.println("Transaction Successful");
        }
        else
        {
            con.rollback();
            System.out.println("Insufficient Balance");
        }
        
    }

    static boolean isBalanceAvailable(Connection Con, int accno, double amount) throws Exception
    {
        String query = "Select balance from accone where accno = ?";
        PreparedStatement ps = Con.prepareStatement(query);
        ps.setInt(1, accno);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            double currentBalance = rs.getDouble("balance");
            if(amount>currentBalance)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
