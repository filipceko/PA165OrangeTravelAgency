package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * Implementation of {@link CustomerDao}
 *
 * @author Filip Cekovsky
 */
@Repository
@Transactional
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
    public Collection<Customer> findAll() {
        return manager.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    @Override
    public Customer findById(Long id) {
        return manager.find(Customer.class, id);
    }

    @Override
    public Customer findByName(String name, String surname) {
        if (name == null || surname == null) {
            throw new IllegalArgumentException("findByName() was called with NULL argument!");
        }
        return manager.createQuery("select c from Customer c where c.name = :name and c.surname = :surname", Customer.class)
                .setParameter("name", name)
                .setParameter("surname", surname)
                .getSingleResult();
    }

    @Override
    public void update(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("tried to update NULL customer");
        }
        if (findById(customer.getId()) == null) {
            throw new IllegalArgumentException("Customer must be saved before editing");
        }
        manager.merge(customer);
    }

    @Override
    public void remove(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Tried to remove NULL from the Customers");
        }
        manager.remove(manager.merge(customer));
    }
}
