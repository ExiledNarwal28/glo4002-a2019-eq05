package ca.ulaval.glo4002.booking.domain.shuttles;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.Number;
import ca.ulaval.glo4002.booking.domain.money.Money;
import ca.ulaval.glo4002.booking.domain.trip.Trip;
import ca.ulaval.glo4002.booking.enums.ShuttleCategories;

public class Shuttle {

	private Number shuttleNumber;
	private ShuttleCategories category;
	private Integer maxCapacity;
	private Money price;
	private List<Trip> departures;
	private List<Trip> arrivals;
	
	
	public Shuttle(Number shuttleNumber, ShuttleCategories category, Integer maxCapacity, Money price) {
		this.shuttleNumber = shuttleNumber;
		this.category = category;
		this.maxCapacity = maxCapacity;
		this.price = price;
		this.departures = new ArrayList<>();
		this.arrivals = new ArrayList<>();
	}
	
	public Number getShuttleNumber() {
		return shuttleNumber;
	}
	
	public ShuttleCategories getCategory() {
		return category;
	}
	
	public Integer getMaxCapacity() {
		return maxCapacity;
	}

	public Money getPrice() {
		return price;
	}
}
