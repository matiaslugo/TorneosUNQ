package application.dto;

import java.util.ArrayList;

import application.domain.Game;

public class FixtureDTO {

    private int matchDateNumber;
    private ArrayList<Game> games;
    
    public FixtureDTO(){

    }

    public FixtureDTO(int matchDateNumber){
        this.matchDateNumber = matchDateNumber;
        this.games = new ArrayList<Game>();
    }

    public int getMatchDateNumber() {
        return matchDateNumber;
    }

    public void setMatchDateNumber(int matchDateNumber) {
        this.matchDateNumber = matchDateNumber;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
    
}