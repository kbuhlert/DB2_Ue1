package at.campus02.DB2_DAO;

//Definiert CRUD Operationen (Create Read Update Delete) für die Customer Tabelle
public interface CustomerDAO {


    void create(Customer customer);
    Customer read(String lastname); //Customer ist Rückgabewert
    void update(Customer customer);
    void delete(Customer customer);
}
