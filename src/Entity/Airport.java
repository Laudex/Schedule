package Entity;

public class Airport {
    public int id;
    public String name;
    public double congestion;

    public Airport(int id, String name, double congestion) {
        this.id = id;
        this.name = name;
        this.congestion = congestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCongestion() {
        return congestion;
    }

    public void setCongestion(double congestion) {
        this.congestion = congestion;
    }
}
