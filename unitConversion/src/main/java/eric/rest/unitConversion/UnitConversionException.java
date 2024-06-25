package eric.rest.unitConversion;

public class UnitConversionException extends Throwable {
    public UnitConversionException(String message) {
        super(message);
        System.out.println("Exception");
    }
}
