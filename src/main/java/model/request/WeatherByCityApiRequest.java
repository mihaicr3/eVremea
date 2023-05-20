package model.request;

public class WeatherByCityApiRequest implements ApiRequest {
    private String city;
    private String country;
    public WeatherByCityApiRequest(String city,String country)
    {
        this.city = city; this.country=country;
    }
    public String getCity()
    {
        return this.city;
    }
    public void setCity(String city)
    {
        this.city=city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
