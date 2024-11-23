import java.sql.*;
import java.util.Scanner;

public class StatementApp {
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String userName = "root";
    private static final String pwd = "Pdk@12398";

    public static void showRecord(Connection conn, Statement st) throws Exception
    {
        int record = 0;
        String showData = "Select * from Students";
        ResultSet rs = st.executeQuery(showData);
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
    public static void insertRecord(Connection conn, Statement st) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Student Name :");
        String name = sc.nextLine();
        System.out.println("Enter Student Age :");
        int age = sc.nextInt();
        System.out.println("Enter Student Marks :");
        double marks = sc.nextDouble();

        String insertQuery = String.format("Insert into Students(name, age, marks) values('%s', %d, %f)", name, age, marks);
        int recordCount = st.executeUpdate(insertQuery);
        if (recordCount>0) {
            System.out.println("Yeah.. Record inserted!");
        }
        else
        {
            System.out.println("OOppps.. Not inserted!");
        }

    }
    public static void updateRecord(Connection conn, Statement st) throws Exception
    {
        System.out.println("Alert!  you can only modify the marks. And make sure you have remeber Student Id");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Student Id whoes marks you want change :");
        int id = sc.nextInt();
        System.out.println("Enter Correct Marks : ");
        double marks = sc.nextDouble();

        String updateMarks = String.format("Update Students set Marks = %f where Id = %d", marks, id);
        int recordCount = st.executeUpdate(updateMarks);
        if(recordCount>0)
        {
            System.out.println("Wohhhhh.. Marks Updated.");
        }
        else
        {
            System.out.println("Not Updated!.. Try next time.");
        }


    }
    public static void deleteRecord(Connection conn, Statement st) throws Exception
    {
        System.out.println("Alert! You can Delete Record only if you know Student Id..");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Student Id to Delete Record : ");
        int id = sc.nextInt();

        String deleteRecord = String.format("Delete from Students where Id=%d", id);
        int recordCount = st.executeUpdate(deleteRecord);
        if(recordCount>0)
        {
            System.out.println("Sad to say.. You Deleted one Record :-( ");
        }
        else
        {
            System.out.println("Hmmm... seems like you dont want to delete it.. please enter correct details to delete it. ");
        }

    }
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, pwd);
        Statement stmt = con.createStatement();
        int operation = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println(
                "Please Select which operation you want to do : If you want see data then Enter 1, if you want to insert data then Enter 2, If you want to update data then Enter 3, If you want to delete a record Enter 4. Just press any key if you dont want to perform any operation.");
        operation = sc.nextInt();
        try {
            
            switch (operation) {
                case 1:
                showRecord(con, stmt);
                    break;
                case 2:
                insertRecord(con, stmt);
                    break;
                case 3:
                updateRecord(con, stmt);
                    break;
                case 4:
                deleteRecord(con, stmt);
                    break;
            
                default: System.out.println("You entered wrong Input.. so please re-run the Application to enter correct input.");
                    break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
