package reactor;

import java.util.HashMap;

public class Reactor {
    private String name;
    private String reactorClass;
    private String country;
    private String region;
    private String operator;
    private double burnup;
    private int thermalCapacity;
    private int firstGridConnection;
    private HashMap<Integer, Double> loadFactorPerYear;
    private HashMap<Integer, Double> consumptionPerYear;

    public Reactor(String name, String reactorClass, String country, String region, String operator, int burnup, int thermalCapacity, int firstGridConnection, HashMap<Integer, Double> loadFactorPerYear) {
        this.name = name;
        this.reactorClass = reactorClass;
        this.country = country;
        this.region = region;
        this.operator = operator;
        this.burnup = burnup;
        this.thermalCapacity = thermalCapacity;
        this.firstGridConnection = firstGridConnection;
        this.loadFactorPerYear = loadFactorPerYear;
        this.consumptionPerYear = new HashMap<Integer, Double>();
    }

    public String getReactorClass() {
        return reactorClass;
    }

    public void setReactorClass(String reactorClass) {
        this.reactorClass = reactorClass;
    }

    public double getBurnup() {
        return burnup;
    }

    public void setBurnup(double burnup) {
        this.burnup = burnup;
    }
    public String getName() {
        return name;
    }
    public String getCountry() {return country;}

    public  String getRegion(){return region;}
    public String getOperator() {return operator;}
    public int getThermalCapacity() {return thermalCapacity;}
    public HashMap<Integer, Double> getLoadFactorPerYear() {return loadFactorPerYear;}

    public HashMap<Integer, Double> getConsumptionPerYear() {return consumptionPerYear;}

    public int getFirstGridConnection(){return firstGridConnection;}

    public void setLoadFactorPerYear(HashMap<Integer, Double> loadFactorPerYear) {
        this.loadFactorPerYear = loadFactorPerYear;
    }

    public void calculateConsumptionPerYear() {
        for (HashMap.Entry<Integer, Double> entry : loadFactorPerYear.entrySet()) {
            Integer year = entry.getKey();
            Double fLoad = entry.getValue();
            Double consumption = thermalCapacity / burnup * fLoad / 100000*365;
            if (year != firstGridConnection){consumption *= 3;}
            consumptionPerYear.put(year,consumption);
        }

    }
}
