package converter;

import model.response.CurrentWeather;

public class CurrentWeatherToJsonConverter {
    public CurrentWeatherToJsonConverter(){}
    public String convertCurrentWeatherToJson(CurrentWeather currentWeather)
    {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("{\n");
        stringBuilder.append("\"temperature\":").append("\"").append(currentWeather.getTemperature()).append("\",\n").append("\"humidity\":").append(currentWeather.getHumidity()).append("\n}");
        return stringBuilder.toString();
    }
}
