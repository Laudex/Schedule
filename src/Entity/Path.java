package Entity;

import java.util.ArrayList;
import java.util.TreeSet;


public class Path {
    public int id;
    public ArrayList<Flight> setOfFlights;
    public AircraftType assignedAircraftType;

    public Path(int id, AircraftType assignedAircraftType) {
        this.id = id;
        this.assignedAircraftType = assignedAircraftType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Flight> getSetOfFlights() {
        return setOfFlights;
    }

    public void setSetOfFlights(Flight ... flights) {
        ArrayList<Flight> set = new ArrayList<>();
        boolean prevExist = false;
        Flight prevFlight;
        for (int i = 0; i < flights.length; i++){
            if (i+1 != flights.length){
                flights[i].setNextFlight(flights[i+1]);
            } else {
                flights[i].setNextFlight(null);
            }
            set.add(flights[i]);
        }
        this.setOfFlights = set;
    }

    public AircraftType getAssignedAircraftType() {
        return assignedAircraftType;
    }

    public void setAssignedAircraftType(AircraftType assignedAircraftType) {
        this.assignedAircraftType = assignedAircraftType;
    }
}
