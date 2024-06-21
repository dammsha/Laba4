package reactor;

import java.sql.*;

public class Aggregating {
    Connection connection;
    String aggrCountry = "SELECT B.country, A.year, SUM(A.consumption) FROM Consumption AS A INNER JOIN ReactorsFromPRIS AS B ON A.reactor = B.name GROUP BY B.country, A.year";
    String aggrCompany = "SELECT B.operator, A.year, SUM(A.consumption) FROM Consumption AS A INNER JOIN ReactorsFromPRIS AS B ON A.reactor = B.name GROUP BY B.operator, A.year";
    String aggrRegion = "SELECT C.region, A.year, SUM(A.consumption) FROM Consumption AS A INNER JOIN ReactorsFromPRIS AS B ON A.reactor = B.name INNER JOIN Countries AS C ON B.country = C.country GROUP BY C.region, A.year";
    String insertSQLCountry = "INSERT OR REPLACE INTO ConsumptionCountry (country, year, consumption) VALUES (?, ?, ?)";
    String insertSQLCompany = "INSERT OR REPLACE INTO ConsumptionCompany (company, year, consumption) VALUES (?, ?, ?)";
    String insertSQLRegion = "INSERT OR REPLACE INTO ConsumptionRegion (region, year, consumption) VALUES (?, ?, ?)";

    PreparedStatement statementAggrCountry;
    PreparedStatement statementInsertCountry;
    PreparedStatement statementAggrCompany;
    PreparedStatement statementInsertCompany;
    PreparedStatement statementAggrRegion;
    PreparedStatement statementInsertRegion;

    ResultSet resultSetCountry;
    ResultSet resultSetCompany;
    ResultSet resultSetRegion;

    public Aggregating() throws SQLException {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:reactorsDB.db");
            statementAggrCountry = connection.prepareStatement(aggrCountry);
            statementInsertCountry = connection.prepareStatement(insertSQLCountry);
            statementAggrCompany = connection.prepareStatement(aggrCompany);
            statementInsertCompany = connection.prepareStatement(insertSQLCompany);
            statementAggrRegion = connection.prepareStatement(aggrRegion);
            statementInsertRegion = connection.prepareStatement(insertSQLRegion);
            resultSetCountry = statementAggrCountry.executeQuery();
            resultSetCompany = statementAggrCompany.executeQuery();
            resultSetRegion = statementAggrRegion.executeQuery();

            while (resultSetCountry.next()) {
                statementInsertCountry.setString(1, resultSetCountry.getString(1));
                statementInsertCountry.setString(2, resultSetCountry.getString(2));
                statementInsertCountry.setDouble(3, resultSetCountry.getDouble(3));
                statementInsertCountry.executeUpdate();
                System.out.println("Запись добавлена");
            }
            while (resultSetCompany.next()) {
                statementInsertCompany.setString(1, resultSetCompany.getString(1));
                statementInsertCompany.setString(2, resultSetCompany.getString(2));
                statementInsertCompany.setDouble(3, resultSetCompany.getDouble(3));
                statementInsertCompany.executeUpdate();
                System.out.println("Запись добавлена 1");
            }
            while (resultSetRegion.next()) {
                statementInsertRegion.setString(1, resultSetRegion.getString(1));
                statementInsertRegion.setString(2, resultSetRegion.getString(2));
                statementInsertRegion.setDouble(3, resultSetRegion.getDouble(3));
                statementInsertRegion.executeUpdate();
                System.out.println("Запись добавлена 2");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statementAggrCountry != null) statementAggrCountry.close();
                if (statementAggrCompany != null) statementAggrCompany.close();
                if (statementAggrRegion != null) statementAggrRegion.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
