package cz.muni.fi.travelAgency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * In memory database provider
 *
 * @author Filip Cekovsky
 */
@Configuration
public class InMemoryDB {
    /**
     * Provides in memory database
     * @return in memory database reference
     */
    @Bean
    public DataSource db(){
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.DERBY).build();
    }
}
