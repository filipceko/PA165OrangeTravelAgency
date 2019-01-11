package cz.muni.fi.travelAgency.sampleData;

import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * Takes Service configuration and loads sample data
 *
 * @author Filip Cekovsky
 */
@Configuration
@Import(ServiceConfiguration.class)
public class SampleDataConfiguration {

    /**
     * Logger for this class
     */
    private final static Logger logger = LoggerFactory.getLogger(SampleDataConfiguration.class);

    /**
     * Sample data loading facade
     */
    @Autowired
    DataLoadingFacade sampleDataLoader;

    /**
     * Loads data
     */
    @PostConstruct
    public void loadData() {
        logger.debug("Data loading... ");
        sampleDataLoader.loadData();
    }
}
