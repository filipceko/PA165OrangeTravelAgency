package cz.muni.fi.travelAgency.service;

import java.util.Collection;
import java.util.List;
import org.dozer.Mapper;

/**
 * Interface for BeanMappingService
 * @author Simona Raucinova
 */
public interface BeanMappingService {

    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);
    public  <T> T mapTo(Object u, Class<T> mapToClass);
    public Mapper getMapper();

}
