import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistration {

    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password) VALUES (?, ?);";

    public static void registerUser(String username, String rawPassword) {
        String hashedPassword = Login.PasswordHasher.hashPassword(rawPassword);

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Replace with your database connection details
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Nart1207");

            pstmt = conn.prepareStatement(INSERT_USER_SQL);
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("User registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Example usage
        registerUser("newUser", "password123");
        System.out.println("Password hash: " + Login.PasswordHasher.hashPassword("password123"));
    }

    // ... Include your PasswordHasher and other classes here
}