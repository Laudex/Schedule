package Algorithm;

import Entity.Flight;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void helpThirdConstraint(ArrayList<Flight> flights) {
        for (Flight flight : flights) {
            if (flight.getCruiseTime() > flight.getCruiseTimeUpper()) {
                setNewCruiseTime(flight.getCruiseTimeUpper(), flight);
            } else if (flight.getCruiseTime() < flight.getCruiseTimeLower()) {
                setNewCruiseTime(flight.getCruiseTimeLower(), flight);
            }
        }
    }

    public void helpEighthConstraint(ArrayList<Flight> flights, ArrayList<Flight> conFlights) {

    }

    public double searchWithinCruiseTime(ArrayList<Flight> flights, double totalValidation, ArrayList<Flight> conFlights) {
        double newTotalValidation = 0;
        for (Flight flight : flights) {
            boolean atLeastOneBetter = false;
            int currentBetterCruiseTime = 0;
            int currentCruiseTime = flight.getCruiseTime();
            for (int i = flight.getCruiseTimeLower(); i <= flight.getCruiseTimeUpper(); i++) {
                setNewCruiseTime(i, flight);
                newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                if (newTotalValidation < totalValidation) {
                    currentBetterCruiseTime = i;
                    atLeastOneBetter = true;
                    totalValidation = newTotalValidation;
                }
            }
            if (!atLeastOneBetter) {
                setNewCruiseTime(currentCruiseTime, flight);
            } else {
                setNewCruiseTime(currentBetterCruiseTime, flight);
            }
        }
        return totalValidation;
    }

    public double searchWithinDepTime(ArrayList<Flight> flights, double totalValidation, ArrayList<Flight> conFlights) {
        double newTotalValidation = 0;
        for (Flight flight : flights) {
            if (!flight.isFirstInPath()) {
                boolean atLeastOneBetter = false;
                int currentBetterDepTime = 0;
                int currentDepTime = flight.getDepTimeInMin();
                for (int i = flight.getDepTimeLower(); i <= flight.getDepTimeUpper(); i++) {
                    setNewDepTime(i, flight);
                    newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if (newTotalValidation < totalValidation) {
                        currentBetterDepTime = i;
                        atLeastOneBetter = true;
                        totalValidation = newTotalValidation;
                    }
                }
                if (!atLeastOneBetter) {
                    setNewDepTime(currentDepTime, flight);
                } else {
                    setNewDepTime(currentBetterDepTime, flight);
                }
            }
        }
        return totalValidation;
    }

    public double searchWithinIdleTime(ArrayList<Flight> flights, double totalValidation, ArrayList<Flight> conFlights) {
        double newTotalValidation = 0;
        for (Flight flight : flights) {
            boolean atLeastOneBetter = false;
            int currentBetterIdleTime = 0;
            int currentIdleTime = flight.getIdleTime();
            for (int i = 0; i <= 50; i++) {
                setNewIdleTime(i, flight);
                newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                if (newTotalValidation < totalValidation) {
                    currentBetterIdleTime = i;
                    atLeastOneBetter = true;
                    totalValidation = newTotalValidation;
                }
            }
            if (!atLeastOneBetter) {
                setNewIdleTime(currentIdleTime, flight);
            } else {
                setNewIdleTime(currentBetterIdleTime, flight);
            }
        }
        return totalValidation;
    }

    public double searchWithinServiceLevel(ArrayList<Flight> flights, double totalValidation, ArrayList<Flight> conFlights) {
        double newTotalValidation = 0;
        for (Flight flight : conFlights) {
            ArrayList<Flight> p = flight.getPasConnected();
            for (Flight pFlight : p) {
                boolean atLeastOneBetter = false;
                double currentBetterServiceLevel = 0;
                double currentServiceLevel = flight.getServiceLevel().get(pFlight.getId());
                for (double i = 0.5; i < 1; i = i + 0.001) {
                    setNewServiceLevel(i, flight, pFlight);
                    newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if (newTotalValidation < totalValidation) {
                        currentBetterServiceLevel = i;
                        atLeastOneBetter = true;
                        totalValidation = newTotalValidation;
                    }
                }
                if (!atLeastOneBetter) {
                    setNewServiceLevel(currentServiceLevel, flight, pFlight);
                } else {
                    setNewServiceLevel(currentBetterServiceLevel, flight, pFlight);
                }
            }
        }
        return totalValidation;
    }

    public double searchWithinServiceLevelUpdate(ArrayList<Flight> flights, double totalValidation, ArrayList<Flight> conFlights) {
        double newTotalValidation;
        for (double i = 0.5; i < 1; i = i + 0.001) {
            for (Flight flight : conFlights) {
                ArrayList<Flight> p = flight.getPasConnected();
                for (Flight pFlight : p) {
                    double currentServiceLevel = flight.getServiceLevel().get(pFlight.getId());
                    setNewServiceLevel(i, flight, pFlight);
                    newTotalValidation = validateFirstConstraint(conFlights) + validateSecondConstraint(conFlights) + validateThirdConstraint(flights) + validateFourthConstraint(flights) + validateFifthConstraint(flights) + validateSixthConstraint(flights) + validateSeventhConstraint(flights) + validateEighthConstraint(conFlights);
                    if (newTotalValidation < totalValidation) {
                        totalValidation = newTotalValidation;
                    } else {
                        setNewServiceLevel(currentServiceLevel, flight, pFlight);
                    }
                }
            }

        }
        return totalValidation;
    }

    public void writeToFile(ArrayList<Flight> flights, ArrayList<Flight> conFlights) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("results.txt", "UTF-8");
            writer.write("id   Departure Time   Planned Arr Time   Actual Arr Time     Cruise Time   Origin Airport   Destination Airport\n");
            for (Flight flight : flights) {
                writer.write(flight.toFile());
                writer.write("\n");
            }
            for (Flight flight : conFlights) {
                ArrayList<Flight> p = flight.getPasConnected();
                for (Flight flightInP : p) {
                    writer.write("Flight " + flight.getId() + " connects with flight " + flightInP.getId() + " with Service Level " + flight.getServiceLevel().get(flightInP.getId()));
                    writer.write("\n");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.close();
    }

    public void localSearchExecute(ArrayList<Flight> flights, double totalValidation, ArrayList<
            Flight> conFlights) {
        for (int i = 1; i <= 200; i++) {
            //totalValidation = searchWithinServiceLevel(flights, totalValidation, conFlights);
            totalValidation = searchWithinServiceLevelUpdate(flights, totalValidation, conFlights);
            totalValidation = searchWithinDepTime(flights, totalValidation, conFlights);
            totalValidation = searchWithinIdleTime(flights, totalValidation, conFlights);
            totalValidation = searchWithinCruiseTime(flights, totalValidation, conFlights);
            totalValidation = searchWithinServiceLevel(flights, totalValidation, conFlights);
           // totalValidation = searchWithinDepTime(flights, totalValidation, conFlights);
            if (totalValidation <= 0.01) {
                System.out.println("Iteration: " + i);
                writeToFile(flights, conFlights);
                break;
            }
        }
        // System.out.println(totalValidation);

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

        /*

        double newSecTotalValidation = searchWithinDepTime(flights, totalValidation, conFlights);
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

        double newThirdTotalValidation = searchWithinServiceLevel(flights, totalValidation, conFlights);
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

        double newFourthTotalValidation = searchWithinIdleTime(flights, totalValidation, conFlights);
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
        */


    }
}
