package at.campus02.DB2_DAO;

import java.sql.*;

public class CustomerDAO_JDBC implements CustomerDAO {

    //Derby Datenbannkverbindung muss unter dependencies in build.gradle angegeben werden.
    // Dann wird automatisch der Database-Folder im Projekt-Folder erstellt (weil: create = true in Application bei new CustomerDAO_JDBC)

    //Das brauchen wir: Treiber (hier Derby Embedded) und Pfad/URL
    private Connection con;

    public CustomerDAO_JDBC(String JDBCUrl) {
        try {
            //bei build.gradle muss noch eine dependency "compile" zugefügt werden
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");  //Registriert diese Klasse als Treiber mit Name
            //Connection erzeugen lassen
            con = DriverManager.getConnection(JDBCUrl);
            //Sicherstellen, dass die Tabelle existiert
            ensureTable();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

    }

    private void ensureTable() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("CREATE TABLE CUSTOMER" +
                    "(lastname VARCHAR(50) primary key, firstname VARCHAR(50), age INT)");
            pstmt.execute();
        } catch (SQLException e) {
            //Falls die Tabelle schon existiert, kommt ein spezieller SQLState mit der
            // Exception mit -> diesen können wir ignorieren,
            // andere Exceptions schmeißen wir einfach weiter
            if("X0Y32".equalsIgnoreCase(e.getSQLState())){return;}
            throw e;
        }
    }

    @Override
    public void create(Customer customer) {
        try {
            PreparedStatement insert = con.prepareStatement("INSERT INTO CUSTOMER VALUES(?,?,?)");
            insert.setString(1, customer.getLastname());
            insert.setString(2, customer.getFirstname());
            insert.setInt(3, customer.getAge());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not insert customer.", e);
        }

    }

    @Override
    public Customer read(String lastname) {
        try {
            PreparedStatement query = con.prepareStatement("SELECT * from CUSTOMER WHERE lastname = ?");
            query.setString(1,lastname);
            ResultSet rs = query.executeQuery();
            if(rs.next()){
                Customer fromDB = new Customer();
                fromDB.setLastname(rs.getString(1));
                fromDB.setFirstname(rs.getString(2));
                fromDB.setAge(rs.getInt(3));
                return fromDB;}
            else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Customer customer) {
        try {
            PreparedStatement update = con.prepareStatement("UPDATE CUSTOMER SET firstname = ?, age = ? WHERE lastname = ?");
            update.setString(1, customer.getFirstname());
            update.setInt(2, customer.getAge());
            update.setString(3, customer.getLastname());
            int count = update.executeUpdate();
            if (count == 0) {
                System.out.println("Customer ist nicht in Datenbank vorhanden");
            } else
                System.out.println("Projektdaten wurden aktualisiert");
            update.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void delete(Customer customer) {
        try {
            PreparedStatement delete = con.prepareStatement("DELETE FROM CUSTOMER WHERE lastname = ?");
            delete.setString(1, customer.getLastname());
            delete.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    public void tabelleCustomerAusgeben() {
        String selectAll = "SELECT * FROM CUSTOMER";
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(selectAll);
            int colums = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= colums; i++) {
                System.out.print(String.format("%-17s", rs.getMetaData().getColumnLabel(i)));
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------");
            while (rs.next()) {
                for (int i = 1; i <= colums; i++) {
                    System.out.print(String.format("%-20s", rs.getString(i)));
                }
                System.out.println();
            }
            System.out.println();
            rs.close();
            stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
