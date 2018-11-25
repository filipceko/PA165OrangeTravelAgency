package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.entities.Reservation;
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
        return mappingService.mapTo(service.findAll(), ReservationDTO.class);
    }

    @Override
    public ReservationDTO getById(Long id) {
        Reservation queryResult = service.findById(id);
        return (queryResult == null) ? null : mappingService.mapTo(queryResult, ReservationDTO.class);
    }

    @Override
    public Collection<ReservationDTO> getByCustomer(Long customerId) {
        return mappingService.mapTo(service.findByCustomer(customerId), ReservationDTO.class);
    }

    @Override
    public Collection<ReservationDTO> getByTrip(Long tripId) {
        return mappingService.mapTo(service.findByTrip(tripId), ReservationDTO.class);
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
        service.update(reservation);
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
        service.remove(reservation);
    }

    @Override
    public Collection<ReservationDTO> getReservationByInterval(LocalDate from, LocalDate to) {
        return mappingService.mapTo(service.findReservationsBetween(from, to), ReservationDTO.class);
    }
}
