package ca.ulaval.glo4002.booking.constants;

import java.time.LocalDate;

public class FestivalConstants {

    public static class DateFestival {
        public static final LocalDate FESTIVAL_START_DATE = LocalDate.of(2050, 7, 17);
        public static final LocalDate FESTIVAL_END_DATE = LocalDate.of(2050, 7, 24);

        private DateFestival(){
            throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS_EXCEPTION_MESSAGE);
        }
    }

    private FestivalConstants(){
        throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS_EXCEPTION_MESSAGE);
    }
}
