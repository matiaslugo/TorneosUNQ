package application.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Fixture {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "fixture_id")
    private List<Game> Game = new ArrayList<Game>();

    private int currentMatchWeek;

    public Fixture(){}

    public Fixture(List<application.domain.Game> game, int currentMatchWeek) {
        Game = game;
        this.currentMatchWeek = currentMatchWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<application.domain.Game> getGame() {
        return Game;
    }

    public void setGame(List<application.domain.Game> game) {
        Game = game;
    }

    public int getCurrentMatchWeek() {
        return currentMatchWeek;
    }

    public void setCurrentMatchWeek(int currentMatchWeek) {
        this.currentMatchWeek = currentMatchWeek;
    }
}
