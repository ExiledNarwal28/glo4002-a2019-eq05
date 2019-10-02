package ca.ulaval.glo4002.booking.constants;

public class VendorConstants {

    public static final Long TEAM_VENDOR_ID = 1L;
    public static final String TEAM_VENDOR_CODE = "TEAM";

    private VendorConstants(){
        throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS_ERROR);
    }
}