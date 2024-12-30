import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.Connection;

public class BatchProcessingUsingStatement {
    private static final String url="jdbc:mysql://localhost:3306/mydb";
    private static final String userName="root";
    private static final String pwd="Pdk@12398";
    public static void main(String args[]) throws Exception
    {
        Connection con = DriverManager.getConnection(url,userName,pwd);
        Statement st = con.createStatement();
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("Enter Name : ");
            String name = sc.nextLine();
            System.out.println("Enter Age : ");
            int age = sc.nextInt();
            System.out.println("Enter Marks : ");
            double marks = sc.nextDouble();
            System.out.print("Do you want to insert more records (Y/N) : ");
            String choice=sc.next();
            String query = String.format("Insert into Students(name, age, marks) values('%s', %d, %f)", name, age, marks);
            st.addBatch(query);
            if(choice.toUpperCase().equals("N"))
            {
                break;
            }
        }
         
        int[] arr = st.executeBatch();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]==0) {
                System.out.println("Query "+i+" is not executed.");
            }
        }
        st.close();
        con.close();

    }
}
