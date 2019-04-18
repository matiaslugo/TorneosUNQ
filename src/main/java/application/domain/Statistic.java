package application.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Statistic {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Player player;

    private int goals;

    private int yellowCard;

    private int redCard;

    public Statistic(){};

    public Statistic(Player player, int goals, int yellowCard, int redCard) {
        this.player = player;
        this.goals = goals;
        this.yellowCard = yellowCard;
        this.redCard = redCard;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(int yellowCard) {
        this.yellowCard = yellowCard;
    }

    public int getRedCard() {
        return redCard;
    }

    public void setRedCard(int redCard) {
        this.redCard = redCard;
    }
}
