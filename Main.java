import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                if (connection != null) {
                    System.out.println("Connected to the database!");

                    String sql = "SELECT * " +
                            "FROM gift_recipients " +
                            "LEFT OUTER JOIN gifts ON gift_recipients.gift_id = gifts.gift_id";

                    try (Statement statement = connection.createStatement();
                         ResultSet resultSet = statement.executeQuery(sql)) {

                        while (resultSet.next()) {
                            int recipientId = resultSet.getInt("recipient_id");
                            String name = resultSet.getString("recipient_name");
                            String giftName = resultSet.getString("gift_name");

                            System.out.println("Recipient ID: " + recipientId + ", Name: " + name + ", Gift: " + giftName);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Failed to make a connection!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
