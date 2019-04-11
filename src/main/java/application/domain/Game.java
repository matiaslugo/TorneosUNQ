package application.domain;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.LocalTime;
import org.hibernate.annotations.Type;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Team teamA;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Team teamB;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    private LocalTime startTime;

    public Game(){}


    public Game(Team teamA, Team teamB, DateTime date, LocalTime startTime) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
