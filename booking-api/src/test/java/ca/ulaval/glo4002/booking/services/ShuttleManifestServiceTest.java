package ca.ulaval.glo4002.booking.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.booking.shuttles.manifest.ShuttleManifestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.events.EventDate;
import ca.ulaval.glo4002.booking.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.shuttles.manifest.ShuttleManifestMapper;
import ca.ulaval.glo4002.booking.shuttles.trips.TripRepository;

class ShuttleManifestServiceTest {

	private ShuttleManifestService service;
	private TripRepository tripRepository;
	private ShuttleManifestMapper mapper;

	@BeforeEach
	void setUpService() {
		tripRepository = mock(TripRepository.class);

		mapper = mock(ShuttleManifestMapper.class);

		this.service = new ShuttleManifestService(tripRepository, mapper);
	}

	@Test
	void getWithDate_shouldCallMapper() {
		String aDate = EventDate.START_DATE.toString();

		service.getTripsForDate(aDate);

		verify(mapper).toDto(any(), any());
	}

	@Test
	void getWithDate_shouldThrowInvalidFormatException_whenDateIsInvalid() {
		String anInvalidDate = "anInvalidDate";

		assertThrows(InvalidFormatException.class, () -> service.getTripsForDate(anInvalidDate));
	}

	@Test
	void getWithDate_shouldCallRepositoryForArrivals() {
		EventDate aDate = new EventDate(EventDate.START_DATE);

		service.getTripsForDate(aDate.toString());

		verify(tripRepository).getArrivalsForDate(aDate);
	}

	@Test
	void getWithDate_shouldCallRepositoryForDepartures() {
		EventDate aDate = new EventDate(EventDate.START_DATE);

		service.getTripsForDate(aDate.toString());

		verify(tripRepository).getDeparturesForDate(aDate);
	}

	@Test
	void getWithoutDate_shouldCallMapper() {
		service.getTrips();

		verify(mapper).toDto(any(), any());
	}

	@Test
	void getWithoutDate_shouldCallRepositoryForArrivals() {
		service.getTrips();

		verify(tripRepository).getArrivals();
	}

	@Test
	void getWithoutDate_shouldCallRepositoryForDepartures() {
		service.getTrips();

		verify(tripRepository).getDepartures();
	}
}
