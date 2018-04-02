package Factory;

import Entity.Airport;
import Entity.Flight;
import Entity.Path;

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
}
