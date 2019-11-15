package ca.ulaval.glo4002.booking.events;

import ca.ulaval.glo4002.booking.activities.Activities;
import ca.ulaval.glo4002.booking.artists.ArtistService;
import ca.ulaval.glo4002.booking.artists.BookingArtist;
import ca.ulaval.glo4002.booking.program.InvalidProgramException;
import ca.ulaval.glo4002.booking.program.ProgramEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventFactoryTest {

    private EventFactory eventFactory;
    private ArtistService artistService;

    @BeforeEach
    void setUpFactory() {
        artistService = mock(ArtistService.class);

        this.eventFactory = new EventFactory(artistService);
    }

    @Test
    void build_shouldBuildCorrectAmountOfEvents() {
        List<ProgramEventDto> aProgramDto = buildProgramDto(Activities.YOGA, "aArtist");
        Integer expectedSize = aProgramDto.size();

        List<Event> events = eventFactory.build(aProgramDto);

        assertEquals(expectedSize, events.size());
    }

    @Test
    void build_shouldBuildCorrectEventDates() {
        List<EventDate> expectedEventDates = EventDate.getFullFestivalEventDates();
        List<ProgramEventDto> aProgramDto = buildProgramDto(Activities.YOGA, "aArtist");

        List<Event> events = eventFactory.build(aProgramDto);

        assertTrue(events.stream().allMatch(event -> expectedEventDates.contains(event.getEventDate())));
    }

    @ParameterizedTest
    @EnumSource(Activities.class)
    void build_shouldBuildCorrectActivity(Activities activity) {
        List<ProgramEventDto> aProgramDto = buildProgramDto(activity, "aArtist");

        List<Event> events = eventFactory.build(aProgramDto);

        assertEquals(activity, events.get(0).getActivity());
    }

    @Test
    void build_shouldBuildCorrectActivities_whenThereAreMultipleEvents() {
        Activities firstEventActivity = Activities.YOGA;
        Activities otherEventsActivity = Activities.CARDIO;
        List<ProgramEventDto> aProgramDto = buildProgramDto(otherEventsActivity, "aArtist");
        aProgramDto.set(0, buildEventDto(new EventDate(EventDate.START_DATE), firstEventActivity, "aArtist"));

        List<Event> events = eventFactory.build(aProgramDto);

        assertEquals(firstEventActivity, events.get(0).getActivity());
        assertTrue(events.subList(1, events.size() - 1).stream().allMatch(event -> event.getActivity().equals(otherEventsActivity)));
    }

    @Test
    void build_shouldBuildCorrectArtist() {
        String artistName = "aArtist";
        List<ProgramEventDto> aProgramDto = buildProgramDto(Activities.YOGA, artistName);
        BookingArtist expectedArtist = mock(BookingArtist.class);
        when(artistService.getByName(artistName + 0)).thenReturn(expectedArtist);

        List<Event> events = eventFactory.build(aProgramDto);

        assertEquals(expectedArtist, events.get(0).getArtist());
    }

    @Test
    void build_shouldBuildCorrectArtists_whenThereAreMultipleEvents() {
        String firstArtistName = "aFirstArtist";
        List<ProgramEventDto> aProgramDto = buildProgramDto(Activities.YOGA, firstArtistName);
        BookingArtist expectedFirstArtist = mock(BookingArtist.class);
        BookingArtist expectedOtherArtist = mock(BookingArtist.class);
        when(artistService.getByName(firstArtistName)).thenReturn(expectedFirstArtist);
        when(artistService.getByName(not(eq(firstArtistName)))).thenReturn(expectedOtherArtist);
        aProgramDto.set(0, buildEventDto(new EventDate(EventDate.START_DATE), Activities.YOGA, firstArtistName));

        List<Event> events = eventFactory.build(aProgramDto);

        assertEquals(expectedFirstArtist, events.get(0).getArtist());
        assertTrue(events.subList(1, events.size() - 1).stream().allMatch(event -> event.getArtist().equals(expectedOtherArtist)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenEventDateIsInvalid() {
        String anInvalidDate = "anInvalidDate";
        ProgramEventDto aEventDto = new ProgramEventDto(anInvalidDate, Activities.YOGA.toString(), "aArtist");

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Collections.singletonList(aEventDto)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenEventDateIsUnderBounds() {
        String anUnderBoundsEventDate = EventDate.START_DATE.minusDays(1).toString();
        ProgramEventDto aEventDto = new ProgramEventDto(anUnderBoundsEventDate, Activities.YOGA.toString(), "aArtist");

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Collections.singletonList(aEventDto)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenEventDateIsOverBounds() {
        String anOverBoundsEventDate = EventDate.END_DATE.plusDays(1).toString();
        ProgramEventDto aEventDto = new ProgramEventDto(anOverBoundsEventDate, Activities.YOGA.toString(), "aArtist");

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Collections.singletonList(aEventDto)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenEventDateIsDuplicate() {
        EventDate aEventDate = new EventDate(EventDate.START_DATE);
        ProgramEventDto aEventDto = buildEventDto(aEventDate, Activities.YOGA, "aArtist");
        ProgramEventDto anotherEventDto = buildEventDto(aEventDate, Activities.YOGA, "anotherArtist");

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Arrays.asList(aEventDto, anotherEventDto)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenEventDateIsAbsent() {
        ProgramEventDto aEventDto = new ProgramEventDto(null, Activities.YOGA.toString(), "aArtist");

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Collections.singletonList(aEventDto)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenProgramDoesNotIncludeAllFestivalDates() {
        ProgramEventDto aEventDto = new ProgramEventDto(EventDate.START_DATE.toString(), Activities.YOGA.toString(), "aArtist");

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Collections.singletonList(aEventDto)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenActivityIsInvalid() {
        String anInvalidActivity = "anInvalidActivity";
        ProgramEventDto aEventDto = new ProgramEventDto(EventDate.START_DATE.toString(), anInvalidActivity, "aArtist");

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Collections.singletonList(aEventDto)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenAmIsAbsent() {
        ProgramEventDto aEventDto = new ProgramEventDto(EventDate.START_DATE.toString(), null, "aArtist");

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Collections.singletonList(aEventDto)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenPmIsAbsent() {
        ProgramEventDto aEventDto = new ProgramEventDto(EventDate.START_DATE.toString(), Activities.YOGA.toString(), null);

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Collections.singletonList(aEventDto)));
    }

    @Test
    void build_shouldThrowInvalidProgramException_whenArtistIsDuplicate() {
        String aArtist = "aArtist";
        ProgramEventDto aEventDto = buildEventDto(new EventDate(EventDate.START_DATE), Activities.YOGA, aArtist);
        ProgramEventDto anotherEventDto = buildEventDto(new EventDate(EventDate.START_DATE), Activities.YOGA, aArtist);

        assertThrows(InvalidProgramException.class, () -> eventFactory.build(Arrays.asList(aEventDto, anotherEventDto)));
    }

    private List<ProgramEventDto> buildProgramDto(Activities activity, String artist) {
        List<ProgramEventDto> eventDtos = new ArrayList<>();
        List<EventDate> eventDates = EventDate.getFullFestivalEventDates();

        for (int i = 0; i < eventDates.size(); i++) {
            eventDtos.add(buildEventDto(eventDates.get(i), activity, artist + i));
        }

        return eventDtos;
    }

    private ProgramEventDto buildEventDto(EventDate eventDate, Activities activity, String artist) {
        return new ProgramEventDto(eventDate.toString(), activity.toString(), artist);
    }
}

