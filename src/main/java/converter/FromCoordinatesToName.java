package converter;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class FromCoordinatesToName {




    public static String transformToName(String lat,String lon)
    {
        while(lat.length()>4)
            lat=lat.substring(0,4);
        if(lon.length()>4)
        lon=lon.substring(0,4);

        return lat+" "+lon;

    }


}
