package Entity;


public class AircraftType {
    public int typeId;
    public int capacity;
    public int mass;
    public double surface;
    public double CD0;
    public double CD2;
    public double Cf1;
    public double Cf2;
    public double CfCR;
    public double MRCSpeed;
    public int baseTurnTime;
    public int idleTimeCost;
    public int numberOfAircrafts;
    public int lowBoundDemand;
    public int uppBoundDemand;

    public AircraftType(int typeId, int capacity, int mass, double surface, double CD0, double CD2, double cf1, double cf2, double cfCR, double MRCSpeed, int baseTurnTime, int idleTimeCost, int numberOfAircrafts, int lowBoundDemand, int uppBoundDemand) {
        this.typeId = typeId;
        this.capacity = capacity;
        this.mass = mass;
        this.surface = surface;
        this.CD0 = CD0;
        this.CD2 = CD2;
        Cf1 = cf1;
        Cf2 = cf2;
        CfCR = cfCR;
        this.MRCSpeed = MRCSpeed;
        this.baseTurnTime = baseTurnTime;
        this.idleTimeCost = idleTimeCost;
        this.numberOfAircrafts = numberOfAircrafts;
        this.lowBoundDemand = lowBoundDemand;
        this.uppBoundDemand = uppBoundDemand;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMass() {
        return mass;
    }

    public double getSurface() {
        return surface;
    }

    public double getCD0() {
        return CD0;
    }

    public double getCD2() {
        return CD2;
    }

    public double getCf1() {
        return Cf1;
    }

    public double getCf2() {
        return Cf2;
    }

    public double getCfCR() {
        return CfCR;
    }

    public double getMRCSpeed() {
        return MRCSpeed;
    }

    public int getBaseTurnTime() {
        return baseTurnTime;
    }

    public int getIdleTimeCost() {
        return idleTimeCost;
    }

    public int getNumberOfAircrafts() {
        return numberOfAircrafts;
    }

    public int getLowBoundDemand() {
        return lowBoundDemand;
    }

    public int getUppBoundDemand() {
        return uppBoundDemand;
    }
}
