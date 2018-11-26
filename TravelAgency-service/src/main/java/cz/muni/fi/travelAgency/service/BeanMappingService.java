package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.DTO.CheapTravelDTO;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for BeanMappingService
 *
 * @author Simona Raucinova, Filip Cekovsky
 */
public interface BeanMappingService {

    /**
     * Retrieves mapper used for mapping simple objects.
     *
     * @return the mapper used
     */
    Mapper getMapper();

    /**
     * Maps list of objects to target type
     *
     * @param objects    List to be mapped
     * @param mapToClass target class
     * @param <T>        target class type
     * @return Mapped List
     */
    <T> List<T> mapTo(List<?> objects, Class<T> mapToClass);

    /**
     * Maps set of objects to target type
     *
     * @param objects    Set to be mapped
     * @param mapToClass target class
     * @param <T>        target class type
     * @return Mapped Set
     */
    <T> Set<T> mapTo(Set<?> objects, Class<T> mapToClass);

    /**
     * Maps collection of objects to target type
     *
     * @param objects    collection to be mapped
     * @param mapToClass target class
     * @param <T>        target class type
     * @return Mapped collection
     */
    <T> Collection<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Maps an object to the target type
     *
     * @param u          object to be mapped
     * @param mapToClass target class
     * @param <T>        target type
     * @return mapped object
     */
    <T> T mapTo(Object u, Class<T> mapToClass);

    /**
     * Maps a map to {@link CheapTravelDTO}.
     *
     * @param entitiesMap map of elements
     * @return constructed DTO
     */
    CheapTravelDTO mapToCheapTravel(Map<Trip, Collection<Excursion>> entitiesMap);
}
