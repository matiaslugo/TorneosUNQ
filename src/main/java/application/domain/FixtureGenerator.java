package application.domain;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import application.repository.FixtureRepository;

public class FixtureGenerator {

	//Algoritmo: http://www.delphiaccess.com/forum/c-c-49/fixture-todos-contra-todos-en-c/
	
	private ArrayList<String> teamsName;
	private Team[][] matriz1,matriz2;
	private String[][] jornadas;
	private int teamsNumber;
	@Autowired
	private FixtureRepository repositoryFixture;

	//Num de jornadas = (N-1)*2, con N = num equipos. (N-1) es una vuelta.
	
	/**
	 * @param teamsNumber -> numero de equipos
	 */
	public FixtureGenerator(){}

	public Fixture FixtureCreate(ArrayList<Team> teams)
	{
		this.teamsName = new ArrayList<String>();
		this.teamsNumber = teams.size();

		//Asigno posiciones del array a los equipos
		for (Team team : teams) {
			teamsName.add(team.getName());
		}
		
		matriz1 = new Team[teamsNumber-1][teamsNumber/2];
		matriz2 = new Team[teamsNumber-1][teamsNumber/2];
		jornadas = new String[teamsNumber-1][teamsNumber/2]; 

		int cont = 0;
		int cont2 = teamsNumber-2;
		
		int matchWeek = 1;
		Fixture fixtureNew = new Fixture();

		for(int i=0;i<teamsNumber-1;i++){
			for(int j=0;j<teamsNumber/2;j++){
				//matriz1
				matriz1[i][j] = teams.get(cont);
				cont++;
				if(cont==(teamsNumber-1)) cont=0;
				
				//matriz2
				if(j==0) matriz2[i][j] = teams.get(teamsNumber - 1);
				else {
					matriz2[i][j] = teams.get(cont2);
					cont2--;
					if(cont2==-1) cont2 = teamsNumber-2;
				}
				
			//Genero el partido, asigno los equipos que se enfrentan y lo cargo en el fixture.
				Game gameNew = new Game();
				Team teamA;
				Team teamB;
				
				if(j==0){
					if(i%2==0)
					{
						teamA = matriz2[i][j];
						teamB = matriz1[i][j];

						jornadas[i][j] = matriz2[i][j].getName() + "-" + matriz1[i][j].getName() + " ";
					} 
					else
					{
						teamA = matriz1[i][j];
						teamB = matriz2[i][j];
						jornadas[i][j] = matriz1[i][j].getName() + "-" + matriz2[i][j].getName() + " ";
					} 
				}
				else{
					teamA = matriz1[i][j];
					teamB = matriz2[i][j];
					jornadas[i][j] = matriz1[i][j].getName() + "-" + matriz2[i][j].getName() + " ";
				} 
				gameNew.setTeamA(teamA);
				gameNew.setTeamB(teamB);
				gameNew.setMatchweek(matchWeek);
				fixtureNew.getGame().add(gameNew);
		}
				matchWeek++;
	}
		
		//Solo para mostrarlo por consola

		int jorn = 1;
		for(int i=0;i<teamsNumber-1;i++){
			for(int j=0;j<teamsNumber/2;j++){
				System.out.print("J"+jorn+" "+jornadas[i][j]); 
				if(j==(teamsNumber/2)-1) System.out.println();
			}
			jorn++;
		}
		System.out.println();
		for (Game game : fixtureNew.getGame()) {
			System.out.print("Fecha " + game.getMatchweek() + " ");
			System.out.println();

			System.out.print(game.getTeamA().getName() + " vs " + game.getTeamB().getName()  + " ");
			System.out.println();
		}
		return fixtureNew;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Team team1 = new Team();
		team1.setName("Equipo UNQ");
		Team team2 = new Team();
		team2.setName("Equipo UNSAM");
		Team team3 = new Team();
		team3.setName("Equipo UTN");
		Team team4 = new Team();
		team4.setName("San Lorenzo");
		Team team5 = new Team();
		team5.setName("River");
		ArrayList<Team> teams = new ArrayList<Team>();
		teams.add(team1);
		teams.add(team2);
		teams.add(team3);
		teams.add(team4);
		// teams.add(team5);
		// System.out.print();
		FixtureGenerator fg = new FixtureGenerator();
		fg.FixtureCreate(teams);
	}
	
}