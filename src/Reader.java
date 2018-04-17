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
            String idString = row.getCell(0).toString();
            int id = Integer.parseInt(idString.substring(0,1));

            double capacityDouble = row.getCell(1).getNumericCellValue();
            int capacity = (int) capacityDouble;

            double massDouble = row.getCell(2).getNumericCellValue();
            int mass = (int) massDouble;

            String surfaceString = row.getCell(3).toString();
            double surface = Double.parseDouble(surfaceString);

            String cd0String = row.getCell(4).toString();
            double CD0 = Double.parseDouble(cd0String);

            String cd2String = row.getCell(5).toString();
            double CD2 = Double.parseDouble(cd2String);

            String cf1String = row.getCell(6).toString();
            double Cf1 = Double.parseDouble(cf1String);

            String cf2String = row.getCell(7).toString();
            double Cf2 = Double.parseDouble(cf2String);

            String cfCRString = row.getCell(8).toString();
            double CfCR = Double.parseDouble(cfCRString);

            String mrcString = row.getCell(9).toString();
            double MRCSpeed = Double.parseDouble(mrcString);

            double baseTurnTimeDouble = row.getCell(10).getNumericCellValue();
            int baseTurnTime = (int) baseTurnTimeDouble;

            double idleTimeCostDouble = row.getCell(11).getNumericCellValue();
            int idleTimeCost = (int) idleTimeCostDouble;

            double lowBoundDemandDouble = row.getCell(12).getNumericCellValue();
            int lowBoundDemand = (int) lowBoundDemandDouble;

            double uppBoundDemandDouble = row.getCell(13).getNumericCellValue();
            int uppBoundDemand = (int)uppBoundDemandDouble;
           // System.out.println(id + " " + capacity+ " "  + mass + " " + surface + " " + CD0 + " " + CD2 + " " + Cf1 + " " + Cf2 + " " + CfCR + " " + MRCSpeed + " " + baseTurnTime + " " + idleTimeCost + " " + lowBoundDemand + " " + uppBoundDemand);
            AircraftType newType = new AircraftType(id, capacity, mass, surface, CD0, CD2, Cf1, Cf2, CfCR, MRCSpeed, baseTurnTime, idleTimeCost, lowBoundDemand, uppBoundDemand);

        }
    }
}
