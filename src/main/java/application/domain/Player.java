package application.domain;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    private String lastName;

    private int dni;


    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthdate;

    private boolean isStudent;

    public Player (){}

    public Player(String name, String lastName) {

        this.name = name;
        this.lastName = lastName;
    }


    public Player(String name, String lastName, Integer dni, DateTime birthdate, boolean isStudent) {

        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.birthdate = birthdate;
        this.isStudent = isStudent;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public DateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(DateTime birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public List<Player> setPlayerFromExcel(String nameExcel) throws IOException, InvalidFormatException {

        List<Player> players = new ArrayList<Player>();

        Workbook workbook = WorkbookFactory.create(new File(nameExcel));

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        int index = 0;

        for (Row row: sheet) {
            index ++;

            if(index != 1) {
                String name = "";
                String lastName = "";
                Integer dni = 0;
                DateTime birthdate = null;
                boolean isStudent = true;

                for (Cell cell : row) {
                    int numberColumn = cell.getColumnIndex();

                    String cellValue = dataFormatter.formatCellValue(cell);

                    switch (numberColumn) {
                        case 0:
                            name = cellValue;
                            break;
                        case 1:
                            lastName = cellValue;
                            break;
                        case 2:
                            dni = Integer.parseInt(cellValue);
                            break;
                        case 3:
                            birthdate = DateTime.parse(cellValue);
                            break;
                        case 4:
                            isStudent = Boolean.parseBoolean(cellValue);
                            break;
                        default:
                            break;
                    }

                }

                if(name != "") {
                    Player player = new Player(name, lastName, dni, birthdate, isStudent);

                    players.add(player);
                }
            }
        }

        // Closing the workbook
        workbook.close();

        return players;
    }
}
