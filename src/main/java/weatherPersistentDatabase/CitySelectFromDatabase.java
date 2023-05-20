package weatherPersistentDatabase;

import model.response.CurrentWeather;

import java.sql.*;

public class CitySelectFromDatabase {


    public static CurrentWeather selectWeather(String city, long timestampToRemove) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/persistentweatherdb",
                "root", ""
        );
        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT temperature, humidity
                    FROM weathercity
                    where city=?
                    order by abs(timestamp-?)
                    limit 1
                    
                """)) {
            statement.setString(1, city);
            statement.setLong(2, timestampToRemove);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            float val1 = resultSet.getFloat(1);
            float val2 = resultSet.getFloat(2);
            // ... use val1 and val2 ...
            return new CurrentWeather(val1, val2);

        }

    }


}

