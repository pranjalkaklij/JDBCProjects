import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BatchProcessingUsingPreparedStmnt {
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String userName = "root";
    private static final String pwd = "Pdk@12398";

    public static void main(String args[]) throws Exception
    {
        Connection con = DriverManager.getConnection(url, userName, pwd);
        String query = "Insert into Students(name,age,marks) values(?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("Enter Name : ");
            String name = sc.nextLine();
            System.out.println("Enter Age: ");
            int age = sc.nextInt();
            System.out.println("Enter Marks : ");
            double marks = sc.nextDouble();
            System.out.println("Do ypu want to inter more data (Y/N) : ");
            String choice = sc.next();
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setDouble(3, marks);
            pstmt.addBatch();
            if(choice.toUpperCase().equals("N"))
            {
                break;
            }
        }

        int[] arr = pstmt.executeBatch();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]==0) {
                System.out.println("Query "+i+" is not executed!");
            }
        }
    }
}
