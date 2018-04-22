import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Entity.AircraftType;
import Entity.Airport;
import Entity.Flight;
import Entity.Path;
import Factory.FlightFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Reader {
    public int startHour = 6;
    public int startMinutes = 45;
    public ArrayList<Path> paths = new ArrayList<>();
    public ArrayList<Flight> flights = new ArrayList<>();

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public int getTimeInMin(XSSFRow row, int numberOfCell){
        Date date = row.getCell(numberOfCell).getDateCellValue();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        int timeInMin = (hours - startHour) * 60 + minutes + startMinutes;
        return timeInMin;
    }

    public void read114FlightsFromExcel(String file, HashMap<Integer, AircraftType> typeHashMap, HashMap<String, Airport> airportHashMap) throws IOException{
        FlightFactory flightFactory = new FlightFactory();
        XSSFWorkbook excelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet excelSheet = excelBook.getSheet("Sheet1");
        int aircraftTypeId = -1;
        int pathId = 0;
        int flightId = 1;
        ArrayList<Flight> flightOfCurrentPath = new ArrayList<>();
        Path currentPath = new Path();
        for(int i = 1; i <= 114; i++){
            XSSFRow row = excelSheet.getRow(i);
            String idString = row.getCell(7).toString();
            int id = Integer.parseInt(idString.substring(0,1));
            if (id != aircraftTypeId){
                aircraftTypeId = id;
                if(!flightOfCurrentPath.isEmpty()){
                    currentPath.setSetOfFlights(flightOfCurrentPath);
                    flightFactory.setIdleTime(flightOfCurrentPath);
                }
                pathId++;
                flightOfCurrentPath = new ArrayList<>();
                Path path = new Path(pathId, typeHashMap.get(id));
                paths.add(path);
                currentPath = path;
                //Departure Time
                int depTimeInMin = getTimeInMin(row, 4);
                //Arrival Time
                int arrTimeInMin = getTimeInMin(row, 6);
                //Airports
                String origin = row.getCell(2).getStringCellValue();
                String destination = row.getCell(3).getStringCellValue();

                Flight newFlight = flightFactory.buildFlight(flightId, depTimeInMin, arrTimeInMin, currentPath, airportHashMap.get(origin), airportHashMap.get(destination), true);
                flightOfCurrentPath.add(newFlight);
                flights.add(newFlight);
                flightId++;

            } else {
                int depTimeInMin = getTimeInMin(row, 4);
                //Arrival Time
                int arrTimeInMin = getTimeInMin(row, 6);
                //Airports
                String origin = row.getCell(2).getStringCellValue();
                String destination = row.getCell(3).getStringCellValue();

                Flight newFlight = flightFactory.buildFlight(flightId, depTimeInMin, arrTimeInMin, currentPath, airportHashMap.get(origin), airportHashMap.get(destination), true);
                flightOfCurrentPath.add(newFlight);
                flights.add(newFlight);
                flightId++;

            }

        }
        currentPath.setSetOfFlights(flightOfCurrentPath);
        flightFactory.setIdleTime(flightOfCurrentPath);

    }
    public HashMap<Integer, AircraftType> readAirCraftTypesFromExcel(String file) throws IOException {
        HashMap<Integer, AircraftType> typeHashMap = new HashMap<>();
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
            typeHashMap.put(id, newType);

        }
        return typeHashMap;
    }
}
