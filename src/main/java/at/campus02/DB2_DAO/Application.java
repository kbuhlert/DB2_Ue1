package at.campus02.DB2_DAO;

public class Application {

    public static void log(String msg){
        System.out.println("Application: --> " + msg);
    }

    public static void main(String[] args) {
        log("Hallo");

        //Customer vorbereiten
        Customer customer1 = new Customer();
        customer1.setLastname("Fuchs");
        customer1.setFirstname("Fritz");
        customer1.setAge(30);

        //Data Access Object holen -> woher?

        //InMemory, bzw. Cache-Implementierung
        //CustomerDAO dao = new CustomerDAOInMemory();

        //JDBC Implementierung (DB-Programmierung 1)
        //CustomerDAO_JDBC dao = new CustomerDAO_JDBC("jdbc:derby:database;create=true");

        //JBA Implementierung
        Customer_DAO_JPA dao = new Customer_DAO_JPA();


                //1) CREATE
        dao.create(customer1);
        log("Created customer1");

        //2) READ
        Customer fromDBX = dao.read("Fuchs");
            //oder
        Customer fromDB = dao.read(customer1.getLastname());
        log("Read customer from DB: " + fromDB);

        //3) UPDATE, dann noch einmal aus DB holen
        customer1.setFirstname("Friedrich");
        dao.update(customer1);
        fromDB = dao.read(customer1.getLastname());
        log("Updated customer1 in DB: " + fromDB);

        //3b) Tabelle CUSTOMER Ausgeben
        //dao.tabelleCustomerAusgeben();

        //4) DELETE, dann versuchen aus DB zu holen
        dao.delete(customer1);
        fromDB = dao.read(customer1.getLastname());
        log("Deleted customer1 from DB: " + fromDB);

        //4b) Tabelle CUSTOMER Ausgeben
        //dao.tabelleCustomerAusgeben();


    }
}
