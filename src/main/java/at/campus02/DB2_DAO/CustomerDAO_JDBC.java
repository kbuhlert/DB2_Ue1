package at.campus02.DB2_DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomerDAO_JDBC implements CustomerDAO {

    //Derby Datenbannkverbindung muss unter dependencies in build.gradle angegeben werden.
    // Dann wird automatisch der Database-Folder im Projekt-Folder erstellt (weil: create = true in Application bei new CustomerDAO_JDBC)

    //Das brauchen wir: Treiber (hier Derby Embedded) und Pfad/URL
    private Connection con;

    public CustomerDAO_JDBC(String JDBCUrl) {
        try {
            //bei build.gradle muss noch eine dependency "compile" zugefügt werden
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");  //Registriert diese Klasse als Treiber mit Name
            con = DriverManager.getConnection(JDBCUrl);
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void create(Customer customer) {

    }

    @Override
    public Customer read(String lastname) {
        return null;
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }
}
