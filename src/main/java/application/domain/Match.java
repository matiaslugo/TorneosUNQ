package application.domain;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.LocalTime;
import org.hibernate.annotations.Type;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    /*@ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "team_id")
    private Team teamA;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "team_id")
    private Team teamB;*/

    /*@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;
    private LocalTime startTime;*/

    public Match(){}

    /*public Match(Team teamA, Team teamB, DateTime date, LocalTime startTime) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.startTime = startTime;
    }*/
}
