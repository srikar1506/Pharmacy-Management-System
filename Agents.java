package OOPS_SKILL;

import java.sql.*;
import java.util.Scanner;

public class Agents {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/oops";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "welcome";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Insert Agent");
                System.out.println("2. Retrieve Agents");
                System.out.println("3. Update Agent");
                System.out.println("4. Delete Agent");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        insertAgent(scanner);
                        break;
                    case 2:
                        retrieveAgents();
                        break;
                    case 3:
                        updateAgent(scanner);
                        break;
                    case 4:
                        deleteAgent(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting the program !");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please choose a valid option.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertAgent(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Enter Agent Information:");
            System.out.print("Agent ID: ");
            int agentId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.print("First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Phone Number: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Gender: ");
            String gender = scanner.nextLine();

            System.out.print("Experience: ");
            int experience = scanner.nextInt();

            System.out.print("Age: ");
            int age = scanner.nextInt();

            insertAgentData(connection, agentId, firstName, lastName, phoneNumber, gender, experience, age);
            System.out.println("Agent data inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertAgentData(Connection connection, int agentId, String firstName, String lastName,
                                        String phoneNumber, String gender, int experience, int age) throws SQLException {
        String insertSQL = "INSERT INTO agents (agentID, Firstname, Lastname, phoneNumber, gender, experience, age) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, agentId);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, gender);
            preparedStatement.setInt(6, experience);
            preparedStatement.setInt(7, age);

            preparedStatement.executeUpdate();
        }
    }


    private static void retrieveAgents() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM agents")) {

            System.out.println("AgentID\tFirstname\tLastname\tPhoneNumber\tGender\tExperience\tAge");

            while (resultSet.next()) {
                int agentId = resultSet.getInt("agentid");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String phoneNumber = resultSet.getString("phonenumber");
                String gender = resultSet.getString("gender");
                int experience = resultSet.getInt("experience");
                int age = resultSet.getInt("age");

                System.out.println(agentId + "\t" + firstName + "\t" + lastName + "\t" +
                        phoneNumber + "\t" + gender + "\t" + experience + "\t" + age);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void updateAgent(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.print("Enter Agent ID to update: ");
            int agentId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.println("Enter updated information:");

            System.out.print("First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Phone Number: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Gender: ");
            String gender = scanner.nextLine();

            System.out.print("Experience: ");
            int experience = scanner.nextInt();

            System.out.print("Age: ");
            int age = scanner.nextInt();

            updateAgentData(connection, agentId, firstName, lastName, phoneNumber, gender, experience, age);
            System.out.println("Agent data updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateAgentData(Connection connection, int agentId, String firstName, String lastName,
                                        String phoneNumber, String gender, int experience, int age) throws SQLException {
        String updateSQL = "UPDATE agents SET Firstname=?, Lastname=?, phoneNumber=?, gender=?, experience=?, age=? WHERE agentID=?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, gender);
            preparedStatement.setInt(5, experience);
            preparedStatement.setInt(6, age);
            preparedStatement.setInt(7, agentId);

            preparedStatement.executeUpdate();
        }
    }

    private static void deleteAgent(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.print("Enter Agent ID to delete: ");
            int agentId = scanner.nextInt();

            deleteAgentData(connection, agentId);
            System.out.println("Agent data deleted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAgentData(Connection connection, int agentId) throws SQLException {
        String deleteSQL = "DELETE FROM agents WHERE agentID=?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, agentId);

            preparedStatement.executeUpdate();
        }
    }
}
