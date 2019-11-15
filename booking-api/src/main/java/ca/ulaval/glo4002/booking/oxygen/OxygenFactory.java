package ca.ulaval.glo4002.booking.oxygen;

import ca.ulaval.glo4002.booking.oxygen.OxygenDate;
import ca.ulaval.glo4002.booking.oxygen.OxygenTank;
import ca.ulaval.glo4002.booking.oxygen.OxygenCategories;
import ca.ulaval.glo4002.booking.passes.PassCategories;
import ca.ulaval.glo4002.booking.oxygen.InvalidOxygenCategoryException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OxygenFactory {

	public List<OxygenTank> buildOxygenTank(OxygenCategories category, LocalDate requestDate, Integer quantityToCover) {
		List<OxygenTank> newTanks = new ArrayList<>();
		Integer numberOfTanksByBundle = getNumberOfTanksByCategoryForCreation(category);
		OxygenDate requestedDate = new OxygenDate(requestDate);

		while (quantityToCover > 0) {
			for (Integer i = 0; i < numberOfTanksByBundle; i++) {
				newTanks.add(new OxygenTank(category, requestedDate));
			}

			quantityToCover -= numberOfTanksByBundle;
		}

		return newTanks;
	}

	public OxygenCategories buildCategory(PassCategories category) {
		switch(category) {
			case SUPERNOVA:
				return OxygenCategories.E;
			case SUPERGIANT:
				return OxygenCategories.B;
			default:
			case NEBULA:
				return OxygenCategories.A;
		}
	}

	private Integer getNumberOfTanksByCategoryForCreation(OxygenCategories category) {
		switch (category) {
			case A:
				return 5;
			case B:
				return 3;
			case E:
				return 1;
			default:
				throw new InvalidOxygenCategoryException(category);
		}
	}
}