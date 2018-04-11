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
        //  System.out.println("First constraint has error: " + validation);
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
        //   System.out.println("Second constraint has error: " + validation);
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
        //   System.out.println("Third constraint has error: " + validation);
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
        //   System.out.println("Fourth constraint has error: " + validation);
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
        //  System.out.println("Fifth constraint has error: " + validation);
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
        //  System.out.println("Sixth constraint has error: " + validation);
        return validation;
    }

    //ограничение (12)
    public double validateSeventhConstraint(ArrayList<Flight> flights) {
        double validation = 0;
        for (Flight flight : flights) {
            if (flight.getIdleTime() < 0) {
                validation = validation - flight.getIdleTime();
            }
        }
        //   System.out.println("Seventh constraint has error: " + validation);
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
        //System.out.println("Eighth constraint has error: " + validation);
        return validation;
    }

    public void firstValidation(ArrayList<Flight> flights, ArrayList<Flight> conFlights) {
        double totalValidation = 0;
        // totalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
        validation1 = validateFirstConstraint(conFlights);
        validation2 = validateSecondConstraint(conFlights);
        validation3 = validateThirdConstraint(flights);
        validation4 = validateFourthConstraint(flights);
        validation5 = validateFifthConstraint(flights);
        validation6 = validateSixthConstraint(flights);
        validation7 = validateSeventhConstraint(flights);
        validation8 = validateEighthConstraint(conFlights);
        System.out.println("First constraint has error: " + validation1);
        System.out.println("Second constraint has error: " + validation2);
        System.out.println("Third constraint has error: " + validation3);
        System.out.println("Fourth constraint has error: " + validation4);
        System.out.println("Fifth constraint has error: " + validation5);
        System.out.println("Sixth constraint has error: " + validation6);
        System.out.println("Seventh constraint has error: " + validation7);
        System.out.println("Eighth constraint has error: " + validation8);
        totalValidation = validation1 + validation2 + validation3 + validation4 + validation5 + validation6 + validation7 + validation8;

        System.out.println(totalValidation);
        localSearchExecute(flights, totalValidation, conFlights);

    }

    public void setNewIdleTime(int newIdleTime, Flight flight) {
        ArrayList<Flight> flightsInPath = flight.getMainPath().getSetOfFlights();
        int diff = newIdleTime - flight.getIdleTime();
        flight.setIdleTime(newIdleTime);
        for (Flight flightInPath : flightsInPath) {
            if (flightInPath.getDepTimeInMin() > flight.getDepTimeInMin()) {
                flightInPath.setDepTimeInMin(flightInPath.getDepTimeInMin() + diff);
                flightInPath.setPlannedArrTimeInMin(flightInPath.getPlannedArrTimeInMin() + diff);
                flightInPath.setActualArrTimeinMin(flightInPath.getActualArrTimeinMin() + diff);
            }
        }

    }

    public void setNewDepTime(int newDepTime, Flight flight) {
        ArrayList<Flight> flightsInPath = flight.getMainPath().getSetOfFlights();
        int diff = newDepTime - flight.getDepTimeInMin();
        flight.setDepTimeInMin(newDepTime);
        flight.setPlannedArrTimeInMin(flight.getPlannedArrTimeInMin() + diff);
        flight.setActualArrTimeinMin(flight.getActualArrTimeinMin() + diff);
        for (Flight flightInPath : flightsInPath) {
            if (flightInPath.getId() + 1 == flight.getId()) {
                flightInPath.setIdleTime(flightInPath.getIdleTime() + diff);
            }
            if (flightInPath.getDepTimeInMin() > flight.getDepTimeInMin()) {
                flightInPath.setDepTimeInMin(flightInPath.getDepTimeInMin() + diff);
                flightInPath.setPlannedArrTimeInMin(flightInPath.getPlannedArrTimeInMin() + diff);
                flightInPath.setActualArrTimeinMin(flightInPath.getActualArrTimeinMin() + diff);
            }
        }
    }

    public void setNewCruiseTime(int newCruiseTime, Flight flight) {
        ArrayList<Flight> flightsInPath = flight.getMainPath().getSetOfFlights();
        int diff = newCruiseTime - flight.getCruiseTime();
        flight.setIdleTime(flight.getIdleTime() - diff);
        flight.setCruiseTime(newCruiseTime);
        flight.setPlannedArrTimeInMin(flight.getPlannedArrTimeInMin() + diff);
        flight.setActualArrTimeinMin(flight.getActualArrTimeinMin() + diff);
    }

    public void setNewServiceLevel(double newServiceLevel, Flight flight, Flight nextFlight) {
        flight.getServiceLevel().put(nextFlight.getId(), newServiceLevel);
    }

    public void helpThirdConstraint(ArrayList<Flight> flights){
        for (Flight flight : flights) {
            if (flight.getCruiseTime() > flight.getCruiseTimeUpper()) {
                setNewCruiseTime(flight.getCruiseTimeUpper(), flight);
            } else if (flight.getCruiseTime() < flight.getCruiseTimeLower()) {
                setNewCruiseTime(flight.getCruiseTimeLower(), flight);
            }
        }
    }

    public void localSearchExecute(ArrayList<Flight> flights, double totalValidation, ArrayList<Flight> conFlights) {
        /*for (int i = 1; i <= 50; i++) {
            for (Flight flight : flights) {
                setNewIdleTime(flight.getIdleTime() + i, flight);
                //setNewDepTime(flight.getDepTimeInMin() + i, flight);
                double newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                if (newTotalValidation >= totalValidation) {
                    setNewIdleTime(flight.getIdleTime() - i + 1, flight);
                    newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if (newTotalValidation >= totalValidation) {
                        setNewIdleTime(flight.getIdleTime() + i, flight);
                    } else {
                        totalValidation = newTotalValidation;
                        System.out.println(newTotalValidation + "for Flight " + flight.getId() + " for I= " + i);
                    }
                } else {
                    totalValidation = newTotalValidation;
                    System.out.println(newTotalValidation + "for Flight " + flight.getId() + " for I= " + i);
                }
            }
        }*/

        for (int i = 1; i <= 30; i++) {
            for (Flight flight : flights) {
                //setNewIdleTime(flight.getIdleTime() + i, flight);
                setNewCruiseTime(flight.getCruiseTime() + i, flight);
                double newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                if (newTotalValidation >= totalValidation) {
                    setNewCruiseTime(flight.getCruiseTime() - i - i, flight);
                    newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if (newTotalValidation >= totalValidation) {
                        setNewCruiseTime(flight.getCruiseTime() + i, flight);
                    } else {
                        totalValidation = newTotalValidation;
                        //  System.out.println(newTotalValidation + "for Flight " + flight.getId() + " for I= " + i);
                    }
                } else {
                    totalValidation = newTotalValidation;
                    // System.out.println(newTotalValidation + "for Flight " + flight.getId() + " for I= " + i);
                }
            }
        }
        validation1 = validateFirstConstraint(conFlights);
        validation2 = validateSecondConstraint(conFlights);
        validation3 = validateThirdConstraint(flights);
        validation4 = validateFourthConstraint(flights);
        validation5 = validateFifthConstraint(flights);
        validation6 = validateSixthConstraint(flights);
        validation7 = validateSeventhConstraint(flights);
        validation8 = validateEighthConstraint(conFlights);
        System.out.println("First constraint has error: " + validation1);
        System.out.println("Second constraint has error: " + validation2);
        System.out.println("Third constraint has error: " + validation3);
        System.out.println("Fourth constraint has error: " + validation4);
        System.out.println("Fifth constraint has error: " + validation5);
        System.out.println("Sixth constraint has error: " + validation6);
        System.out.println("Seventh constraint has error: " + validation7);
        System.out.println("Eighth constraint has error: " + validation8);
        totalValidation = validation1 + validation2 + validation3 + validation4 + validation5 + validation6 + validation7 + validation8;
        System.out.println(totalValidation);

        for (double j = 0.02; j <= 0.49; j = j + 0.02) {
            for (Flight flight : conFlights) {
                ArrayList<Flight> p = flight.getPasConnected();
                for (Flight flightInP : p) {
                    setNewServiceLevel(flight.getServiceLevel().get(flightInP.getId()) + j, flight, flightInP);
                    double newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if (newTotalValidation >= totalValidation) {
                        // setNewServiceLevel(flight.getServiceLevel().get(flightInP.getId()) - j - j, flight, flightInP);
                        // newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                        //if(newTotalValidation >= totalValidation){
                        setNewServiceLevel(flight.getServiceLevel().get(flightInP.getId()) - j, flight, flightInP);
                        // } else {
                        //    totalValidation = newTotalValidation;
                        //  System.out.println(newTotalValidation + "for Flight " + flight.getId() + " for I= " + j);
                        //  }
                    } else {
                        totalValidation = newTotalValidation;
                        //  System.out.println(newTotalValidation + "for Flight " + flight.getId() + " for I= " + j);
                    }
                }
            }
        }

        validation1 = validateFirstConstraint(conFlights);
        validation2 = validateSecondConstraint(conFlights);
        validation3 = validateThirdConstraint(flights);
        validation4 = validateFourthConstraint(flights);
        validation5 = validateFifthConstraint(flights);
        validation6 = validateSixthConstraint(flights);
        validation7 = validateSeventhConstraint(flights);
        validation8 = validateEighthConstraint(conFlights);
        System.out.println("First constraint has error: " + validation1);
        System.out.println("Second constraint has error: " + validation2);
        System.out.println("Third constraint has error: " + validation3);
        System.out.println("Fourth constraint has error: " + validation4);
        System.out.println("Fifth constraint has error: " + validation5);
        System.out.println("Sixth constraint has error: " + validation6);
        System.out.println("Seventh constraint has error: " + validation7);
        System.out.println("Eighth constraint has error: " + validation8);
        totalValidation = validation1 + validation2 + validation3 + validation4 + validation5 + validation6 + validation7 + validation8;
        System.out.println(totalValidation);

        for (int i = 1; i < 30; i++) {
            for (Flight flight : flights) {
                if (flight.getId() != 1) {
                    setNewDepTime(flight.getDepTimeInMin() + i, flight);
                    double newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if(newTotalValidation >= totalValidation){
                        setNewDepTime(flight.getDepTimeInMin() - i - i, flight);
                        newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                        if(newTotalValidation >= totalValidation){
                            setNewDepTime(flight.getDepTimeInMin() + i, flight);
                        } else{
                            totalValidation = newTotalValidation;
                        }
                    } else {
                        totalValidation = newTotalValidation;
                    }
                }
            }
        }

        validation1 = validateFirstConstraint(conFlights);
        validation2 = validateSecondConstraint(conFlights);
        validation3 = validateThirdConstraint(flights);
        validation4 = validateFourthConstraint(flights);
        validation5 = validateFifthConstraint(flights);
        validation6 = validateSixthConstraint(flights);
        validation7 = validateSeventhConstraint(flights);
        validation8 = validateEighthConstraint(conFlights);
        System.out.println("First constraint has error: " + validation1);
        System.out.println("Second constraint has error: " + validation2);
        System.out.println("Third constraint has error: " + validation3);
        System.out.println("Fourth constraint has error: " + validation4);
        System.out.println("Fifth constraint has error: " + validation5);
        System.out.println("Sixth constraint has error: " + validation6);
        System.out.println("Seventh constraint has error: " + validation7);
        System.out.println("Eighth constraint has error: " + validation8);
        totalValidation = validation1 + validation2 + validation3 + validation4 + validation5 + validation6 + validation7 + validation8;
        System.out.println(totalValidation);

        helpThirdConstraint(flights);

        validation1 = validateFirstConstraint(conFlights);
        validation2 = validateSecondConstraint(conFlights);
        validation3 = validateThirdConstraint(flights);
        validation4 = validateFourthConstraint(flights);
        validation5 = validateFifthConstraint(flights);
        validation6 = validateSixthConstraint(flights);
        validation7 = validateSeventhConstraint(flights);
        validation8 = validateEighthConstraint(conFlights);
        System.out.println("First constraint has error: " + validation1);
        System.out.println("Second constraint has error: " + validation2);
        System.out.println("Third constraint has error: " + validation3);
        System.out.println("Fourth constraint has error: " + validation4);
        System.out.println("Fifth constraint has error: " + validation5);
        System.out.println("Sixth constraint has error: " + validation6);
        System.out.println("Seventh constraint has error: " + validation7);
        System.out.println("Eighth constraint has error: " + validation8);
        totalValidation = validation1 + validation2 + validation3 + validation4 + validation5 + validation6 + validation7 + validation8;
        System.out.println(totalValidation);

        for (double j = 0.02; j <= 0.49; j = j + 0.02) {
            for (Flight flight : conFlights) {
                ArrayList<Flight> p = flight.getPasConnected();
                for (Flight flightInP : p) {
                    setNewServiceLevel(flight.getServiceLevel().get(flightInP.getId()) + j, flight, flightInP);
                    double newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if (newTotalValidation >= totalValidation) {
                        // setNewServiceLevel(flight.getServiceLevel().get(flightInP.getId()) - j - j, flight, flightInP);
                        // newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                        //if(newTotalValidation >= totalValidation){
                        setNewServiceLevel(flight.getServiceLevel().get(flightInP.getId()) - j, flight, flightInP);
                        // } else {
                        //    totalValidation = newTotalValidation;
                        //  System.out.println(newTotalValidation + "for Flight " + flight.getId() + " for I= " + j);
                        //  }
                    } else {
                        totalValidation = newTotalValidation;
                        //  System.out.println(newTotalValidation + "for Flight " + flight.getId() + " for I= " + j);
                    }
                }
            }
        }
        validation1 = validateFirstConstraint(conFlights);
        validation2 = validateSecondConstraint(conFlights);
        validation3 = validateThirdConstraint(flights);
        validation4 = validateFourthConstraint(flights);
        validation5 = validateFifthConstraint(flights);
        validation6 = validateSixthConstraint(flights);
        validation7 = validateSeventhConstraint(flights);
        validation8 = validateEighthConstraint(conFlights);
        System.out.println("First constraint has error: " + validation1);
        System.out.println("Second constraint has error: " + validation2);
        System.out.println("Third constraint has error: " + validation3);
        System.out.println("Fourth constraint has error: " + validation4);
        System.out.println("Fifth constraint has error: " + validation5);
        System.out.println("Sixth constraint has error: " + validation6);
        System.out.println("Seventh constraint has error: " + validation7);
        System.out.println("Eighth constraint has error: " + validation8);
        totalValidation = validation1 + validation2 + validation3 + validation4 + validation5 + validation6 + validation7 + validation8;
        System.out.println(totalValidation);

        for (int i = 1; i < 30; i++) {
            for (Flight flight : flights) {
                if (flight.getId() != 1) {
                    setNewDepTime(flight.getDepTimeInMin() + i, flight);
                    double newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if(newTotalValidation >= totalValidation){
                        setNewDepTime(flight.getDepTimeInMin() - i - i, flight);
                        newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                        if(newTotalValidation >= totalValidation){
                            setNewDepTime(flight.getDepTimeInMin() + i, flight);
                        } else{
                            totalValidation = newTotalValidation;
                        }
                    } else {
                        totalValidation = newTotalValidation;
                    }
                }
            }
        }

        validation1 = validateFirstConstraint(conFlights);
        validation2 = validateSecondConstraint(conFlights);
        validation3 = validateThirdConstraint(flights);
        validation4 = validateFourthConstraint(flights);
        validation5 = validateFifthConstraint(flights);
        validation6 = validateSixthConstraint(flights);
        validation7 = validateSeventhConstraint(flights);
        validation8 = validateEighthConstraint(conFlights);
        System.out.println("First constraint has error: " + validation1);
        System.out.println("Second constraint has error: " + validation2);
        System.out.println("Third constraint has error: " + validation3);
        System.out.println("Fourth constraint has error: " + validation4);
        System.out.println("Fifth constraint has error: " + validation5);
        System.out.println("Sixth constraint has error: " + validation6);
        System.out.println("Seventh constraint has error: " + validation7);
        System.out.println("Eighth constraint has error: " + validation8);
        totalValidation = validation1 + validation2 + validation3 + validation4 + validation5 + validation6 + validation7 + validation8;
        System.out.println(totalValidation);

    }
}
