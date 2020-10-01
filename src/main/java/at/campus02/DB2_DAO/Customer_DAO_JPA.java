package at.campus02.DB2_DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer_DAO_JPA implements CustomerDAO{

    private EntityManagerFactory factory;
    private EntityManager manager;

    public Customer_DAO_JPA(){
        factory = Persistence.createEntityManagerFactory("nameOfJpaPersistenceUnit");
        //Manager wird mit Insatnziiert, der Managed dann alle Entitäten nach dem Einfügen in Datenbank
        // ohne dass diese immer wieder neu gemerged werden müssen, weil ein neuer Manager erstellt wurde
        manager = factory.createEntityManager();
    }   //hier kommt genau der gleiche Name wie in persictance.xml rein

    @Override
    public void create(Customer customer) {
       // EntityManager manager = factory.createEntityManager();    ->wird nur benötigt wenn es die Factory nicht als Klassenobjekt gibt
        manager.getTransaction().begin();
        manager.persist(customer);
        manager.getTransaction().commit();
    }

    @Override
    public Customer read(String lastname) {
        //EntityManager manager = factory.createEntityManager();    ->wird nur benötigt wenn es die Factory nicht als Klassenobjekt gibt
        return manager.find(Customer.class, lastname);  //muss nicht gecastet werden, wir bekommen Customer zurück
    }

    @Override
    public void update(Customer customer) {
       // EntityManager manager = factory.createEntityManager();    ->wird nur benötigt wenn es die Factory nicht als Klassenobjekt gibt
        manager.getTransaction().begin();   //WIR WOLLEN WAS VERÄNDERN; DESHALB brauchen wir Transaktion
        manager.merge(customer);    //nimmt sich Primary Key vom geänderten Customer und verbindet dann die geänderten Daten mit dem Customer passend zum Primary Key
        manager.getTransaction().commit();
    }

    @Override
    public void delete(Customer customer) {
        // EntityManager manager = factory.createEntityManager();   ->wird nur benötigt wenn es die Factory nicht als Klassenobjekt gibt
        manager.getTransaction().begin();
        /*Alternativer Code für Line 47
        Customer managed = manager.merge(customer);
        manager.remove(managed);*/

        //hier ist das merge nicht mehr notwendig, da wir jetzt einen Klassen-Manager haben, der also schon mit dieser Entität verbunden ist
        manager.remove(manager.merge(customer));    //wir können nicht nur ein remove machen, sondern müssen erst mergen, damit das gleiche Objekt (die Entity) auch dem EntityManager zugeordnet ist.
        // Jeder EntityManager verwaltet nur die zugeordneten Entitäten in der Datenbank
        manager.getTransaction().commit();
    }

}
