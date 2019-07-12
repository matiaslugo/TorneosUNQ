package application.dto;

import java.util.ArrayList;

public class FixtureDTO {

    private int matchDateNumber;
    private ArrayList<GameFixtureDTO> games;
    
    public FixtureDTO(){

    }

    public FixtureDTO(int matchDateNumber){
        this.matchDateNumber = matchDateNumber;
        this.games = new ArrayList<GameFixtureDTO>();
    }

    public int getMatchDateNumber() {
        return matchDateNumber;
    }

    public void setMatchDateNumber(int matchDateNumber) {
        this.matchDateNumber = matchDateNumber;
    }

    public ArrayList<GameFixtureDTO> getGames() {
        return games;
    }

    public void setGames(ArrayList<GameFixtureDTO> games) {
        this.games = games;
    }
    
}