package Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;

public class Flight {
    public int id; //в конструкторе
    public Path mainPath; //в конструкторе
    public int depTimeInMin; //в конструкторе
    public int plannedArrTimeInMin; //в конструкторе
    public int actualArrTimeinMin; // в setActualNonCruiseTime
    public int cruiseTime; //в конструкторе
    public int actualNonCruiseTime; // в setActualNonCruiseTime
    public double cruiseLenght; //в конструкторе
    public int cruiseTimeLower; // в setCruiseTimeLower
    public int cruiseTimeUpper; // setCruiseTimeUpper
    public Flight nextFlight; // //заполняется после добавления всех флайтов одного пути
    public int idleTime; //заполняется после добавления всех флайтов одного пути
    public ArrayList<Flight> pasConnected = new ArrayList<>(); //заполняется после добавления всех флайтов
    public Airport originAirport; //в конструкторе
    public Airport destinationAirport; //в конструкторе
    public double congestionOrigin; //в конструкторе
    public double congestionDestination; //в конструкторе
    public HashMap<Integer, Integer> turnTime = new HashMap<>(); //заполняется после заполнения листа p(pasConnected)
    public HashMap<Integer, Double> serviceLevel = new HashMap<>(); //считается после заполенения turnTime;
    //time window for departure time
    public int depTimeLower; //в конструкторе
    public int depTimeUpper; //в конструкторе
    public int arrTimeLower; //в конструкторе
    public int arrTimeUpper; //в конструкторе
    public int demand; // в setDemand
    public int turnAroundTime; //в setTurnAroundTime
    public HashMap<Integer, Double> weights = new HashMap<>();
    public boolean firstInPath; //в конструкторе

    public Flight(int id, int depTimeInMin, int plannedArrTimeInMin, Path mainPath, Airport originAirport, Airport destinationAirport, boolean firstInPath) {
        this.id = id;
        this.depTimeInMin = depTimeInMin;
        this.depTimeLower = depTimeInMin - 40;
        this.depTimeUpper = depTimeInMin + 40;
        this.plannedArrTimeInMin = plannedArrTimeInMin;
        this.arrTimeLower = plannedArrTimeInMin - 40;
        this.arrTimeUpper = plannedArrTimeInMin + 40;
        this.cruiseTime = plannedArrTimeInMin - depTimeInMin - 20;
        this.mainPath = mainPath;
        this.cruiseLenght = mainPath.getAssignedAircraftType().getMRCSpeed() * cruiseTime;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.congestionOrigin = originAirport.getCongestion();
        this.congestionDestination = destinationAirport.getCongestion();
        this.firstInPath = firstInPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Path getMainPath() {
        return mainPath;
    }

    public void setMainPath(Path mainPath) {
        this.mainPath = mainPath;
    }

    public int getDepTimeInMin() {
        return depTimeInMin;
    }

    public void setDepTimeInMin(int depTimeInMin) {
        this.depTimeInMin = depTimeInMin;
    }

    public int getPlannedArrTimeInMin() {
        return plannedArrTimeInMin;
    }

    public void setPlannedArrTimeInMin(int plannedArrTimeInMin) {
        this.plannedArrTimeInMin = plannedArrTimeInMin;
    }

    public int getActualArrTimeinMin() {
        return actualArrTimeinMin;
    }

    public void setActualArrTimeinMin(int actualArrTimeinMin) {
        this.actualArrTimeinMin = actualArrTimeinMin;
    }

    public int getCruiseTime() {
        return cruiseTime;
    }

    public void setCruiseTime(int cruiseTime) {
        this.cruiseTime = cruiseTime;
    }

    public int getActualNonCruiseTime() {
        return actualNonCruiseTime;
    }

    public void setActualNonCruiseTime(double scale, double tail) {
        double betta = tail * congestionOrigin * congestionOrigin * congestionDestination * congestionDestination;
        double timeInDouble = scale /((1-betta)*(1+betta));
        this.actualNonCruiseTime = (int)timeInDouble;
        this.actualArrTimeinMin = depTimeInMin + cruiseTime + actualNonCruiseTime;
    }

    public double getCruiseLenght() {
        return cruiseLenght;
    }

    public void setCruiseLenght(double cruiseLenght) {
        this.cruiseLenght = cruiseLenght;
    }

    public int getCruiseTimeLower() {
        return cruiseTimeLower;
    }

    public void setCruiseTimeLower(double cruiseTimeLower) {
        this.cruiseTimeLower = (int)cruiseTimeLower;
    }

    public int getCruiseTimeUpper() {
        return cruiseTimeUpper;
    }

    public void setCruiseTimeUpper(double cruiseTimeUpper) {
        this.cruiseTimeUpper = (int)cruiseTimeUpper;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(){
        this.idleTime = nextFlight.getDepTimeInMin() - this.depTimeInMin - this.turnAroundTime - cruiseTime - actualNonCruiseTime;
    }

    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

    public ArrayList<Flight> getPasConnected() {
        return pasConnected;
    }

    public void addToPasConnected(Flight flight) {
        pasConnected.add(flight);
    }

    public double getCongestionOrigin() {
        return congestionOrigin;
    }

    public void setCongestionOrigin(double congestionOrigin) {
        this.congestionOrigin = congestionOrigin;
    }

    public double getCongestionDestination() {
        return congestionDestination;
    }

    public void setCongestionDestination(double congestionDestination) {
        this.congestionDestination = congestionDestination;
    }

    public HashMap<Integer, Integer> getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(Flight flightCon) {
        int lowBound = 25;
        int uppBound = 40;
        UniformIntegerDistribution dist = new UniformIntegerDistribution(lowBound, uppBound);
        double sample = dist.sample();
        int turnTime = (int)sample;
        this.turnTime.put(flightCon.getId(), turnTime);
    }

    public HashMap<Integer, Double> getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(double serviceLevel, Flight flight) {

        this.serviceLevel.put(flight.getId(), serviceLevel);
    }

    public int getDepTimeLower() {
        return depTimeLower;
    }

    public void setDepTimeLower(int depTimeLower) {
        this.depTimeLower = depTimeLower;
    }

    public int getDepTimeUpper() {
        return depTimeUpper;
    }

    public void setDepTimeUpper(int depTimeUpper) {
        this.depTimeUpper = depTimeUpper;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand() {
        int lowBound = mainPath.getAssignedAircraftType().getLowBoundDemand();
        int uppBound = mainPath.getAssignedAircraftType().getUppBoundDemand();
        UniformIntegerDistribution dist = new UniformIntegerDistribution(lowBound, uppBound);
        double sample = dist.sample();
        this.demand = (int)sample;
    }

    public double getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(double baseTurnTime) {
        double time = baseTurnTime * congestionDestination * 0.70;
        this.turnAroundTime = (int)time;
    }

    public Flight getNextFlight() {
        return nextFlight;
    }

    public void setNextFlight(Flight nextFlight) {
        this.nextFlight = nextFlight;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public HashMap<Integer, Double> getWeights() {
        return weights;
    }

    public void setWeights(Flight flight, double weight) {
        this.weights.put(flight.getId(), weight);
    }

    public int getArrTimeLower() {
        return arrTimeLower;
    }

    public int getArrTimeUpper() {
        return arrTimeUpper;
    }


    public String toFile(){
        StringBuilder flight = new StringBuilder(id + " " + depTimeInMin + " " + plannedArrTimeInMin + " " + actualArrTimeinMin + " " + cruiseTime + " " + originAirport.getName() + " " + destinationAirport.getName() + " " +mainPath.getAssignedAircraftType().getTypeId());
        return flight.toString();
    }

    public boolean isFirstInPath() {
        return firstInPath;
    }
}
