package ca.ulaval.glo4002.booking.domainObjects.trips;

import ca.ulaval.glo4002.booking.domainObjects.shuttles.Passenger;
import ca.ulaval.glo4002.booking.domainObjects.trips.types.TripType;

import java.time.LocalDate;
import java.util.List;

public abstract class Trip {

	protected Long id;
	protected LocalDate date;
	protected List<Passenger> passengers;
	protected TripType type;

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public TripType getType() {
		return type;
	}
}
