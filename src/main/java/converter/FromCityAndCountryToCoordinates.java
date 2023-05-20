package converter;

import OptimisationsClasses.CityCoordinates;
import database.entity.LogException;
import database.exception.DatabaseException;
import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import database.LogExceptionService;
public class FromCityAndCountryToCoordinates {
    String link="https://geocode.maps.co/search?q=%s+%s";
   public CityCoordinates getLatitudeAndLongitude(String city,String country){


       city=city.replace(" ","+");
       link=String.format(link,city,country);
        link=String.format(link,city,country);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(link)).build();

        try {
            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(FromCityAndCountryToCoordinates::getCoordinates)
                    .join();
        }
        catch (Exception e)
        {

           LogExceptionService service=new LogExceptionService();
            try {
                LogException entry=service.saveException(city,new RuntimeException("Wrong city inserted"));
            } catch (DatabaseException ex) {
                throw new RuntimeException(ex);
            }

            return new CityCoordinates(-200,200);

        }



    }
//   public float getLongitude(String city,String country) throws DatabaseException {
//        city=city.replace(" ","+");
//        link=String.format(link,city,country);
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(link)).build();
//       LogExceptionService logExceptionService = new LogExceptionService();
//
//        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenApply(FromCityAndCountryToCoordinates::parseForLongitude)
//                .join();
//
//
//
//    }

    public static CityCoordinates getCoordinates(String responseBody)
    {
        JSONArray data=new JSONArray(responseBody);
        JSONObject data1=data.getJSONObject(0);
        float lat= data1.getFloat("lat");

        data=new JSONArray(responseBody);
        data1=data.getJSONObject(0);
        float lon= data1.getFloat("lon");

        return new CityCoordinates(lat,lon);

    }

//    public static float parseForLatitude(String responseBody)
//    {
//
//
//        JSONArray data=new JSONArray(responseBody);
//        JSONObject data1=data.getJSONObject(0);
//        return data1.getFloat("lat");
//
//    }
//
//    public static float parseForLongitude(String responseBody)
//    {
//        JSONArray data=new JSONArray(responseBody);
//        JSONObject data1=data.getJSONObject(0);
//        return data1.getFloat("lon");
//    }

}
