package ca.ulaval.glo4002.booking.oxygen.inventory.infrastructure;

import ca.ulaval.glo4002.booking.oxygen.domain.OxygenTank;
import ca.ulaval.glo4002.booking.oxygen.inventory.domain.OxygenInventory;

import java.util.List;

public class InMemoryOxygenInventoryRepository implements OxygenInventoryRepository {

	private OxygenInventory inventory;

	public InMemoryOxygenInventoryRepository() {
		this.inventory = new OxygenInventory();
	}

	@Override
	public OxygenInventory getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(OxygenInventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public List<OxygenTank> findall() {
		return inventory.getAllTanks();
	}
}