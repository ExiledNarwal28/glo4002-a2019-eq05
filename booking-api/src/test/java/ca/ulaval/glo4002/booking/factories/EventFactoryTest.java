package ca.ulaval.glo4002.booking.factories;

import ca.ulaval.glo4002.booking.domain.events.Event;
import ca.ulaval.glo4002.booking.domain.events.EventDate;
import ca.ulaval.glo4002.booking.dto.events.ProgramEventDto;
import ca.ulaval.glo4002.booking.enums.Activities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventFactoryTest {

    private EventFactory eventFactory;

    @BeforeEach
    void setUpFactory() {
        this.eventFactory = new EventFactory();
    }

    @Test
    void build_shouldBuildCorrectAmountOfEvents_whenThereIsOneEvent() {
        ProgramEventDto aEventDto = buildEventDto(new EventDate(EventDate.START_DATE), Activities.YOGA, "aArtist");

        List<Event> events = eventFactory.build(Collections.singletonList(aEventDto));

        assertEquals(1, events.size());
    }

    @Test
    void build_shouldBuildCorrectAmountOfEvents_whenThereAreMultipleEvents() {
        ProgramEventDto aEventDto = buildEventDto(new EventDate(EventDate.START_DATE), Activities.YOGA, "aArtist");
        ProgramEventDto anotherEventDto = buildEventDto(new EventDate(EventDate.START_DATE.plusDays(1)), Activities.YOGA, "anotherArtist");

        List<Event> events = eventFactory.build(Arrays.asList(aEventDto, anotherEventDto));

        assertEquals(2, events.size());
    }

    @Test
    void build_shouldBuildCorrectEventDate() {
        EventDate aEventDate = new EventDate(EventDate.START_DATE);
        ProgramEventDto aEventDto = buildEventDto(aEventDate, Activities.YOGA, "aArtist");

        List<Event> events = eventFactory.build(Collections.singletonList(aEventDto));

        assertEquals(aEventDate, events.get(0).getEventDate());
    }

    @Test
    void build_shouldBuildCorrectEventDates_whenThereAreMultipleEvents() {
        EventDate aEventDate = new EventDate(EventDate.START_DATE);
        EventDate anotherEventDate = new EventDate(EventDate.START_DATE.plusDays(1));
        ProgramEventDto aEventDto = buildEventDto(aEventDate, Activities.YOGA, "aArtist");
        ProgramEventDto anotherEventDto = buildEventDto(anotherEventDate, Activities.YOGA, "anotherArtist");

        List<Event> events = eventFactory.build(Arrays.asList(aEventDto, anotherEventDto));

        assertTrue(events.stream().anyMatch(event -> event.getEventDate().equals(aEventDate)));
        assertTrue(events.stream().anyMatch(event -> event.getEventDate().equals(anotherEventDate)));
    }

    @Test
    void build_shouldBuildCorrectActivity() {
        // TODO
    }

    @Test
    void build_shouldBuildCorrectActivities_whenThereAreMultipleEvents() {
        // TODO
    }

    @Test
    void build_shouldBuildCorrectArtist() {
        // TODO
    }

    @Test
    void build_shouldBuildCorrectArtists_whenThereAreMultipleEvents() {
        // TODO
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenEventDateIsInvalid() {
        // TODO
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenEventDateIsDuplicate() {
        // TODO
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenEventDateIsAbsent() {
        // TODO
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenPmIsDefinedTwice() {
        // TODO
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenAmIsDefinedTwice() {
        // TODO
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenAmIsArtist() {
        // TODO
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenPmIsActivity() {
        // TODO
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenArtistIsDuplicate() {
        // TODO
    }

    private ProgramEventDto buildEventDto(EventDate eventDate, Activities activity, String artist) {
        return new ProgramEventDto(eventDate.toString(), activity.toString(), artist);
    }
}