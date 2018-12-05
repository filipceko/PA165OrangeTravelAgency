package cz.muni.fi.travelAgency.sampleData;

import java.io.IOException;

/**
 * Interface for data loading service
 *
 * @author Filip Cekovsky
 */
public interface DataLoadingFacade {

    /**
     * Data Loading sequence
     * @throws IOException in case db access fails.
     */
    void loadData() throws IOException;
}
