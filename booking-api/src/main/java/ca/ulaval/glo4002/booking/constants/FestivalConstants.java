package ca.ulaval.glo4002.booking.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FestivalConstants {

    public static class Dates {
        public static final LocalDate START_DATE = LocalDate.of(2050,7,17);
        public static final LocalDate END_DATE = LocalDate.of(2050,7,24);
        public static final LocalDateTime FESTIVAL_START_ORDER = LocalDateTime.of(2050, 1, 1, 0, 0, 0);
        public static final LocalDateTime FESTIVAL_END_ORDER = LocalDateTime.of(2050, 7, 16, 23, 59, 59);


        private Dates() {
            throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS_EXCEPTION_MESSAGE);
        }
    }

    private FestivalConstants() {
        throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS_EXCEPTION_MESSAGE);
    }
}
