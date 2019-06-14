package application.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Championship {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startDate;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime finishDate;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "championship_id")
    private List<Positions> positions = new ArrayList<Positions>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "championship_id")
    private List<Team> teams = new ArrayList<Team>();

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Fixture fixture;

    public Championship(){}

    public Championship(String name, String description, DateTime startDate, DateTime finishDate, List<Positions> positions, Fixture fixture) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.positions = positions;
        this.fixture = fixture;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(DateTime finishDate) {
        this.finishDate = finishDate;
    }

    public List<Positions> getPositions() {
        return positions;
    }
    
    public void setPositions(List<Positions> positions) {
        this.positions = positions;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public List<Team> allTeams() {
        return this.teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
