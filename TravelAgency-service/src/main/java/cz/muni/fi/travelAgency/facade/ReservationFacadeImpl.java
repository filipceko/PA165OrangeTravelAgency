package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.exceptions.DataAccessException;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Implementation of {@link ReservationFacade}.
 *
 * @author Filip Cekovsky
 */
@Service
@Transactional
public class ReservationFacadeImpl implements ReservationFacade {

    /**
     * Reservation service.
     */
    @Autowired
    private ReservationService service;

    /**
     * Mapper responsible for mapping DTOs to Entities.
     */
    @Autowired
    private BeanMappingService mappingService;

    @Override
    public void createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = mappingService.mapTo(reservationDTO, Reservation.class);
        reservation.setReserveDate(LocalDate.now());
        service.create(reservation);
        reservationDTO.setId(reservation.getId());
        reservationDTO.setReserveDate(reservation.getReserveDate());
    }

    @Override
    public Collection<ReservationDTO> getAllReservations() {
        Collection<Reservation> result;
        try {
            result = service.findAll();
        } catch (Exception e) {
            throw new DataAccessException("Could not obtain Reservations from the persistence layer", e);
        }
        return mappingService.mapTo(result, ReservationDTO.class);
    }

    @Override
    public ReservationDTO getById(Long id) {
        Reservation queryResult;
        try {
            queryResult = service.findById(id);
        } catch (Exception e) {
            throw new DataAccessException("Could not obtain Reservation from the persistence layer", e);
        }
        return (queryResult == null) ? null : mappingService.mapTo(queryResult, ReservationDTO.class);
    }

    @Override
    public Collection<ReservationDTO> getByCustomer(Long customerId) {
        Collection<Reservation> reservations;
        try {
            reservations = service.findByCustomer(customerId);
        } catch (Exception e) {
            throw new DataAccessException("Could not obtain Reservations from the persistence layer", e);
        }
        return mappingService.mapTo(reservations, ReservationDTO.class);
    }

    @Override
    public Collection<ReservationDTO> getByTrip(Long tripId) {
        Collection<Reservation> reservations;
        try {
            reservations = service.findByTrip(tripId);
        } catch (Exception e) {
            throw new DataAccessException("Could not obtain Reservations from the persistence layer", e);
        }
        return mappingService.mapTo(reservations, ReservationDTO.class);
    }

    @Override
    public void update(ReservationDTO data) {
        if (data == null) {
            throw new IllegalArgumentException("Tried to update null reservation");
        }
        if (data.getId() == null) {
            throw new IllegalArgumentException("Tried to update reservation without ID");
        }
        Reservation reservation = mappingService.mapTo(data, Reservation.class);
        try {
            service.update(reservation);
        } catch (Exception e) {
            throw new DataAccessException("Could not update Reservation in the persistence layer", e);
        }


    }

    @Override
    public void delete(ReservationDTO reservationDTO) {
        if (reservationDTO == null) {
            throw new IllegalArgumentException("Tried to delete null reservation");
        }
        if (reservationDTO.getId() == null) {
            throw new IllegalArgumentException("tried to delete reservation without ID");
        }
        Reservation reservation = mappingService.mapTo(reservationDTO, Reservation.class);
        try {
            service.remove(reservation);
        } catch (Exception e) {
            throw new DataAccessException("Could not remove Reservation in the persistence layer", e);
        }
    }

    @Override
    public Collection<ReservationDTO> getReservationByInterval(LocalDate from, LocalDate to) {
        Collection<Reservation> result;
        try {
            result = service.findReservationsBetween(from, to);
        } catch (Exception e) {
            throw new DataAccessException("Could not obtain Reservations from the persistence layer", e);
        }
        return mappingService.mapTo(result, ReservationDTO.class);
    }
}
