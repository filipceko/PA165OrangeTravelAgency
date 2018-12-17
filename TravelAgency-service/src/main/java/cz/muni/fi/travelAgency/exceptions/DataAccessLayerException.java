package cz.muni.fi.travelAgency.exceptions;

import org.springframework.dao.DataAccessException;

/**
 * Class to represent exception caused by DAO.
 *
 * @author Simona Raucinova
 */
public class DataAccessLayerException extends DataAccessException {

    public DataAccessLayerException(String msg) {
        super(msg);
    }

    public DataAccessLayerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
