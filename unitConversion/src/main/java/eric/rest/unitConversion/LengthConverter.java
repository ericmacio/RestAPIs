package eric.rest.unitConversion;

public class LengthConverter {

    static void kilometerToMile (ConversionDetails details) {
        float km = details.getFromValue();
        float miles = km * 0.621371f;
        details.setToValue(miles);
    }

    static void mileToKilometer (ConversionDetails details) {
        float miles = details.getFromValue();
        float km = miles * 1.60934f;
        details.setToValue(km);
    }
}
