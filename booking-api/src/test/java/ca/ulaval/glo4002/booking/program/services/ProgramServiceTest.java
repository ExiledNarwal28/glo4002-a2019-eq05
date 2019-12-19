package ca.ulaval.glo4002.booking.program.services;

import ca.ulaval.glo4002.booking.oxygen.inventory.services.OxygenInventoryService;
import ca.ulaval.glo4002.booking.program.artists.domain.Artist;
import ca.ulaval.glo4002.booking.program.events.domain.Event;
import ca.ulaval.glo4002.booking.program.events.domain.EventDate;
import ca.ulaval.glo4002.booking.program.events.domain.EventFactory;
import ca.ulaval.glo4002.booking.program.events.domain.EventRepository;
import ca.ulaval.glo4002.booking.program.rest.ProgramEventRequest;
import ca.ulaval.glo4002.booking.program.rest.ProgramRequest;
import ca.ulaval.glo4002.booking.shuttles.trips.services.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProgramServiceTest {

    private ProgramService service;
    private EventRepository eventRepository;
    private EventFactory eventFactory;
    private TripService tripService;
    private OxygenInventoryService oxygenInventoryService;

    @BeforeEach
    void setUpService() {
        eventRepository = mock(EventRepository.class);
        eventFactory = mock(EventFactory.class);
        tripService = mock(TripService.class);
        oxygenInventoryService = mock(OxygenInventoryService.class);

        service = new ProgramService(eventRepository, eventFactory, tripService, oxygenInventoryService);
    }

    @Test
    void add_shouldSaveEventsToRepository() {
        Event expectedEvent = mock(Event.class);
        List<Event> expectedEvents = Collections.singletonList(expectedEvent);
        ProgramRequest programRequest = mock(ProgramRequest.class);
        ProgramEventRequest programEventRequest = mock(ProgramEventRequest.class);
        List<ProgramEventRequest> programEventRequests = Collections.singletonList(programEventRequest);
        when(programRequest.getProgram()).thenReturn(programEventRequests);
        when(eventFactory.create(programEventRequests)).thenReturn(expectedEvents);

        service.add(programRequest);

        verify(eventRepository).addAll(eq(expectedEvents));
    }

    @Test
    void add_shouldOrderTripsForArtist_whenThereIsASingleArtist() {
        ProgramRequest aProgramRequest = mock(ProgramRequest.class);
        Event aEvent = mock(Event.class);
        Artist expectedArtist = mock(Artist.class);
        EventDate expectedEventDate = mock(EventDate.class);
        when(aEvent.getArtist()).thenReturn(expectedArtist);
        when(aEvent.getEventDate()).thenReturn(expectedEventDate);
        when(eventFactory.create(any())).thenReturn(Collections.singletonList(aEvent));

        service.add(aProgramRequest);

        verify(tripService).orderForArtist(eq(expectedArtist), eq(expectedEventDate));
    }

    @Test
    void add_shouldOrderTripsForArtists_whenThereAreMultipleArtists() {
        int artistQuantity = 2;
        ProgramRequest aProgramRequest = mock(ProgramRequest.class);
        Event aEvent = mock(Event.class);
        Artist expectedArtist = mock(Artist.class);
        EventDate expectedEventDate = mock(EventDate.class);
        when(aEvent.getArtist()).thenReturn(expectedArtist);
        when(aEvent.getEventDate()).thenReturn(expectedEventDate);
        when(eventFactory.create(any())).thenReturn(Collections.nCopies(artistQuantity, aEvent));

        service.add(aProgramRequest);

        verify(tripService, times(artistQuantity)).orderForArtist(eq(expectedArtist), eq(expectedEventDate));
    }

    @Test
    void add_shouldOrderOxygenTanksForArtist_whenThereIsASingleArtist() {
        ProgramRequest aProgramRequest = mock(ProgramRequest.class);
        Event aEvent = mock(Event.class);
        Artist expectedArtist = mock(Artist.class);
        EventDate expectedEventDate = mock(EventDate.class);
        when(aEvent.getArtist()).thenReturn(expectedArtist);
        when(aEvent.getEventDate()).thenReturn(expectedEventDate);
        when(eventFactory.create(any())).thenReturn(Collections.singletonList(aEvent));

        service.add(aProgramRequest);

        verify(oxygenInventoryService).orderForArtist(eq(expectedArtist), eq(expectedEventDate));
    }

    @Test
    void add_shouldOrderOxygenTanksForArtists_whenThereAreMultipleArtists() {
        int artistQuantity = 2;
        ProgramRequest aProgramRequest = mock(ProgramRequest.class);
        Event aEvent = mock(Event.class);
        Artist expectedArtist = mock(Artist.class);
        EventDate expectedEventDate = mock(EventDate.class);
        when(aEvent.getArtist()).thenReturn(expectedArtist);
        when(aEvent.getEventDate()).thenReturn(expectedEventDate);
        when(eventFactory.create(any())).thenReturn(Collections.nCopies(artistQuantity, aEvent));

        service.add(aProgramRequest);

        verify(oxygenInventoryService, times(artistQuantity)).orderForArtist(eq(expectedArtist), eq(expectedEventDate));
    }
}