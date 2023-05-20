package handler;

import database.exception.DatabaseException;
import handler.ApiRequestHandler;
import model.request.ApiRequest;
import model.request.WeatherByCityApiRequest;

public class WeatherByCityRequestHandler extends ApiRequestHandler {

    public WeatherByCityRequestHandler(){

    }

    public String handleWeatherRequest(ApiRequest apiRequest)
    {
        try {
            return this.converter.convertCurrentWeatherToJson(this.getWeatherProvider().provideWeatherByCity((WeatherByCityApiRequest)apiRequest));
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

}
