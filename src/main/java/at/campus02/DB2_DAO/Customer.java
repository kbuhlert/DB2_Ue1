package at.campus02.DB2_DAO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    private String lastname;

    private String firstname;
    private Integer age;    //wir nehmen Integer statt int, da Integer nullable ist und somit das Alter weggelassen werden kann statt 0 zu schreiben

    //Standard-konstruktor für JPA
    public Customer(){}

    //Hilfskonstruktor zum selber Klonen (Auflösen der Referenz):
    // Da wir keine DB nutzen, sondern eine HashMap,
    // die physikalisch im Speicher nicht getrennt ist, wird wenn JavaObjekt geändert wird, dieses auch gleich in HAshMap geändert,
    // da in der HAshMap nur eine Referenz auf das Objekt liegt. Zum Simullieren der DB wollen wir das Objekt klonen und den Klon
    // in die HashMap legen. Ansonsten könneten wir die DB-Funktion UPDATE nicht testen, da das Objekt wenn es geändert wird auch
    // immer in  der HashMap als geändert erscheinen würde.
    public Customer (Customer toClone){
        lastname = toClone.getLastname();
        firstname = toClone.getFirstname();
        age = toClone.getAge();
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", age=" + age +
                '}';
    }
}
