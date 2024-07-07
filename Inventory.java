package OOPS_SKILL;
import java.sql.*;
import java.util.Scanner;
public class Inventory {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/oops";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "welcome";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nChoose an operation:");
                System.out.println("1. Insert Medicine");
                System.out.println("2. Retrieve Medicines");
                System.out.println("3. Update Medicine");
                System.out.println("4. Delete Medicine");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        insertMedicine(scanner);
                        break;
                    case 2:
                        retrieveMedicines();
                        break;
                    case 3:
                        updateMedicine(scanner);
                        break;
                    case 4:
                        deleteMedicine(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting program.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertMedicine(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("\nEnter Medicine Information:");

            System.out.print("Medicine ID: ");
            int mid = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Medicine Name: ");
            String medicineName = scanner.nextLine();

            System.out.print("Type of Medicine: ");
            String typeOfMedicine = scanner.nextLine();
            
            System.out.print("Quantity");
            long quantity=scanner.nextInt();
            
            System.out.print("Price: ");
            double price = scanner.nextDouble();

            System.out.print("Manufacture Date (YYYY-MM-DD): ");
            String manufactureDate = scanner.next();

            System.out.print("Expiry Date (YYYY-MM-DD): ");
            String expiryDate = scanner.next();

            System.out.print("Row Number: ");
            int rowNo = scanner.nextInt();

            System.out.print("Column Number: ");
            int columnNo = scanner.nextInt();

            
            insertMedicineData(connection, mid, medicineName, typeOfMedicine,quantity, price, manufactureDate, expiryDate, rowNo, columnNo);
            System.out.println("Medicine data inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertMedicineData(Connection connection, int mid, String medicineName, String typeOfMedicine, long quantity,
            double price, String manufactureDate, String expiryDate, int rowNo, int columnNo) throws SQLException {
    	String insertSQL = "INSERT INTO inventory (mid, medicine_name, type_of_medicine, quantity, price, manufacture_date, expiry_date, row_no, column_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    	try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
    		preparedStatement.setInt(1, mid);
    		preparedStatement.setString(2, medicineName);
    		preparedStatement.setString(3, typeOfMedicine);
    		preparedStatement.setLong(4, quantity);
    		preparedStatement.setDouble(5, price);
    		preparedStatement.setString(6, manufactureDate);
    		preparedStatement.setString(7, expiryDate);
    		preparedStatement.setInt(8, rowNo);
    		preparedStatement.setInt(9, columnNo);

    		preparedStatement.executeUpdate();
    	}
    }


    private static void retrieveMedicines() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String selectSQL = "SELECT * FROM inventory";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSQL)) {

                System.out.println("\nMedicines Information:");
                System.out.println("MedID\tMedicineName\tTypeOfMedicine\tquantity\tPrice\tManufactureDate\tExpiryDate\tRowNo\tColumnNo");

                while (resultSet.next()) {
                    int mid = resultSet.getInt("mid");
                    String medicineName = resultSet.getString("medicine_name");
                    String typeOfMedicine = resultSet.getString("type_of_medicine");
                    long quantity = resultSet.getLong("quantity");
                    double price = resultSet.getDouble("price");
                    String manufactureDate = resultSet.getString("manufacture_date");
                    String expiryDate = resultSet.getString("expiry_date");
                    int rowNo = resultSet.getInt("row_no");
                    int columnNo = resultSet.getInt("column_no");

                    System.out.println(mid + "\t" + medicineName + "\t\t" + typeOfMedicine + "\t\t" +quantity+"\t\t"+
                            price + "\t" + manufactureDate + "\t" + expiryDate + "\t" + rowNo + "\t" + columnNo);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateMedicine(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.print("\nEnter Medicine ID to update: ");
            int mid = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Check if the medicine exists
            if (medicineExists(connection, mid)) {
                System.out.println("Enter new information for the medicine:");
                insertMedicine(scanner);
                System.out.println("Medicine data updated successfully.");
            } else {
                System.out.println("Medicine with ID " + mid + " does not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteMedicine(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.print("\nEnter Medicine ID to delete: ");
            int mid = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Check if the medicine exists
            if (medicineExists(connection, mid)) {
                String deleteSQL = "DELETE FROM inventory WHERE med_id = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                    preparedStatement.setInt(1, mid);
                    preparedStatement.executeUpdate();
                    System.out.println("Medicine with ID " + mid + " deleted successfully.");
                }

            } else {
                System.out.println("Medicine with ID " + mid + " does not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean medicineExists(Connection connection, int mid) throws SQLException {
        String checkSQL = "SELECT * FROM inventory WHERE mid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkSQL)) {
            preparedStatement.setInt(1, mid);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
    
    
}
