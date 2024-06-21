package tables;

import reactor.YamlReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FillingReactorTypes {
    YamlReader yamlReader;
    Connection connection;
    Map<String, Double> reactorBurnupMap = yamlReader.getReactorBurnupMap();

    String insertSQL = "INSERT OR REPLACE INTO ReactorsTypes (type, burnup) VALUES (?, ?)";

    PreparedStatement statement;

    public FillingReactorTypes(String path) {
        yamlReader = new YamlReader(path);
        fillTable();
    }

    public void fillTable(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:reactorsDB.db");
            statement = connection.prepareStatement(insertSQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry<String, Double> entry : reactorBurnupMap.entrySet()) {
            try {
                statement.setString(1, entry.getKey());
                statement.setDouble(2, entry.getValue());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            statement.setString(1, "LWGR");
            statement.setDouble(2, 25);
            statement.executeUpdate();
            statement.setString(1, "GCR");
            statement.setDouble(2, 22);
            statement.executeUpdate();
            statement.setString(1, "HWDCR");
            statement.setDouble(2, 12);
            statement.executeUpdate();
            statement.setString(1, "HTGR");
            statement.setDouble(2, 100);
            statement.executeUpdate();
            statement.setString(1, "FBR");
            statement.setDouble(2, 150);
            statement.executeUpdate();
            statement.setString(1, "SGHWR");
            statement.setDouble(2, 8);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
