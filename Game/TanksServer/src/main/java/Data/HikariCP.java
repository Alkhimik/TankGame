package Data;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCP {
    private HikariDataSource data = new HikariDataSource();
    HikariCP() {
        data.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        data.setUsername("postgres");
        data.setPassword("postgres");
    }

public Connection getConnection() throws SQLException {
        return data.getConnection();
 }

}
