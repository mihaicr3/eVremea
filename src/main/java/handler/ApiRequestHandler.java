package handler;

import model.request.ApiRequest;
import converter.CurrentWeatherToJsonConverter;
import weather.provider.WeatherProvider;
public abstract class ApiRequestHandler {
        private WeatherProvider weatherProvider=new WeatherProvider();
        protected CurrentWeatherToJsonConverter converter=new CurrentWeatherToJsonConverter();
        public ApiRequestHandler()
        {

        }

    public WeatherProvider getWeatherProvider() {
        return this.weatherProvider;
    }
    public abstract String handleWeatherRequest(ApiRequest var1);
}
