package application.domain;

import javax.persistence.*;


@Entity
public class StatisticPlayer {

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

    public StatisticPlayer(){};

    public StatisticPlayer(Player player, int goals, int yellowCard, int redCard) {
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
