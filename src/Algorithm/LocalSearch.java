package Algorithm;

import Entity.Flight;

import java.util.ArrayList;

public class LocalSearch {
    public static double serviceLevelBound = 0.8;
    public static double minimumServiceLevelForEachFlight = 0.5;
    //e^a
    public static double scaleParameter = 20;
    //betta
    public static double tailParameter = 0.01;
    public double validation1 = 0;
    public double validation2 = 0;
    public double validation3 = 0;
    public int validation4 = 0;
    public int validation5 = 0;
    public int validation6 = 0;
    public int validation7 = 0;
    public int validation8 = 0;

    // ограничение (5)
    public double validateFirstConstraint(ArrayList<Flight> flights) {
        double validation = 0;
        for (Flight flight : flights) {
            ArrayList<Flight> flightsConnected = flight.getPasConnected();
            for (Flight flightCon : flightsConnected) {
                double betta = tailParameter * flight.getCongestionOrigin() * flight.getCongestionOrigin() * flight.getCongestionDestination() * flight.getCongestionDestination();
                double leftPart = Math.pow(2, betta) * Math.pow(1 - flight.getServiceLevel().get(flightCon.getId()), betta);
                leftPart = scaleParameter / leftPart;
                double rightPart = flightCon.getDepTimeInMin() - flight.getDepTimeInMin() - flight.getTurnTime().get(flightCon.getId()) - flight.getCruiseTime();
                if (leftPart > rightPart) {
                    validation = validation + leftPart - rightPart;
                }
            }
        }
        return validation;
    }

    // ограничение (6)
    public double validateSecondConstraint(ArrayList<Flight> flights){
        double validation = 0;
        for (Flight flight : flights){
            ArrayList<Flight> flightsConnected = flight.getPasConnected();
            for (Flight flightCon : flightsConnected){
                if (flight.getServiceLevel().get(flightCon.getId()) < minimumServiceLevelForEachFlight){
                    validation = validation + minimumServiceLevelForEachFlight - flight.getServiceLevel().get(flightCon.getId());
                }
            }
        }
        return validation;

    }

    // ограничение (7)
    public double validateThirdConstraint(ArrayList<Flight> flights){
        double validation = 0;
        for (Flight flight : flights){
            if (flight.getCruiseTime() >  flight.getCruiseTimeUpper()){
                System.out.println(flight.getCruiseTime());
                System.out.println(flight.getId());
                System.out.println(flight.getCruiseLenght());
                System.out.println(flight.getMainPath().getAssignedAircraftType().getMRCSpeed());
                System.out.println(flight.getCruiseTimeUpper());
                validation = validation + flight.getCruiseTime() - flight.getCruiseTimeUpper();
            } else if (flight.getCruiseTime() < flight.getCruiseTimeLower()){
                validation = validation + flight.getCruiseTimeLower() - flight.getCruiseTime();
            }
        }
        System.out.println(validation);
        return validation;
    }

    public void firstValidation(ArrayList<Flight> flights, ArrayList<Flight> conFlights) {
        validation1 = validateFirstConstraint(conFlights);
        validation2 = validateSecondConstraint(conFlights);
        validation3 = validateThirdConstraint(flights);

    }
}
