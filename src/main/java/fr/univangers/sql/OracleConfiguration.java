package fr.univangers.sql;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ConfigurationProperties("oracle")
//@PropertySources({ @PropertySource("classpath:application.properties") })
public class OracleConfiguration {
    private String username;
    private String password;
    private String urlLocang;

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUrlLocang(String urlLocang) {
        this.urlLocang = urlLocang;
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(urlLocang);
        return dataSource;
    }
}
