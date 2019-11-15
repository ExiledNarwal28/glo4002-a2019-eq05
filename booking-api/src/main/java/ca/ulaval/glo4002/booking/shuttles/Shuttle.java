package ca.ulaval.glo4002.booking.shuttles;

import ca.ulaval.glo4002.booking.profits.Money;
import ca.ulaval.glo4002.booking.profits.Profit;

public class Shuttle {

	private ShuttleCategories category;
	private Integer maxCapacity;
	private Money price;

	public Shuttle(ShuttleCategories category, Integer maxCapacity, Money price) {
		this.category = category;
		this.maxCapacity = maxCapacity;
		this.price = price;
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
	
	public void updateProfit(Profit profit) {
		profit.addExpense(price);
	}
}
