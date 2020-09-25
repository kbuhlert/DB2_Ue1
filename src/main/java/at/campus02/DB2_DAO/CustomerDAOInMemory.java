package at.campus02.DB2_DAO;

import java.util.HashMap;
import java.util.Map;

public class CustomerDAOInMemory implements CustomerDAO{

    private final Map<String, Customer> cache = new HashMap<>();  //wir machen keine echte DB, sondern legen unsere Daten in HashMap,
    // diese ist also DB und Tabelle in einem

    @Override
    public void create(Customer customer) {
        cache.put(customer.getLastname(), new Customer(customer));  //Customer wird geklont/kopiert und die Kopie wird in HashMap gelegt

    }

    @Override
    public Customer read(String lastname) {
        Customer fromCache = cache.get(lastname);   //Datensatz wird aus DB (hashMap) gelesen
        //if-Abfrage kann alternativ so geschrieben werden
        /*if (fromCache == null){
            return null;}
            else {
            return new Customer(fromCache);
        }*/
        //Kurzschreibweise ? Ist if-teil : ist Else-Teil
        return fromCache == null ? null : new Customer(fromCache);
    }

    @Override
    public void update(Customer customer) {
        Customer fromCache = cache.get(customer.getLastname());
        //Wir brauchen aber auch noch Überprüfung ob Customer bereits existiert, hierfür machen wir eine Exception
        if (fromCache == null){
            throw new IllegalStateException("Customer does not exist in DB");
        }
        fromCache.setFirstname(customer.getFirstname());
        fromCache.setAge(customer.getAge());

    }

    @Override
    public void delete(Customer customer) {
        //Überprüfen, ob Datensatz (Key) in hashMap vorhanden ist
        if(!cache.containsKey(customer.getLastname())){
            throw new IllegalStateException("Customer does not exist in DB");
        }else
            //wenn Datensatz vorhanden, dann soll der gelöscht werden
        cache.remove(customer.getLastname());
    }
}
