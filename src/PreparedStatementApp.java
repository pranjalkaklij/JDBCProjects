import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;

public class PreparedStatementApp {
    private static final String url="jdbc:mysql://localhost:3306/mydb";
    private static final String username ="root";
    private static final String pwd="Pdk@12398";

    public static void showRecord(Connection conn) throws Exception
    {
        int record = 0;
        String showData = "Select * from Students";
        PreparedStatement pstmt = conn.prepareStatement(showData);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            record++;
            int id = rs.getInt("Id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            double marks = rs.getDouble("marks");

            System.out.println("RECORD : " + record);
            System.out.println("ID : " + id);
            System.out.println("Name : " + name);
            System.out.println("Age : " + age);
            System.out.println("Makrs : " + marks);
            System.out.println("----------------------------------------");
        }

    }
    public static void insertRecord(Connection conn) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Student Name :");
        String name = sc.nextLine();
        System.out.println("Enter Student Age :");
        int age = sc.nextInt();
        System.out.println("Enter Student Marks :");
        double marks = sc.nextDouble();
        String insertData = "Insert into Students(name,age,marks) values(?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(insertData);
        pstmt.setString(1, name);
        pstmt.setInt(2, age);
        pstmt.setDouble(3, marks);
        System.out.println("Print query - "+ pstmt);

        int recordCount = pstmt.executeUpdate();
        if (recordCount>0) {
            System.out.println("Yeah.. Record inserted!");
        }
        else
        {
            System.out.println("OOppps.. Not inserted!");
        } 
    }
    public static void updateRecord(Connection conn) throws Exception 
    {
        System.out.println("Alert!  you can only modify the marks. And make sure you have remeber Student Id");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Student Id whoes marks you want change :");
        int id = sc.nextInt();
        System.out.println("Enter Correct Marks : ");
        double marks = sc.nextDouble();

        String updateMarks = String.format("Update Students set Marks = ? where Id = ?", marks, id);
        PreparedStatement pstmt = conn.prepareStatement(updateMarks);
        pstmt.setDouble(1, marks);
        pstmt.setInt(2, id);

        int recordCount = pstmt.executeUpdate();
        if(recordCount>0)
        {
            System.out.println("Record updted!..");
        }
        else
        {
            System.out.println("You did a mistake!. please try again.");
        }
    }
    public static void deleteRecord(Connection conn) throws Exception
    {
        System.out.println("You Should know the student id if you want to delete the record.");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Student Id to delete the record.");
        int id = sc.nextInt();
        String deleteRecord = "Delete From Students where Id = ?";
        PreparedStatement pstmt = conn.prepareStatement(deleteRecord);
        pstmt.setInt(1, id);
        int recordCount = pstmt.executeUpdate();
        if(recordCount>0)
        {
            System.out.println("Record Deleted Successfully.");
        }
        else
        {
            System.out.println("Record Failed.");
        }
        
    }
    public static void main(String args[]) throws Exception{
        Connection con = DriverManager.getConnection(url, username, pwd);

        int operation =0;
        Scanner sc = new Scanner(System.in);
        System.out.println(
                "Please Select which operation you want to do : If you want see data then Enter 1, if you want to insert data then Enter 2, If you want to update data then Enter 3, If you want to delete a record Enter 4. Just press any key if you dont want to perform any operation.");
        operation = sc.nextInt();
        try {
            
                switch (operation) {
                    case 1:
                    showRecord(con);
                        break;
                    case 2:
                    insertRecord(con);
                        break;
                    case 3:
                    updateRecord(con);
                        break;
                    case 4:
                    deleteRecord(con);
                        break;
                
                    default: System.out.println("You entered wrong Input.. so please re-run the Application to enter correct input.");
                        break;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } 

    }
}
