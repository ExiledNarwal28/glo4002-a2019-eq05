package ca.ulaval.glo4002.booking.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateConstants {

    public static final LocalDate START_DATE = LocalDate.of(2050,7,17);
    public static final LocalDate END_DATE = LocalDate.of(2050,7,24);
    public static final LocalDateTime ORDER_START_DATE_TIME = LocalDateTime.of(2050, 1, 1, 0, 0, 0);
    public static final LocalDateTime ORDER_END_DATE_TIME = LocalDateTime.of(2050, 7, 16, 23, 59, 59);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    public static final DateTimeFormatter MESSAGE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMMM d y").withLocale(Locale.ENGLISH);

    private DateConstants() {
        throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS_ERROR);
    }
}