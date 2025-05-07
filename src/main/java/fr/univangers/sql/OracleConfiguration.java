package fr.univangers.sql;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ConfigurationProperties("mysql")  // Change "oracle" en "mysql" si tu utilises MySQL
public class OracleConfiguration {

    private String username;
    private String password;
    private String urlSympa;

    // Getters et setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrlSympa(String urlSympa) {
        this.urlSympa = urlSympa;
    }

    @Bean
    public DataSource dataSourceSympa() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(urlSympa);
        return dataSource;
    }
}
