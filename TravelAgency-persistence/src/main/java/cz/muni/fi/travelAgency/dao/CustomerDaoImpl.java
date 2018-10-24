package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of {@link CustomerDao}
 *
 * @author Filip Cekovsky
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

    /**
     * Entity manager for this class.
     */
    @PersistenceContext
    private EntityManager manager;

    @Override
    public void create(Customer customer) {
        manager.persist(customer);
    }

    @Override
    public List<Customer> findAll() {
        return manager.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    @Override
    public Customer findById(Long id) {
        return manager.find(Customer.class, id);
    }

    @Override
    public Customer findByName(String name, String surname) {
        return manager.createQuery("select c from Customer c where c.name = :name and c.surname = :surname", Customer.class)
                .setParameter("name", name)
                .setParameter("surname", surname)
                .getSingleResult();
    }

    @Override
    public void update(Customer customer) {
        manager.merge(customer);
    }

    @Override
    public void remove(Customer customer) {
        manager.remove(customer);
    }
}