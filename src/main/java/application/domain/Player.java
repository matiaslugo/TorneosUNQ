package application.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    /*@ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "team_id")
    private Team team;*/

    private String name;
    private String lastName;
    private Long dni;
    /*@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthdate;*/
    private boolean isStudent;

    public Player (){}

    /*public Player(Team team, String name, String lastName, Long dni, DateTime birthdate, boolean isStudent) {
        //this.team = team;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.birthdate = birthdate;
        this.isStudent = isStudent;
    }*/

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

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

  /*  public DateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(DateTime birthdate) {
        this.birthdate = birthdate;
    }*/

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }
}
