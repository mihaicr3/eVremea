package weatherPersistentDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CoordinatesInsertIntoDatabase {

    public static void insertInDatabase(String lat,String lon,float temp,float hum) throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/persistentweatherdb",
                "root", ""
        );
        try (PreparedStatement statement = connection.prepareStatement("""
        INSERT INTO weathercoordinates(latitude,longitude, temperature,humidity,timestamp)
        VALUES (?,?, ?,?,?)
      """)) {
            statement.setString(1,lat);
            statement.setString(2,lon);
            statement.setFloat(3,temp);
            statement.setFloat(4,hum);
            statement.setFloat(5,System.currentTimeMillis());
            int rowsInserted = statement.executeUpdate();
        }

    }
}
