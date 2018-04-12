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

    public Flight buildFlight(int id, int depTimeInMin, int plannedArrTimeInMin, Path mainPath, Airport originAirport, Airport destinationAirport, boolean firstInPath) {
        Flight flight = new Flight(id, depTimeInMin, plannedArrTimeInMin, mainPath, originAirport, destinationAirport, firstInPath);
        flight.setTurnAroundTime(mainPath.getAssignedAircraftType().getBaseTurnTime());
        flight.setActualNonCruiseTime(scaleParameter, tailParameter);
        flight.setDemand();
        return flight;
    }

    public void setIdleTime(Flight... flights) {
        for (int i = 0; i < flights.length; i++) {
            if (flights[i].getNextFlight() != null) {
                flights[i].setIdleTime();
            }
        }
    }

    public ArrayList<Flight> getConnectedFlights(ArrayList<Flight> flights) {
        ArrayList<Flight> connectedFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (!flight.getPasConnected().isEmpty()) {
                connectedFlights.add(flight);
            }
        }
        return connectedFlights;
    }

    public void setTurnTime(ArrayList<Flight> flights) {
        for (Flight flight : flights) {
            ArrayList<Flight> pasConnected = flight.getPasConnected();
            for (Flight flightCon : pasConnected) {
                flight.setTurnTime(flightCon);
            }
        }
    }

    public void setServiceLevel(ArrayList<Flight> flights) {
        for (Flight flight : flights) {
            ArrayList<Flight> pasConnected = flight.getPasConnected();
            for (Flight flightCon : pasConnected) {
                flight.setServiceLevel(0.5, flightCon);
            }
        }
    }

    public void setWeights(ArrayList<Flight> flights) {
        int fullDemand = 0;
        for (Flight flight : flights) {
            ArrayList<Flight> pasConnected = flight.getPasConnected();
            for (Flight flightCon : pasConnected) {
                int demand1 = flight.getDemand();
                int demand2 = flightCon.getDemand();
                if (demand1 > demand2) {
                    fullDemand = fullDemand + demand2;
                } else {
                    fullDemand = fullDemand + demand1;
                }

            }
        }
        //System.out.println(fullDemand);
        for (Flight flight : flights) {
            ArrayList<Flight> pasConnected = flight.getPasConnected();
            for (Flight flightCon : pasConnected) {
                double weight;
                double demand1 = (double)flight.getDemand();
                double demand2 = (double)flightCon.getDemand();
                if (demand1 > demand2) {
                    weight = demand2 / fullDemand;
                } else {
                    weight = demand1 / fullDemand;
                }
                flight.setWeights(flightCon, weight);
            }
        }
    }
    public void setCruiseTimeBounds(ArrayList<Flight> flights){
        for(Flight flight : flights){
            double upper = flight.getCruiseLenght() / flight.getMainPath().getAssignedAircraftType().getMRCSpeed();
            double lower = upper * 0.85;
            upper = Math.round(upper);
            flight.setCruiseTimeUpper(upper);
            flight.setCruiseTimeLower(lower);
        }
    }
}
