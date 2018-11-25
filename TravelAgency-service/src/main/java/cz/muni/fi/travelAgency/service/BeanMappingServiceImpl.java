package cz.muni.fi.travelAgency.service;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of {@link BeanMappingService}
 *
 * @author Simona Raucinova, Filip Cekovsky
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    /**
     * Mapper to be used for basic mapping.
     */
    @Autowired
    private Mapper mapper;

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public <T> List<T> mapTo(List<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(mapTo(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> Set<T> mapTo(Set<?> objects, Class<T> mapToClass) {
        Set<T> mappedCollection = new HashSet<>();
        for (Object object : objects) {
            mappedCollection.add(mapTo(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> Collection<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(mapTo(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return mapper.map(u, mapToClass);
    }
}
