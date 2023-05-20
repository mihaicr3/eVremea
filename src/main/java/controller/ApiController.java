package controller;

import converter.CurrentWeatherToJsonConverter;
import model.request.ApiRequest;
import model.request.WeatherByCityApiRequest;
import model.request.WeatherByCoordinatesApiRequest;
import org.json.JSONObject;
import spark.Spark;
import handler.*;
import weatherPersistentDatabase.CityInsertIntoDatabase;
import weatherPersistentDatabase.CitySelectFromDatabase;
import weatherPersistentDatabase.CoordinatesInsertIntoDatabase;
import weatherPersistentDatabase.CoordinatesSelectFromDatabase;

public class ApiController {
    static ApiRequestHandler apiRequestHandlerByCity = new WeatherByCityRequestHandler();
    static ApiRequestHandler getApiRequestHandlerByCoordinates = new WeatherByCoordinatesRequestHandler();

    public ApiController() {

    }

    public void startSparkHttpServer() {
        Spark.get("/api/v1/weather/cities/country/:country/city/:city", (request, response) -> {
            String city = request.params(":city");
            String country = request.params(":country");
            ApiRequest apiRequest = new WeatherByCityApiRequest(city, country);
            response.type("application/json");
            response.status(200);
            String s=apiRequestHandlerByCity.handleWeatherRequest(apiRequest);
            JSONObject json=new JSONObject(s);
            float temp=json.getFloat("temperature");
            float hum=json.getFloat("humidity");
            CityInsertIntoDatabase.insertInDatabase(city,temp,hum);


            return json;
        });
        Spark.get("/api/v1/weather/coordinates/latitude/:lat/longitude/:long", (request, response) -> {

            String longitude = request.params(":long");
            String latitude = request.params(":lat");
            ApiRequest apiRequest = new WeatherByCoordinatesApiRequest(latitude, longitude);
            response.type("application/json");
            response.status(200);
            String s= getApiRequestHandlerByCoordinates.handleWeatherRequest(apiRequest);
            JSONObject json=new JSONObject(s);
            float temp=json.getFloat("temperature");
            float hum=json.getFloat("humidity");
            CoordinatesInsertIntoDatabase.insertInDatabase(latitude,longitude,temp,hum);
            return json;
        });
        Spark.get("/api/v1/weather/cities/country/:country/city/:city/timestamp/:timestamp", (request, response) -> {
            String timestamp1 = request.params(":timestamp");
            long timestamp=Long.parseLong(timestamp1);
            String city = request.params(":city");
            String country = request.params(":country");
            ApiRequest apiRequest = new WeatherByCityApiRequest(city, country);
            response.type("application/json");
            response.status(200);
            String s=apiRequestHandlerByCity.handleWeatherRequest(apiRequest);
            JSONObject json=new JSONObject(s);
            float temp=json.getFloat("temperature");
            float hum=json.getFloat("humidity");

            try {
                CurrentWeatherToJsonConverter currentWeatherToJsonConverter = new CurrentWeatherToJsonConverter();
                return currentWeatherToJsonConverter.convertCurrentWeatherToJson(CitySelectFromDatabase.selectWeather(city, timestamp));

            }catch (Exception e)
            {
                CityInsertIntoDatabase.insertInDatabase(city,temp,hum);
                CurrentWeatherToJsonConverter currentWeatherToJsonConverter = new CurrentWeatherToJsonConverter();
                return currentWeatherToJsonConverter.convertCurrentWeatherToJson(CitySelectFromDatabase.selectWeather(city, timestamp));
            }




        });
        Spark.get("/api/v1/weather/coordinates/latitude/:lat/longitude/:long/timestamp/:timestamp", (request, response) -> {

            String longitude = request.params(":long");
            String latitude = request.params(":lat");
            String timestamp1 = request.params(":timestamp");
            long timestamp=Long.parseLong(timestamp1);
            ApiRequest apiRequest = new WeatherByCoordinatesApiRequest(latitude, longitude);
            response.type("application/json");
            response.status(200);
            String s= getApiRequestHandlerByCoordinates.handleWeatherRequest(apiRequest);
            JSONObject json=new JSONObject(s);
            float temp=json.getFloat("temperature");
            float hum=json.getFloat("humidity");

            try {
                CurrentWeatherToJsonConverter currentWeatherToJsonConverter = new CurrentWeatherToJsonConverter();
                return currentWeatherToJsonConverter.convertCurrentWeatherToJson(CoordinatesSelectFromDatabase.selectWeather(latitude,longitude, timestamp));

            }catch (Exception e)
            {
                CoordinatesInsertIntoDatabase.insertInDatabase(latitude,longitude,temp,hum);
                CurrentWeatherToJsonConverter currentWeatherToJsonConverter = new CurrentWeatherToJsonConverter();
                return currentWeatherToJsonConverter.convertCurrentWeatherToJson(CoordinatesSelectFromDatabase.selectWeather(latitude,longitude, timestamp));
            }
        });

    }
}
