package application.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Positions {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "positions_id")
    private List<StatisticTeam> statisticTeams = new ArrayList<StatisticTeam>();

    public Positions(){}


    public Positions(List<StatisticTeam> statisticTeams, String name) {
        this.statisticTeams = statisticTeams;
        this.name = name;
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

    public List<StatisticTeam> getStatisticTeams() {
        return statisticTeams;
    }

    public void setStatisticTeams(List<StatisticTeam> statisticTeams) {
        this.statisticTeams = statisticTeams;
    }
}
