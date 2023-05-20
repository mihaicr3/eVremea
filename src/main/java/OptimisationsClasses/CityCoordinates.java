package OptimisationsClasses;

public class CityCoordinates {

    private float lat;
    private float lon;


    public CityCoordinates(){};
    public CityCoordinates(float lat, float lon) {
        this.lat=lat;
        this.lon=lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
