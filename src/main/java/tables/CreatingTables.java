package tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreatingTables {
    Connection connection;
    String createTableSQL;
    PreparedStatement statement;

    {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:reactorsDB.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CreatingTables() {
        creatingReactorTypes();
        creatingRegions();
        creatingCountries();
        creatingReactorsPris();
        creatingLoadFactor();
        creatingConsumptionTable();
        creatingAggrCountryTable();
        creatingAggrCompanyTable();
        creatingAggrRegionTable();
    }

    public void creatingReactorTypes() {
        createTableSQL = "CREATE TABLE IF NOT EXISTS ReactorsTypes (type TEXT PRIMARY KEY, burnup INT)";
        try {
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();  // Выполняем создание таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creatingLoadFactor() {
        createTableSQL = "CREATE TABLE IF NOT EXISTS LoadFactor (\n"
                + " reactor TEXT,\n"
                + " year INTEGER,\n"
                + " loadfactor REAL,\n"
                + "FOREIGN KEY (reactor) REFERENCES ReactorsFromPRIS(name)\n"
                + ");";
        try {
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();  // Выполняем создание таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creatingRegions() {
        createTableSQL = "CREATE TABLE IF NOT EXISTS Regions (region TEXT PRIMARY KEY)";
        try {
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();  // Выполняем создание таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creatingCountries() {
        createTableSQL = "CREATE TABLE IF NOT EXISTS Countries (\n"
                + "    country TEXT PRIMARY KEY,\n"
                + "    region TEXT,\n"
                + "FOREIGN KEY (region) REFERENCES Regions(region)\n"
                + ");";
        try {
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();  // Выполняем создание таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creatingReactorsPris() {
        createTableSQL = "CREATE TABLE IF NOT EXISTS ReactorsFromPRIS (" +
                "name TEXT PRIMARY KEY, " +
                "country TEXT, " +
                "status TEXT, " +
                "type TEXT, " +
                "owner TEXT, " +
                "operator TEXT, " +
                "thermalCapacity INTEGER, " +
                "firstGridConnection INTEGER, " +
                "loadFactor REAL, " +
                "suspendedDate INTEGER, " +
                "permanentShutdownDate INTEGER, " +
                "FOREIGN KEY (type) REFERENCES ReactorsTypes(type))";
        try {
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();  // Выполняем создание таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creatingConsumptionTable() {
        createTableSQL = "CREATE TABLE IF NOT EXISTS Consumption (\n"
                + " reactor TEXT,\n"
                + " year INTEGER,\n"
                + " consumption REAL,\n"
                + "FOREIGN KEY (reactor) REFERENCES ReactorsFromPRIS(name)\n"
                + ");";
        try {
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();  // Выполняем создание таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creatingAggrCountryTable() {
        createTableSQL = "CREATE TABLE IF NOT EXISTS ConsumptionCountry (\n"
                + " country TEXT,\n"
                + " consumption REAL,\n"
                + " year INTEGER,\n"
                + "FOREIGN KEY (country) REFERENCES Countries(country)\n"
                + ");";
        try {
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();  // Выполняем создание таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creatingAggrCompanyTable() {
        createTableSQL = "CREATE TABLE IF NOT EXISTS ConsumptionCompany (\n"
                + " company TEXT,\n"
                + " consumption REAL,\n"
                + " year INTEGER,\n"
                + "FOREIGN KEY (company) REFERENCES ReactorsFromPRIS(operator)\n"
                + ");";
        try {
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();  // Выполняем создание таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creatingAggrRegionTable() {
        createTableSQL = "CREATE TABLE IF NOT EXISTS ConsumptionRegion (\n"
                + " region TEXT,\n"
                + " consumption REAL,\n"
                + " year INTEGER,\n"
                + "FOREIGN KEY (region) REFERENCES Regions(region)\n"
                + ");";
        try {
            statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();  // Выполняем создание таблицы
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
