package ca.ulaval.glo4002.booking.domain.shuttles;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.EventDate;
import ca.ulaval.glo4002.booking.domain.trip.Trip;

public class ShuttleManifest {
	
	private List<Trip> departures;
	private List<Trip> arrivals;
	
	public ShuttleManifest(EventDate date, List<Shuttle> shuttles) {
		for (Shuttle shuttle : shuttles) {
			arrivals.addAll(shuttle.getArrivalsByDate(date));
			departures.addAll(shuttle.getDeparturesByDate(date));
		}
		
	}

	public List<Trip> getDepartures() {
		return departures;
	}

	public List<Trip> getArrivals() {
		return arrivals;
	}
	
	

}
