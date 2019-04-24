package application.domain;

import javax.persistence.*;

@Entity
public class StatisticTeam {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Team team;

    private int played;

    private int points;

    private int won;

    private int drawn;

    private int lost;

    private int goalsF;

    private int goalsA;

    private int goalsD;

    public StatisticTeam(){}


    public StatisticTeam(Team team, int played, int points, int won, int drawn, int lost, int goalsF, int goalsA, int goalsD) {
        this.team = team;
        this.played = played;
        this.points = points;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.goalsF = goalsF;
        this.goalsA = goalsA;
        this.goalsD = goalsD;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDrawn() {
        return drawn;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getGoalsF() {
        return goalsF;
    }

    public void setGoalsF(int goalsF) {
        this.goalsF = goalsF;
    }

    public int getGoalsA() {
        return goalsA;
    }

    public void setGoalsA(int goalsA) {
        this.goalsA = goalsA;
    }

    public int getGoalsD() {
        return goalsD;
    }

    public void setGoalsD(int goalsD) {
        this.goalsD = goalsD;
    }
}
