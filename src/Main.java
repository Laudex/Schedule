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

        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<Flight> flights = new ArrayList<>();

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

        flights.add(f2303);
        flights.add(f2336);
        flights.add(f1053);
        flights.add(f336);
        flights.add(f336sec);

        Path path2 = new Path(2,b767300);
        Flight f1341 = flightFactory.buildFlight(6, 150, 385, path2, airportMap.get("ORD"), airportMap.get("SFO"));
        Flight f348 = flightFactory.buildFlight(7, 430, 695, path2, airportMap.get("SFO"), airportMap.get("ORD"));
        Flight f1521 = flightFactory.buildFlight(8, 775,1010, path2, airportMap.get("ORD"), airportMap.get("TUS"));
        path2.setSetOfFlights(f1341,f348,f1521);
        flightFactory.setIdleTime(f1341, f348, f1521);

        flights.add(f1341);
        flights.add(f348);
        flights.add(f1521);

        Path path3 = new Path(3, a320212);

        Flight f407 = flightFactory.buildFlight(9, 0,70, path3, airportMap.get("ORD"), airportMap.get("STL"));
        Flight f755 = flightFactory.buildFlight(10, 135,210, path3, airportMap.get("STL"), airportMap.get("ORD"));
        Flight f755sec = flightFactory.buildFlight(11, 265,445, path3, airportMap.get("ORD"), airportMap.get("SAT"));
        Flight f408 = flightFactory.buildFlight(12,490,650, path3, airportMap.get("SAT"), airportMap.get("ORD"));
        Flight f408sec = flightFactory.buildFlight(13,705,830, path3, airportMap.get("ORD"), airportMap.get("PHL"));
        path3.setSetOfFlights(f407,f755,f755sec,f408,f408sec);
        flightFactory.setIdleTime(f407,f755,f755sec,f408,f408sec);

        flights.add(f407);
        flights.add(f755);
        flights.add(f755sec);
        flights.add(f408);
        flights.add(f408sec);

        Path path4 = new Path(4,a320111);

        Flight f876 = flightFactory.buildFlight(14,15,145, path4, airportMap.get("ORD"), airportMap.get("BOS"));
        Flight f413 = flightFactory.buildFlight(15,195,380, path4, airportMap.get("BOS"), airportMap.get("ORD"));
        Flight f413sec = flightFactory.buildFlight(16,445,720, path4, airportMap.get("ORD"), airportMap.get("SNA"));
        Flight f1262 = flightFactory.buildFlight(17, 770, 1000, path4, airportMap.get("SNA"), airportMap.get("ORD"));
        path4.setSetOfFlights(f876, f413, f413sec, f1262);
        flightFactory.setIdleTime(f876, f413, f413sec, f1262);

        flights.add(f876);
        flights.add(f413);
        flights.add(f413sec);
        flights.add(f1262);

        Path path5 = new Path(5,a320212);

        Flight f451 = flightFactory.buildFlight(18, 205,500, path5, airportMap.get("ORD"), airportMap.get("SFO"));
        Flight f554 = flightFactory.buildFlight(19, 565, 830, path5, airportMap.get("SFO"), airportMap.get("ORD"));
        path5.setSetOfFlights(f451, f554);
        flightFactory.setIdleTime(f451, f554);

        flights.add(f451);
        flights.add(f554);

        Path path6 = new Path(6, a320111);

        Flight f496 = flightFactory.buildFlight(20, 25,125, path6, airportMap.get("ORD"), airportMap.get("DCA"));
        Flight f1715 = flightFactory.buildFlight(21, 175, 305, path6, airportMap.get("DCA"), airportMap.get("ORD"));
        Flight f1715sec = flightFactory.buildFlight(22, 365, 610, path6, airportMap.get("ORD"), airportMap.get("LAS"));
        Flight f1708 = flightFactory.buildFlight(23, 660, 880, path6, airportMap.get("LAS"), airportMap.get("ORD"));
        path6.setSetOfFlights(f496, f1715, f1715sec, f1708);
        flightFactory.setIdleTime(f496, f1715, f1715sec, f1708);

        flights.add(f496);
        flights.add(f1715);
        flights.add(f1715sec);
        flights.add(f1708);

        Path path7 = new Path(7, b767300);

        Flight f1425 = flightFactory.buildFlight(24, 125,405, path7, airportMap.get("ORD"), airportMap.get("SNA"));
        Flight f556 = flightFactory.buildFlight(25, 460, 600, path7, airportMap.get("SNA"), airportMap.get("ORD"));
        Flight f1940 = flightFactory.buildFlight(26, 785,965, path7, airportMap.get("ORD"), airportMap.get("MIA"));
        path7.setSetOfFlights(f1425,f556,f1940);
        flightFactory.setIdleTime(f1425,f556,f1940);

        flights.add(f1425);
        flights.add(f556);
        flights.add(f1940);

        Path path8 = new Path(8, b767300);

        Flight f2460 = flightFactory.buildFlight(27,25, 190, path8, airportMap.get("ORD"), airportMap.get("RSW"));
        Flight f564 = flightFactory.buildFlight(28, 240,425, path8, airportMap.get("RSW"), airportMap.get("ORD"));
        Flight f1446 = flightFactory.buildFlight(29, 515, 680, path8, airportMap.get("ORD"), airportMap.get("EWR"));
        Flight f1411 = flightFactory.buildFlight(30, 745, 910, path8, airportMap.get("EWR"), airportMap.get("ORD"));
        path8.setSetOfFlights(f2460,f564,f1446,f1411);
        flightFactory.setIdleTime(f2460,f564,f1446,f1411);

        flights.add(f2460);
        flights.add(f564);
        flights.add(f1446);
        flights.add(f1411);

        Path path9 = new Path(9, a320111);

        Flight f1021 = flightFactory.buildFlight(31, 130,375, path9, airportMap.get("ORD"), airportMap.get("LAS"));
        Flight f1544 = flightFactory.buildFlight(32, 425, 645, path9, airportMap.get("LAS"), airportMap.get("ORD"));
        Flight f1544sec = flightFactory.buildFlight(33, 700, 800, path9, airportMap.get("ORD"), airportMap.get("DCA"));
        path9.setSetOfFlights(f1021, f1544, f1544sec);
        flightFactory.setIdleTime(f1021, f1544, f1544sec);

        flights.add(f1021);
        flights.add(f1544);
        flights.add(f1544sec);

        Path path10 = new Path(10, a320212);

        Flight f1823 = flightFactory.buildFlight(34, 180, 355, path10, airportMap.get("ORD"), airportMap.get("PBI"));
        Flight f2067 = flightFactory.buildFlight(35, 400, 600, path10, airportMap.get("PBI"), airportMap.get("ORD"));
        Flight f2067sec = flightFactory.buildFlight(36, 655, 725, path10, airportMap.get("ORD"), airportMap.get("STL"));
        Flight f1186 = flightFactory.buildFlight(37, 770, 845, path10, airportMap.get("STL"), airportMap.get("ORD"));
        path10.setSetOfFlights(f1823,f2067, f2067sec, f1186);
        flightFactory.setIdleTime(f1823,f2067, f2067sec, f1186);

        flights.add(f1823);
        flights.add(f2067);
        flights.add(f2067sec);
        flights.add(f1186);

        Path path11 = new Path(11, a320111);

        Flight f2363 = flightFactory.buildFlight(38, 210, 380, path11, airportMap.get("ORD"), airportMap.get("HDN"));
        Flight f2318 = flightFactory.buildFlight(39, 440, 610, path11, airportMap.get("HDN"), airportMap.get("ORD"));
        path11.setSetOfFlights(f2363, f2318);
        flightFactory.setIdleTime(f2363, f2318);

        flights.add(f2363);
        flights.add(f2318);

        Path path12 = new Path(12, a320212);

        Flight f2345 = flightFactory.buildFlight(40, 655, 810, path12, airportMap.get("ORD"), airportMap.get("DFW"));
        Flight f2374 = flightFactory.buildFlight(41, 860, 1000, path12, airportMap.get("DFW"), airportMap.get("ORD"));
        path12.setSetOfFlights(f2345, f2374);
        flightFactory.setIdleTime(f2345, f2374);

        flights.add(f2345);
        flights.add(f2374);


        paths.add(path1);
        paths.add(path2);
        paths.add(path3);
        paths.add(path4);
        paths.add(path5);
        paths.add(path6);
        paths.add(path7);
        paths.add(path8);
        paths.add(path9);
        paths.add(path10);
        paths.add(path11);
        paths.add(path12);

        for (Flight flightStart : flights){
            for (Flight flightFinish : flights){
                if (flightStart.getPlannedArrTimeInMin() + 40 >= flightFinish.getDepTimeInMin() && flightStart.getPlannedArrTimeInMin() <= flightFinish.getDepTimeInMin() && flightStart.getDestinationAirport().getId() == flightFinish.getOriginAirport().getId()){
                    flightStart.addToPasConnected(flightFinish);
                }
            }
        }

        flightFactory.setTurnTime(flights);

        for(Flight f : flights){
            ArrayList<Flight> p = f.getPasConnected();
            if (!p.isEmpty()){
                for(Flight flight : p){
                    System.out.println(f.getId() + " linked with " + flight.getId() + " with TurnTime = " + f.getTurnTime().get(flight.getId()));
                }
            }
        }




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
