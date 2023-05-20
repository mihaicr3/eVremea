package weather.provider;

import HttpClient.SimpleHttpClientForCoordinates;
import OptimisationsClasses.CityCoordinates;
import cache.InMemoryCache;
import converter.FromCityAndCountryToCoordinates;
import converter.FromCoordinatesToName;
import database.exception.DatabaseException;
import model.response.CurrentWeather;
import model.request.WeatherByCityApiRequest;
import model.request.WeatherByCoordinatesApiRequest;


public class WeatherProvider {
    public WeatherProvider(){}

    public CurrentWeather provideWeatherByCity(WeatherByCityApiRequest weatherByCityApiRequest) throws DatabaseException {


        String city=weatherByCityApiRequest.getCity();
        String country=weatherByCityApiRequest.getCountry();

        if(InMemoryCache.checkCache(city)==1)
        {
            return InMemoryCache.weatherThrow(city);
        }

        else{
            FromCityAndCountryToCoordinates fromCityAndCountryToCoordinates = new FromCityAndCountryToCoordinates();


            CityCoordinates cityCoordinates=new CityCoordinates();

            cityCoordinates=fromCityAndCountryToCoordinates.getLatitudeAndLongitude(city,country);



            float longitudeFloat = cityCoordinates.getLon();
            float latitudeFloat = cityCoordinates.getLat();


            ///// Aceeasi modalitate ca la coordonate


            String longitude = String.valueOf(longitudeFloat);
            String latitude = String.valueOf(latitudeFloat);

            SimpleHttpClientForCoordinates simpleHttpClientForTemp = new SimpleHttpClientForCoordinates();


            CurrentWeather currentWeather = simpleHttpClientForTemp.doRequestForLatitudeAndLongitude(latitude, longitude);
            InMemoryCache.addToCache(city,currentWeather);
            return currentWeather;
        }

    }
    public CurrentWeather provideWeatherByCoordinates(WeatherByCoordinatesApiRequest weatherByCoordinatesApiRequest)
    {
       String latitude= weatherByCoordinatesApiRequest.getLatitude();

       String longitude=weatherByCoordinatesApiRequest.getLongitude();

       /// Made up name for the city to be stored in the same hashmap ( up to 2 to 4 digits)
       String city= FromCoordinatesToName.transformToName(latitude,longitude);


        if(InMemoryCache.checkCache(city)==1)
        {

            return InMemoryCache.weatherThrow(city);

        }
        else {
            SimpleHttpClientForCoordinates simpleHttpClientForTemp = new SimpleHttpClientForCoordinates();

            CurrentWeather currentWeather = simpleHttpClientForTemp.doRequestForLatitudeAndLongitude(latitude, longitude);

            InMemoryCache.addToCache(city,currentWeather);

            return currentWeather;

        }
    }
}
