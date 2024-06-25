package eric.rest.unitConversion;

public class UnitConverter {
    static void convert(ConversionDetails details) throws UnitConversionException {
        String fromUnit = details.getFromUnit();
        String toUnit = details.getToUnit();

        if(fromUnit.equals("km") && toUnit.equals("mile")) {
            LengthConverter.kilometerToMile(details);
        } else if (fromUnit.equals("mile") && toUnit.equals("km")) {
            LengthConverter.mileToKilometer(details);
        } else
            throw new UnitConversionException("Invalid input data");
    }
}
