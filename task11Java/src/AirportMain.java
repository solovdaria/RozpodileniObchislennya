import java.util.ArrayList;

public class AirportMain {
    public static void main(String[] args) throws InterruptedException {
        final Airport airport=new Airport();
        final ArrayList<City> routes=new ArrayList<City>();
        final ArrayList<Plane> planes = new ArrayList<Plane>();

        try {
            routes.add(new City("Dnepr",2000,1));
            routes.add(new City("Odessa", 2200,2));
            routes.add(new City("Kyiv", 1800,3));
            routes.add(new City("Moscow",780,4));
            routes.add(new City("Lviv",1000,5));
        }
        catch (ParametersException e){
            e.printStackTrace();
        }

        try {
            planes.add(new Plane(airport, 2500, 200));
            planes.add(new Plane(airport, 5000, 180));
            planes.add(new Plane(airport, 4200, 220));
            planes.add(new Plane(airport, 3000, 210));
            planes.add(new Plane(airport, 3200, 160));
        }
        catch (ParametersException e){
            e.printStackTrace();
        };
        for (int i=0;i<routes.size(); i++){
            planes.get(i).setRoute(routes.get(i));
        }

        for (Plane plane: planes) {
            if (plane.getRoute()!=null)
                plane.start();
            Thread.sleep(200);
        }
    }
}