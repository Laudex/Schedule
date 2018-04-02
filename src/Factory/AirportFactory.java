package Factory;

import Entity.Airport;

import java.util.ArrayList;
import java.util.HashMap;

public class AirportFactory{
    public AirportFactory() {
    }
    public HashMap<String, Airport> buildAirportMap(){
        HashMap<String, Airport> airportMap = new HashMap<>();
        ArrayList<Airport> airports = init();
        for(Airport airport : airports){
            airportMap.put(airport.getName(), airport);
        }


        return airportMap;
    }

    public ArrayList<Airport> init(){
        ArrayList<Airport> airports = new ArrayList<>();
        Airport mia = new Airport(1,"MIA", 1.96);
        airports.add(mia);
        Airport ord = new Airport(2,"ORD", 1.88);
        airports.add(ord);
        Airport lax = new Airport(3,"LAX", 1.82);
        airports.add(lax);
        Airport den = new Airport(4,"DEN", 1.82);
        airports.add(den);
        Airport dfw = new Airport(5,"DFW", 1.74);
        airports.add(dfw);
        Airport lga = new Airport(6,"LGA", 1.69);
        airports.add(lga);
        Airport bos = new Airport(7,"BOS", 1.69);
        airports.add(bos);
        Airport atl = new Airport(8,"ATL", 1.64);
        airports.add(atl);
        Airport phx = new Airport(9,"PHX", 1.56);
        airports.add(phx);
        Airport las = new Airport(10,"LAS", 1.56);
        airports.add(las);
        Airport sfo = new Airport(11,"SFO", 1.44);
        airports.add(sfo);
        Airport msp = new Airport(12,"MSP", 1.32);
        airports.add(msp);
        Airport phl = new Airport(13,"PHL", 1.32);
        airports.add(phl);
        Airport ewr = new Airport(14,"EWR", 1.25);
        airports.add(ewr);
        Airport fll = new Airport(15,"FLL", 1.25);
        airports.add(fll);
        Airport slc = new Airport(16,"SLC", 1.17);
        airports.add(slc);

        Airport dca = new Airport(17,"DCA", 1.17);
        airports.add(dca);
        Airport san = new Airport(18,"SAN", 1.10);
        airports.add(san);
        Airport stl = new Airport(19,"STL", 1.10);
        airports.add(stl);
        Airport mci = new Airport(20,"MCI", 1.04);
        airports.add(mci);
        Airport aus = new Airport(21,"AUS", 1.00);
        airports.add(aus);
        Airport rdu = new Airport(22,"RDU", 1.00);
        airports.add(rdu);
        Airport msy = new Airport(23,"MSY", 0.96);
        airports.add(msy);
        Airport sna = new Airport(24,"SNA", 0.96);
        airports.add(sna);
        Airport sat = new Airport(25,"SAT", 0.90);
        airports.add(sat);
        Airport rsw = new Airport(26,"RSW", 0.90);
        airports.add(rsw);
        Airport sju = new Airport(27,"SJU", 0.85);
        airports.add(sju);
        Airport pbi = new Airport(28,"PBI", 0.81);
        airports.add(pbi);
        Airport tus = new Airport(29,"TUS", 0.77);
        airports.add(tus);
        Airport mco = new Airport(30,"MCO", 0.72);
        airports.add(mco);
        Airport ege = new Airport(31,"EGE", 0.72);
        airports.add(ege);
        Airport hdn = new Airport(32,"HDN", 0.64);
        airports.add(hdn);

        return airports;
    }
}
