package ca.ulaval.glo4002.booking.events;

import ca.ulaval.glo4002.booking.BookingConfiguration;
import ca.ulaval.glo4002.booking.exceptions.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventDateFactoryTest {

    EventDateFactory factory;
    private BookingConfiguration configuration;

    @BeforeEach
    void setUpFactory() {
        factory = new EventDateFactory(configuration);
    }

    @BeforeEach
    void setUpConfiguration() {
        configuration = mock(BookingConfiguration.class);

        when(configuration.getStartEventDate()).thenReturn(EventDate.getStartEventDate());
        when(configuration.getEndEventDate()).thenReturn(EventDate.getEndEventDate());
    }

    @Test
    void build_shouldThrowInvalidEventDateException_whenEventDateIsUnderBounds() {
        EventDate aUnderBoundEventDate  = configuration.getStartEventDate().minusDays(1);

        assertThrows(InvalidEventDateException.class, () -> factory.build(aUnderBoundEventDate.toString()));
    }

    @Test
    void build_shouldThrowInvalidEventDateException_whenEventDateIsOverBounds() {
        EventDate aOverBoundEventDate  = configuration.getEndEventDate().plusDays(1);

        assertThrows(InvalidEventDateException.class, () -> factory.build(aOverBoundEventDate.toString()));
    }

    @Test
    void build_shouldThrowInvalidFormatException_whenEventDateIsInvalid() {
        String anInvalidDate = "anInvalidDate";

        assertThrows(InvalidFormatException.class, () -> factory.build(anInvalidDate));
    }
}