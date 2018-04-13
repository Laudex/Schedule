import java.io.FileInputStream;
import java.io.IOException;

import Entity.AircraftType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Reader {
    public void readFromExcel(String file) throws IOException {
        XSSFWorkbook excelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet excelSheet = excelBook.getSheet("types");
        for (int i = 1; i <= 6; i++) {
            XSSFRow row = excelSheet.getRow(i);
            String id = row.getCell(0).getStringCellValue();
            /*double capacity = row.getCell(1).getNumericCellValue();
            double mass = row.getCell(2).getNumericCellValue();
            double surface =row.getCell(3).getNumericCellValue();
            double CD0 = row.getCell(4).getNumericCellValue();
            double CD2 = row.getCell(5).getNumericCellValue();
            double Cf1 = row.getCell(6).getNumericCellValue();
            double Cf2 = row.getCell(7).getNumericCellValue();
            double CfCR = row.getCell(8).getNumericCellValue();
            double MRCSpeed = row.getCell(9).getNumericCellValue();
            double baseTurnTime = row.getCell(10).getNumericCellValue();
            double idleTimeCost = row.getCell(11).getNumericCellValue();
            double lowBoundDemand = row.getCell(12).getNumericCellValue();
            double uppBoundDemand = row.getCell(13).getNumericCellValue();
            System.out.println(id + " " + capacity+ " "  + mass + " " + surface + " " + CD0 + " " + CD2 + " " + Cf1 + " " + Cf2 + " " + CfCR + " " + MRCSpeed + " " + baseTurnTime + " " + idleTimeCost + " " + lowBoundDemand + " " + uppBoundDemand);
           // AircraftType newType = new AircraftType(id, capacity, mass, surface, CD0, CD2, Cf1, Cf2, CfCR, MRCSpeed, baseTurnTime, idleTimeCost, lowBoundDemand, uppBoundDemand);*/

        }
    }
}
