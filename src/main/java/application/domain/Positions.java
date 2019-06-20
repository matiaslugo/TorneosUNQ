package application.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public void addStatisticTeams( StatisticTeam statisticTeam){
        this.statisticTeams.add(statisticTeam);
    }

    public void ordenar(){

        Collections.sort(this.statisticTeams, new Comparator<StatisticTeam>(){
            @Override
            public int compare(StatisticTeam st1, StatisticTeam st2) {
                Integer st1Point = new Integer(st1.getPoints());
                Integer st2Point = new Integer(st2.getPoints());
                return st2Point.compareTo(st1Point);
            }
        });
    }
}
