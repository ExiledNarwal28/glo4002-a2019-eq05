package ca.ulaval.glo4002.booking.repositories;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenTankInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TODO  OXY : not sure if these kind of test are part of unit testing, for example if Repo where a BD we should'nt test this in unit testing

class InMemoryOxygenTankInventoryRepositoryTest {

	private OxygenTankInventoryRepository inMemoryRepository;
	private OxygenTankInventory inventory;

	@BeforeEach
	void initRepositoryTest() {
		this.inMemoryRepository = new InMemoryOxygenTankInventoryRepository();
		inventory = new OxygenTankInventory();
		inMemoryRepository.setInventory(inventory);
	}

	@Test
	void getInventory_shouldReturnInventory() {
		OxygenTankInventory getInventory =  inMemoryRepository.getInventory();

		assertEquals(inventory, getInventory);
	}

	@Test
	void getInventory_shouldReturnNewInventory_whenNoneWasCreated() {
		OxygenTankInventory inventory =  inMemoryRepository.getInventory();

		assertNotNull(inventory);
		assertTrue(inventory instanceof OxygenTankInventory);
	}

	@Test
	void setInventory_shouldSetInventory() {
		OxygenTankInventory newInventory = new OxygenTankInventory();

		inMemoryRepository.setInventory(newInventory);
		OxygenTankInventory getInventory = inMemoryRepository.getInventory();

		assertEquals(newInventory, getInventory);
		assertNotEquals(inventory, getInventory);
	}
}
