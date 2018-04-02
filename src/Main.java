import Entity.AircraftType;
import Entity.Airport;
import Entity.Flight;
import Entity.Path;
import Factory.AirportFactory;
import Factory.FlightFactory;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static double serviceLevelBound = 0.8;
    public static double minimumServiceLevelForEachFlight = 0.5;
    //e^a
    public static double scaleParameter = 20;
    //betta
    public static double tailParameter = 0.01;
    public static double costFuel = 0.6;
    public static double baseSpillCost = 15;
    public static double connectedTimeInterval = 45;
    public static double density = 1.29;
    public static double gravAcc = 9.8;
    public static double blank = 5;

    public static double numberOfTypes = 3;

    public static void main(String[] args) {
        AirportFactory airportFactory = new AirportFactory();
        HashMap<String, Airport> airportMap = airportFactory.buildAirportMap();

        AircraftType b767300 = new AircraftType(1, 218, 135000, 283.3, 0.021, 0.049, 0.763, 1430, 1.0347, 876.70, 40, 147, 4, 160, 218);
        AircraftType a320212 = new AircraftType(2,180,64000,122.6,0.024,0.0375,0.94,100000,1.06,868.79,30,144,4,160,180);
        AircraftType a320111 = new AircraftType(3, 172,62000,122.4,0.024,0.0375,0.94,50000,1.095,855.15,28,136,4,150,172);

        Path path1 = new Path(1, b767300);
        //6:20
        int startTime = 0;

        /*for(Map.Entry<String, Airport> entry : airportMap.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue().getCongestion());
        }
        */



        FlightFactory flightFactory = new FlightFactory();

        Flight f2303 = flightFactory.buildFlight(1, 25, 180, path1, airportMap.get("ORD"), airportMap.get("DFW"));
        Flight f2336 = flightFactory.buildFlight(2, 230, 380, path1, airportMap.get("DFW"), airportMap.get("ORD"));
        Flight f1053 = flightFactory.buildFlight(3, 425, 595, path1, airportMap.get("ORD"), airportMap.get("AUS"));
        Flight f336 = flightFactory.buildFlight(4, 640, 805, path1, airportMap.get("AUS"), airportMap.get("ORD"));
        Flight f336sec = flightFactory.buildFlight(5, 860, 994, path1, airportMap.get("ORD"), airportMap.get("LGA"));
        path1.setSetOfFlights(f2303, f2336, f1053, f336, f336sec);
        flightFactory.setIdleTime(f2303, f2336, f1053, f336, f336sec);

        Path path2 = new Path(2,b767300);
        Flight f1341 = flightFactory.buildFlight(6, 150, 385, path2, airportMap.get("ORD"), airportMap.get("SFO"));
        Flight f348 = flightFactory.buildFlight(7, 430, 695, path2, airportMap.get("SFO"), airportMap.get("ORD"));
        Flight f1521 = flightFactory.buildFlight(8, 775,1010, path2, airportMap.get("ORD"), airportMap.get("TUS"));
        path2.setSetOfFlights(f1341,f348,f1521);
        flightFactory.setIdleTime(f1341, f348, f1521);

        Path path3 = new Path(3, a320212);

        Flight f407 = flightFactory.buildFlight(9, 0,70, path3, airportMap.get("ORD"), airportMap.get("STL"));
        Flight f755 = flightFactory.buildFlight(10, 135,210, path3, airportMap.get("STL"), airportMap.get("ORD"));
        Flight f755sec = flightFactory.buildFlight(11, 265,445, path3, airportMap.get("ORD"), airportMap.get("SAT"));
        Flight f408 = flightFactory.buildFlight(12,490,650, path3, airportMap.get("SAT"), airportMap.get("ORD"));
        Flight f408sec = flightFactory.buildFlight(13,705,830, path3, airportMap.get("ORD"), airportMap.get("PHL"));
        path3.setSetOfFlights(f407,f755,f755sec,f408,f408sec);
        flightFactory.setIdleTime(f407,f755,f755sec,f408,f408sec);

        Path path4 = new Path(4,a320111);

        Flight f876 = flightFactory.buildFlight(14,15,145, path4, airportMap.get("ORD"), airportMap.get("BOS"));
        Flight f413 = flightFactory.buildFlight(15,195,380, path4, airportMap.get("BOS"), airportMap.get("ORD"));
        Flight f413sec = flightFactory.buildFlight(16,445,720, path4, airportMap.get("ORD"), airportMap.get("SNA"));
        Flight f1262 = flightFactory.buildFlight(17, 770, 1000, path4, airportMap.get("SNA"), airportMap.get("ORD"));

        Path path5 = new Path(5,a320212);

        Flight f451 = flightFactory.buildFlight(18, 205,500, path5, airportMap.get("ORD"), airportMap.get("SFO"));
        Flight f554 = flightFactory.buildFlight(19, 565, 830, path5, airportMap.get("SFO"), airportMap.get("ORD"));
        path5.setSetOfFlights(f451, f554);
        flightFactory.setIdleTime(f451, f554);

        Path path6 = new Path(6, a320111);

        Flight f496 = flightFactory.buildFlight(20, 25,125, path6, airportMap.get("ORD"), airportMap.get("DCA"));
        Flight f1715 = flightFactory.buildFlight(21, 175, 305, path6, airportMap.get("DCA"), airportMap.get("ORD"));
        Flight f1715sec = flightFactory.buildFlight(22, 365, 610, path6, airportMap.get("ORD"), airportMap.get("LAS"));
        Flight f1708 = flightFactory.buildFlight(23, 660, 880, path6, airportMap.get("LAS"), airportMap.get("ORD"));
        path6.setSetOfFlights(f496, f1715, f1715sec, f1708);
        flightFactory.setIdleTime(f496, f1715, f1715sec, f1708);




        /*ArrayList<Flight> a = path1.getSetOfFlights();
        for(Flight flight : a){
            if (flight.getNextFlight() == null){
                System.out.println("Flight " + flight.getId() + " doesn't have next flight.");
            } else {
                System.out.println("Flight " + flight.getId() + " has next flight " + flight.getNextFlight().getId() + " ");
            }
        }
        */






    }
}
