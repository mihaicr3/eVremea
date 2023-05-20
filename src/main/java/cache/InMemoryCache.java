package cache;

import HttpClient.SimpleHttpClientForCoordinates;
import OptimisationsClasses.CityCoordinates;
import converter.FromCityAndCountryToCoordinates;
import database.exception.DatabaseException;
import model.response.CurrentWeather;
import java.util.*;



public class InMemoryCache {

    static Map<String, CurrentWeather> cities = new HashMap<String, CurrentWeather>();

    public static void addToCache(String city, CurrentWeather currentWeather) {

        cities.put(city, currentWeather);

    }

    public static int checkCache(String city) {
        if (cities.containsKey(city)) {
            return 1;
        } else
            return 0;
    }

    public static CurrentWeather weatherThrow(String city) {
        return cities.get(city);
    }

    public static synchronized void updateCache () {


        for(Map.Entry<String,CurrentWeather> city:cities.entrySet())
        {


            String cityName=city.getKey();
            if(Character.isDigit(cityName.charAt(0)))
            {


                String[] splitCoords =cityName.split("\\s+");
                String lat=splitCoords[0];
                String lon=splitCoords[1];


                SimpleHttpClientForCoordinates simpleHttpClientForCoordinates=new SimpleHttpClientForCoordinates();
                city.setValue(simpleHttpClientForCoordinates.doRequestForLatitudeAndLongitude(lat,lon));



            }
            else
            {
                String country="";
                FromCityAndCountryToCoordinates fromCityAndCountryToCoordinates = new FromCityAndCountryToCoordinates();

                CityCoordinates cityCoordinates=new CityCoordinates();
                cityCoordinates=fromCityAndCountryToCoordinates.getLatitudeAndLongitude(cityName,country);
                float longitudeFloat =cityCoordinates.getLon();

                float latitudeFloat = cityCoordinates.getLat();
                String longitude = String.valueOf(longitudeFloat);
                String latitude = String.valueOf(latitudeFloat);

                SimpleHttpClientForCoordinates simpleHttpClientForTemp = new SimpleHttpClientForCoordinates();


                 city.setValue(simpleHttpClientForTemp.doRequestForLatitudeAndLongitude(latitude, longitude));

            }
            System.out.println(cities);
        }

    }

}
