package ca.ulaval.glo4002.booking.producers;

import ca.ulaval.glo4002.booking.domain.events.EventDate;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenTank;
import ca.ulaval.glo4002.booking.enums.OxygenCategories;
import ca.ulaval.glo4002.booking.exceptions.oxygen.InvalidOxygenCategoryException;
import ca.ulaval.glo4002.booking.factories.OxygenTankFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OxygenTankProducer {
	
	private OxygenInventory inventory;
	private OxygenTankFactory factory;

	// TODO : This constructor should not exist
	public OxygenTankProducer() {
		this.inventory = new OxygenInventory();
		this.factory = new OxygenTankFactory();
	}

	// TODO : Inventory should be gotten using a repo
	// TODO : Factory should be injected
	public OxygenTankProducer(OxygenInventory inventory, OxygenTankFactory factory) {
		this.inventory = inventory;
		this.factory = factory;
	}
	
	public List<OxygenTank> produceOxygenForOrder(OxygenCategories category, LocalDate requestDate, Integer numberOfDays) {
		List<OxygenTank> newTanks = new ArrayList<>();
		Integer quantityToCover = getQuantityToCoverForOrderCategory(category, numberOfDays);
		OxygenCategories possibleCategory;

		if (quantityToCover > inventory.getNotInUseQuantityByCategory(category)) {
			possibleCategory = getCategoryForRequestDate(category, requestDate);
			quantityToCover = inventory.requestTankByCategory(category, possibleCategory, quantityToCover);
		} else {
			possibleCategory = category;
			quantityToCover = inventory.requestTankByCategory(category,category,quantityToCover);
		}

		if (quantityToCover > 0) {
			newTanks.addAll(factory.buildOxygenTank(possibleCategory, requestDate, quantityToCover));

			inventory.addTanksToInventory(category, newTanks);
		}
		
		return newTanks;
	}
	
	private Integer getQuantityToCoverForOrderCategory(OxygenCategories category, Integer numberOfDays) {
		if (category.equals(OxygenCategories.E)) {
			return (numberOfDays * 5);
		} else {
			return (numberOfDays * 3);
		}
	}

	private OxygenCategories getCategoryForRequestDate(OxygenCategories category, LocalDate requestDate) {
		LocalDate readyBeforeDate = EventDate.START_DATE.plusDays(1);

		switch (category) {
            case A:
                if (requestDate.plusDays(20).isBefore(readyBeforeDate)) {
                    return OxygenCategories.A;
                } else if (requestDate.plusDays(10).isBefore(readyBeforeDate)) {
                    return OxygenCategories.B;
                } else {
                    return OxygenCategories.E;
                }
            case B:
                if (requestDate.plusDays(10).isBefore(readyBeforeDate)) {
                    return OxygenCategories.B;
                } else {
                    return OxygenCategories.E;
                }
            case E:
                return OxygenCategories.E;
            default:
                throw new InvalidOxygenCategoryException(category);
        }
	}
}