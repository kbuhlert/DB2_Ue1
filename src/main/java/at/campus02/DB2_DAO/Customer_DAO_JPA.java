package at.campus02.DB2_DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer_DAO_JPA implements CustomerDAO{

    private EntityManagerFactory factory;

    public Customer_DAO_JPA(){
        factory = Persistence.createEntityManagerFactory("nameOfJpaPersistenceUnit");
    }

    @Override
    public void create(Customer customer) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(customer);
        manager.getTransaction().commit();
    }

    @Override
    public Customer read(String lastname) {
        EntityManager manager = factory.createEntityManager();
        return manager.find(Customer.class, lastname);
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }

}
