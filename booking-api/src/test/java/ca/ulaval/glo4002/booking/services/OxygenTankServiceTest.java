package ca.ulaval.glo4002.booking.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.constants.ExceptionConstants;
import ca.ulaval.glo4002.booking.domainObjects.oxygen.OxygenTank;
import ca.ulaval.glo4002.booking.exceptions.oxygen.OxygenTankNotFoundException;

class OxygenTankServiceTest {

	OxygenTankServiceImpl subject;
	OxygenTankServiceContext context;

	@BeforeEach
	public void setUp() {
		this.context = new OxygenTankServiceContext();
		this.subject = new OxygenTankServiceImpl(this.context.repository);
	}

	@Test
	public void findAll_shouldReturnCorrectOxygens() {
		List<Long> tankIds = new ArrayList<>();
		subject.findAll().forEach(oxygenTank -> tankIds.add(oxygenTank.getId()));

		assertEquals(3, tankIds.size());
		assertTrue(tankIds.contains(context.oxygenTankA.getId()));
		assertTrue(tankIds.contains(context.oxygenTankB.getId()));
		assertTrue(tankIds.contains(context.oxygenTankE.getId()));
	}

	@Test
	public void findById_shouldThrowOxygenTankNotFoundException_whenOxygenTankDoesNotExist() {
		OxygenTankNotFoundException thrown = assertThrows(OxygenTankNotFoundException.class,
				() -> subject.findById(this.context.oxygenTankNonExistantId));

		assertEquals(ExceptionConstants.OXYGEN_TANK_NOT_FOUND_MESSAGE, thrown.getMessage());
	}

	@Test
	public void findById_shouldReturnCorrectOxygenTank() {
		OxygenTank oxygenTank = subject.findById(this.context.oxygenTankAId);

		assertEquals(context.oxygenTankA.getId(), oxygenTank.getId());
	}

	@Test
	public void save_shouldSaveOxygenTank() {
		context.setUpRepositoryForSave();
		subject.save(context.oxygenTankNonExistant);
		OxygenTank oxygenTank = subject.findById(this.context.oxygenTankNonExistantId);

		assertEquals(context.oxygenTankNonExistant.getId(), oxygenTank.getId());
	}
}
