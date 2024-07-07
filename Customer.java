package OOPS_SKILL;
import java.sql.*;
import java.util.Scanner;
public class Customer {
	private static String JDBC_URL="jdbc:mysql://localhost:3306/oops";
	private static final String USERNAME = "root";
    private static final String PASSWORD = "welcome";
    
    public static void main(String[]args)
    {
    	try(Scanner sc=new Scanner(System.in))
    			{
    				while(true)
    				{
    					System.out.println("\nChoose your operation");
    					System.out.println("1.Insert Data");
    					System.out.println("2.retrieve Data");
    					System.out.println("3.Update Data");
    					System.out.println("4.Delete Data");
    					System.out.println("5.Exit");
    					
    					int Choice=sc.nextInt();
        				sc.nextLine();
        				
        				switch(Choice)
        				{
        				case 1:
        				
        					insertData(sc);
        					break;
        				
        				case 2:
        				
        					retrieveData();
        					break;
        				
        				case 3:
        					
        					updateData(sc);
        					break;
        					
        				case 4:
        				
        					deleteData(sc);
        					break;
        				
        				case 5:
        					System.out.println("Exiting the program");
        					break;
        				default:
        					System.out.println("Invalid operation");
        				
        				}
    				}
    				
    				
    				
    			}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    private static void insertData(Scanner sc)
    {
    	try(Connection con=DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD)
    			{
    				System.out.println("\nenter the Customer Details");
    				System.out.println("enter date");
    				int date=sc.nextInt();
    				sc.nextLine();
    				System.out.println("MedID:");
    				String ID=sc.nextLine();
    				System.out.println("Customer Name:");
    				String Name=sc.nextLine();
    				System.out.println("Phone Number:");
    				int number=sc.nextInt();
    			}
    }
}
