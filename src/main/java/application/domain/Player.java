package application.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.awt.*;
import java.sql.Blob;

import static java.sql.JDBCType.BLOB;

@Entity
@Transactional
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    private String lastName;

    private int dni;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthdate;

    private boolean isStudent;

    @Lob
    //@Column(length=100000,columnDefinition="BLOB")
    private byte[] photo;

    public Player (){}

    public Player(String name, String lastName) {

        this.name = name;
        this.lastName = lastName;
    }


    public Player(String name, String lastName, Integer dni, DateTime birthdate, boolean isStudent) {

        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.birthdate = birthdate;
        this.isStudent = isStudent;
    }

    public Player(String name, String lastName, int dni, DateTime birthdate, boolean isStudent, byte[] photo) {
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.birthdate = birthdate;
        this.isStudent = isStudent;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public DateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(DateTime birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
