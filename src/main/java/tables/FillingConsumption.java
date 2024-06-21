package tables;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FillingConsumption {
    Connection connection;
    PreparedStatement statement;
    PreparedStatement statement1;
    String insertSQL = "INSERT OR REPLACE INTO Consumption (reactor, year, consumption) VALUES (?, ?, ?)";
    String query = "SELECT A.name, A.type, A.thermalCapacity, B.burnup, C.loadfactor, C.year FROM ReactorsFromPRIS AS A INNER JOIN ReactorsTypes AS B ON A.type = B.type INNER JOIN LoadFactor AS C ON A.name = C.reactor";
    ResultSet resultSet;

    public FillingConsumption() {
        fillConsumptionTable();
    }

    public void fillConsumptionTable() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:reactorsDB.db");
            statement = connection.prepareStatement(query);
            statement1 = connection.prepareStatement(insertSQL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                statement1.setString(1, resultSet.getString(1));
                statement1.setString(2, resultSet.getString(6));
                statement1.setDouble(3, calculateConsumption(resultSet.getInt(3), resultSet.getInt(4), resultSet.getDouble(5)));
                statement1.executeUpdate();
                System.out.println("Запись добавлена");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Double calculateConsumption(int thermalCapacity, int burnup, double loadFactor) {
        return (thermalCapacity*loadFactor/burnup);
    }
}
