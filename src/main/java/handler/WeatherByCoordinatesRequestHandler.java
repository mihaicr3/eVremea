package handler;

import handler.ApiRequestHandler;
import model.request.ApiRequest;
import model.request.WeatherByCoordinatesApiRequest;

public class WeatherByCoordinatesRequestHandler extends ApiRequestHandler {
     public WeatherByCoordinatesRequestHandler(){
     }
     public String handleWeatherRequest(ApiRequest apiRequest)
     {
          return this.converter.convertCurrentWeatherToJson(this.getWeatherProvider().provideWeatherByCoordinates((WeatherByCoordinatesApiRequest)apiRequest));
     }
}
