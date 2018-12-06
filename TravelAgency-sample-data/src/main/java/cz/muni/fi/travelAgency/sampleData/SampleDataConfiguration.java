package cz.muni.fi.travelAgency.sampleData;

import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Takes Service configuration and loads sample data
 *
 * @author Filip Cekovsky
 */
@Configuration
@Import(ServiceConfiguration.class)
public class SampleDataConfiguration {
    final static Logger logger = LoggerFactory.getLogger(SampleDataConfiguration.class);

    @Autowired
    DataLoadingFacade sampleDataLoader;

    @PostConstruct
    public void loadData() throws IOException {
        logger.debug("Data loading... ");
        sampleDataLoader.loadData();
    }
}
