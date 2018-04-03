package Factory;

import Entity.Airport;
import Entity.Flight;
import Entity.Path;

import java.util.ArrayList;

public class FlightFactory {
    //e^a
    public static double scaleParameter = 20;
    //betta
    public static double tailParameter = 0.01;

    public FlightFactory() {
    }

    public Flight buildFlight(int id, int depTimeInMin, int plannedArrTimeInMin, Path mainPath, Airport originAirport, Airport destinationAirport) {
        Flight flight = new Flight(id, depTimeInMin, plannedArrTimeInMin, mainPath, originAirport, destinationAirport);
        flight.setTurnAroundTime(mainPath.getAssignedAircraftType().getBaseTurnTime());
        flight.setActualNonCruiseTime(scaleParameter, tailParameter);
        flight.setDemand();
        return flight;
    }

    public void setIdleTime(Flight... flights) {
        for (int i = 0; i < flights.length; i++) {
            if (flights[i].getNextFlight() != null){
                flights[i].setIdleTime();
            }
        }
    }
    public void setTurnTime(ArrayList<Flight> flights){
        for (Flight flight : flights){
            if(!flight.getPasConnected().isEmpty()){
                ArrayList<Flight> pasConnected = flight.getPasConnected();
                for (Flight flightCon : pasConnected){
                    flight.setTurnTime(flightCon);
                }
            }
        }
    }

    public void setServiceLevel(ArrayList<Flight> flights){
        for (Flight flight : flights){
            if(!flight.getPasConnected().isEmpty()){
                ArrayList<Flight> pasConnected = flight.getPasConnected();
                for (Flight flightCon : pasConnected){
                    int first = flightCon.getDepTimeInMin() - flight.getDepTimeInMin() - flight.getTurnTime().get(flightCon.getId()) - flight.getCruiseTime();
                    double second = first / scaleParameter;
                    double betta = tailParameter * flight.getCongestionOrigin() * flight.getCongestionOrigin() * flight.getCongestionDestination() * flight.getCongestionDestination();
                    second = second * Math.pow(2, betta);
                    second = Math.pow(second, 1.0/betta);
                    second = 1.0/second;
                    double third = 1 - second;
                    int serviceLevel = (int)third;
                }
            }
        }
    }
}
