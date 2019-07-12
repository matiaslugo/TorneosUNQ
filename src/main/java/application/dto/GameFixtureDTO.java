package application.dto;


import org.json.JSONObject;

import java.util.List;

public class GameFixtureDTO {

    private Long id;
    private Long teamAId;
    private String teamAName;
    private Long teamBId;
    private String teamBName;
    private String date;

    private String startTime;

    private int goalsTeamA;

    private int goalsTeamB;

    private int matchweek;

    private List<StatisticPlayerDTO> statisticPlayer;

    public GameFixtureDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamAId() {
        return teamAId;
    }

    public void setTeamAId(Long teamAId) {
        this.teamAId = teamAId;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public Long getTeamBId() {
        return teamBId;
    }

    public void setTeamBId(Long teamBId) {
        this.teamBId = teamBId;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getGoalsTeamA() {
        return goalsTeamA;
    }

    public void setGoalsTeamA(int goalsTeamA) {
        this.goalsTeamA = goalsTeamA;
    }

    public int getGoalsTeamB() {
        return goalsTeamB;
    }

    public void setGoalsTeamB(int goalsTeamB) {
        this.goalsTeamB = goalsTeamB;
    }

    public int getMatchweek() {
        return matchweek;
    }

    public void setMatchweek(int matchweek) {
        this.matchweek = matchweek;
    }

    public List<StatisticPlayerDTO> getStatisticPlayer() {
        return statisticPlayer;
    }

    public void setStatisticPlayer(List<StatisticPlayerDTO> statisticPlayer) {
        this.statisticPlayer = statisticPlayer;
    }
}
