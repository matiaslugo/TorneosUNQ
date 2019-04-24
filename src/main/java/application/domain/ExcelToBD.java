package application.domain;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelToBD {

    public ExcelToBD (){}

    public Team setTeamFromExcel(String nameExcel) throws IOException, InvalidFormatException {

        Team newTeam = new Team();

        Workbook workbook = WorkbookFactory.create(new File(nameExcel));

        Sheet sheetTeam = workbook.getSheetAt(0);

        DataFormatter dataFormatterTeam = new DataFormatter();

        int indexTeam = 0;

        for (Row row: sheetTeam) {
            indexTeam ++;

            if(indexTeam != 1) {
                String nameTeam = "";

                for (Cell cell : row) {
                    nameTeam = dataFormatterTeam.formatCellValue(cell);
                }

                newTeam.setName(nameTeam);
            }
        }

        // Closing the workbook
        workbook.close();

        return newTeam;
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
                String photo = "";

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
                        case 5:
                            photo = cellValue;
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
