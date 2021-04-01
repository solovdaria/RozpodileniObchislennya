public class Plane extends Thread{
    private Airport airport;
    private int maxDistance;
    private int capacity;
    private City route;

    public Plane(Airport airport, int maxDistance, int capacity) throws ParametersException {
        if ((maxDistance<0)||(capacity<0))
            throw new ParametersException("Wrong Parameters");

        this.airport=airport;
        this.maxDistance=maxDistance;
        this.capacity=capacity;
    }

    public void run() {
        try {
            while(true) {
                Gate gate = airport.getGate(maxDistance - route.getDistance());
                System.out.println("Plane " + this.getRoute().getRouteID()
                        + " from " + getRoute().getCity() + " arrived for boarding:   Terminal "
                        + gate.getGateTerminalId() + " Gate " + gate.getGateId());
                gate.using(capacity);
                System.out.println("Plane " + this.getRoute().getRouteID()
                        + " flies away to " + getRoute().getCity() + ":   Terminal "
                        + gate.getGateTerminalId()  + " Gate " + gate.getGateId() );
                airport.returnGate(gate);
            }
        }
        catch (ResourсeException e) {
            System.out.println("Plane " + this.getRoute().getRouteID()
                    + " from " + getRoute().getCity() + " disappeared from radar!!!!!!!"
                    + e.getMessage());
        }
    }

    public void setRoute(City route) {
        if ((this.getDistantZone()<route.getDistanceZone())||(maxDistance<route.getDistance()))
            System.out.println("Plane (capacity: "+capacity+", max range : "+maxDistance
                    +") will not fly to "+route.getCity());
        else{
            this.route=route;
        }
    }

    public int getDistantZone() {
        if (maxDistance<2000)
            return 1;
        else if ((maxDistance>=2000)&&(maxDistance<4000))
            return 2;
        else
            return 3;
    }

    public City getRoute() {
        return route;
    }
}
