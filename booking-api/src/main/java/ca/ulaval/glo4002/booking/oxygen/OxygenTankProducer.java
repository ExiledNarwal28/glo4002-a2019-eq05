package ca.ulaval.glo4002.booking.oxygen;

import ca.ulaval.glo4002.booking.oxygen.history.OxygenHistory;
import ca.ulaval.glo4002.booking.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.oxygen.inventory.OxygenInventory;
import ca.ulaval.glo4002.booking.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.passes.PassCategories;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OxygenTankProducer {

	// TODO : Test calls to history

    private OxygenInventoryRepository inventoryRepository;
    private OxygenHistoryRepository historyRepository;
	private OxygenFactory factory;

	@Inject
	public OxygenTankProducer(OxygenInventoryRepository inventoryRepository, OxygenHistoryRepository historyRepository, OxygenFactory factory) {
	    this.inventoryRepository = inventoryRepository;
	    this.historyRepository = historyRepository;
		this.factory = factory;
	}

	public List<OxygenTank> produceForDay(OxygenCategories category, LocalDate requestDate) {
		OxygenInventory inventory = inventoryRepository.getInventory();
		OxygenHistory history = historyRepository.getHistory();
		PassCategories passCategories = convertOxygenCategoryToPassCategories(category);
		OxygenCategory requestCategory = factory.buildCategory(passCategories);
		OxygenCategory actucalOxygenCategory = factory.buildCategoryForRequestDate(requestDate, category);

		List<OxygenTank> newTanks = new ArrayList<>();
		Integer quantityToCover = requestCategory.getTanksNeededPerDay();

		quantityToCover = inventory.requestTankByCategory(category, actucalOxygenCategory.getCategory(), quantityToCover);

		if (quantityToCover > 0) {
		    List<OxygenTank> producedTanks = factory.buildOxygenTank(actucalOxygenCategory, requestDate, quantityToCover);
			newTanks.addAll(producedTanks);

			history.addMadeTanks(actucalOxygenCategory.calculateReadyDateForCategory(requestDate).getValue(), producedTanks.size());

			actucalOxygenCategory.addProductionInformationsToHistory(requestDate, history);
			inventory.addTanksToInventory(category, newTanks);
		}

		history.addTanksBought(requestDate, quantityToCover);

		inventoryRepository.setInventory(inventory);
		historyRepository.setHistory(history);

		return newTanks;
	}

	// TODO Move somewhere else
	private PassCategories convertOxygenCategoryToPassCategories(OxygenCategories oxygenCategories) {
		PassCategories passCategories = PassCategories.SUPERNOVA;

		switch (oxygenCategories) {
			case E:
				passCategories = PassCategories.SUPERNOVA;
				break;
			case B:
				passCategories = PassCategories.SUPERGIANT;
				break;
			case A:
				passCategories = PassCategories.NEBULA;
				break;
			default:
				break;
		}

		return passCategories;
	}

}