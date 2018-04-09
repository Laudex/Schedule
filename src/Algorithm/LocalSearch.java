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
    public double validation4 = 0;
    public double validation5 = 0;
    public double validation6 = 0;
    public double validation7 = 0;
    public double validation8 = 0;

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
    public double validateSecondConstraint(ArrayList<Flight> flights) {
        double validation = 0;
        for (Flight flight : flights) {
            ArrayList<Flight> flightsConnected = flight.getPasConnected();
            for (Flight flightCon : flightsConnected) {
                if (flight.getServiceLevel().get(flightCon.getId()) < minimumServiceLevelForEachFlight) {
                    validation = validation + minimumServiceLevelForEachFlight - flight.getServiceLevel().get(flightCon.getId());
                }
            }
        }
        return validation;

    }

    // ограничение (7)
    public double validateThirdConstraint(ArrayList<Flight> flights) {
        double validation = 0;
        for (Flight flight : flights) {
            if (flight.getCruiseTime() > flight.getCruiseTimeUpper()) {
                validation = validation + flight.getCruiseTime() - flight.getCruiseTimeUpper();
            } else if (flight.getCruiseTime() < flight.getCruiseTimeLower()) {
                validation = validation + flight.getCruiseTimeLower() - flight.getCruiseTime();
            }
        }
        return validation;
    }

    // ограничение (8)
    public double validateFourthConstraint(ArrayList<Flight> flights) {
        double validation = 0;
        for (Flight flight : flights) {
            if (flight.getNextFlight() != null) {
                double leftPart = flight.getNextFlight().getDepTimeInMin() - flight.getDepTimeInMin() - flight.getTurnAroundTime() - flight.getCruiseTime() - flight.getActualNonCruiseTime() - flight.getIdleTime();
                if (leftPart > 0) {
                    validation = validation + leftPart;
                } else if (leftPart < 0) {
                    validation = validation - leftPart;
                }
            }
        }
        return validation;
    }

    //ограничение (9)
    public double validateFifthConstraint(ArrayList<Flight> flights) {
        double validation = 0;
        for (Flight flight : flights) {
            if (flight.getDepTimeInMin() > flight.getDepTimeUpper()) {
                validation = validation + flight.getDepTimeInMin() - flight.getDepTimeUpper();
            } else if (flight.getDepTimeInMin() < flight.getDepTimeLower()) {
                validation = validation + flight.getDepTimeLower() - flight.getDepTimeInMin();
            }
        }
        return validation;
    }

    //ограничение (10)
    public double validateSixthConstraint(ArrayList<Flight> flights) {
        double validation = 0;
        for (Flight flight : flights) {
            double middlePart = flight.getDepTimeInMin() + flight.getCruiseTime() + flight.getActualNonCruiseTime();
            if (middlePart > flight.getArrTimeUpper()) {
                validation = validation + middlePart - flight.getArrTimeUpper();
            } else if (middlePart < flight.getArrTimeLower()) {
                validation = validation + flight.getArrTimeLower() - middlePart;
            }
        }
        return validation;
    }

    //ограничение (11)
    public double validateSeventhConstraint(ArrayList<Flight> flights) {
        double validation = 0;
        for (Flight flight : flights) {
            if (flight.getIdleTime() < 0) {
                validation = validation - flight.getIdleTime();
            }
        }
        return validation;
    }

    //ограничение (14)
    public double validateEighthConstraint(ArrayList<Flight> flights) {
        double validation = 0;
        double serviceLevel = 0;
        for (Flight flight : flights) {
            ArrayList<Flight> conFlights = flight.getPasConnected();
            for (Flight conFlight : conFlights) {
                serviceLevel = serviceLevel + flight.getWeights().get(conFlight.getId()) * flight.getServiceLevel().get(conFlight.getId());
            }
        }
        if (serviceLevel < serviceLevelBound) {
            validation = validation + serviceLevelBound - serviceLevel;
        }
        return validation;
    }

    public void firstValidation(ArrayList<Flight> flights, ArrayList<Flight> conFlights) {
        double totalValidation = 0;
        totalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
        /*validation1 = validateFirstConstraint(conFlights);
        validation2 = validateSecondConstraint(conFlights);
        validation3 = validateThirdConstraint(flights);
        validation4 = validateFourthConstraint(flights);
        validation5 = validateFifthConstraint(flights);
        validation6 = validateSixthConstraint(flights);
        validation7 = validateSeventhConstraint(flights);
        validation8 = validateEighthConstraint(conFlights);
        */
        System.out.println(totalValidation);
        localSearchExecute(flights, totalValidation, conFlights);

    }

    public void localSearchExecute(ArrayList<Flight> flights, double totalValidation, ArrayList<Flight> conFlights) {
        //int i = 1;
        for(int i = 1; i <=20; i++) {
            for (Flight flight : flights) {
                flight.setIdleTime(flight.getIdleTime() + i);
                double newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                if (newTotalValidation >= totalValidation) {
                    flight.setIdleTime(flight.getIdleTime() - i + 1);
                    newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if (newTotalValidation >= totalValidation) {
                        flight.setIdleTime(flight.getIdleTime() + i);
                    } else {
                        System.out.println(newTotalValidation + "for Flight " + flight.getId());
                    }
                } else {
                    System.out.println(newTotalValidation + "for Flight " + flight.getId());
                }
            }
        }

    }
}
