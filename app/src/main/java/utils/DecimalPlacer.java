package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class DecimalPlacer {
	
	/* helper method to round float to 2 decimal places */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
