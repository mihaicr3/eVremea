package weatherPersistentDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CityInsertIntoDatabase {



    public static void insertInDatabase(String city,float temp,float hum) throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/persistentweatherdb",
                "root", ""
        );
        try (PreparedStatement statement = connection.prepareStatement("""
        INSERT INTO weathercity(city, temperature,humidity,timestamp)
        VALUES (?, ?,?,?)
      """)) {
            statement.setString(1,city);
            statement.setFloat(2,temp);
            statement.setFloat(3,hum);
            statement.setFloat(4,System.currentTimeMillis());
            int rowsInserted = statement.executeUpdate();
        }

    }
}
