public class City {
    private String name;
    private int distance;
    private int routeID;

    public City(String name, int distance, int routeID) throws ParametersException {
        if (distance<0) throw new ParametersException("Wrong parameters");

        this.name=name;
        this.distance=distance;
        this.routeID=routeID;
    }

    public int getDistanceZone(){
        if (distance<2000)
            return 1;
        else if ((distance>=2000)&&(distance<4000))
            return 2;
        else
            return 3;
    }

    public String getCity() {
        return name;
    }

    public int getDistance() {
        return distance;
    }

    public int getRouteID() {
        return routeID;
    }
}
