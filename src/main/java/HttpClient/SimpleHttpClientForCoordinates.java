package HttpClient;

import database.LogExceptionService;
import database.entity.LogException;
import database.exception.DatabaseException;
import model.response.CurrentWeather;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SimpleHttpClientForCoordinates {

    // De pe net + de la mine

    String link="https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m";

    public CurrentWeather doRequestForLatitudeAndLongitude(String latitude, String longitude){
        link=String.format(link, latitude, longitude);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(link)).build();
        CurrentWeather currentWeather=client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(SimpleHttpClientForCoordinates::getWeather)
                .join();

        return currentWeather;





    }


    public static CurrentWeather getWeather(String responseBody){
        JSONObject data = new JSONObject(responseBody);
        try {
            JSONObject data1 = data.getJSONObject("current_weather");

        }
        catch (Exception e)
        {
            LogExceptionService service=new LogExceptionService();
            try {
                LogException entry=service.saveException("NullCoordinates",new RuntimeException("Coordinates do not exist"));
            } catch (DatabaseException ex) {
                throw new RuntimeException(ex);
            }
        }
        JSONObject data1 = data.getJSONObject("current_weather");
        float temp = data1.getFloat("temperature");
        data = new JSONObject(responseBody);
        data1 = data.getJSONObject("hourly");
        JSONArray data2 = data1.getJSONArray("relativehumidity_2m");
        float humidity = data2.getFloat(0);
        return new CurrentWeather(temp,humidity);
    }
    public static float parseForTemp(String responseBody){
            JSONObject data = new JSONObject(responseBody);
            JSONObject data1 = data.getJSONObject("current_weather");
            float temp = data1.getFloat("temperature");
            return temp;

    }

    public static float parseForHumidty(String responseBody)
    {
           JSONObject data = new JSONObject(responseBody);
           JSONObject data1 = data.getJSONObject("hourly");
           JSONArray data2 = data1.getJSONArray("relativehumidity_2m");
           float humidity = data2.getFloat(0);
           return humidity;
    }


}




