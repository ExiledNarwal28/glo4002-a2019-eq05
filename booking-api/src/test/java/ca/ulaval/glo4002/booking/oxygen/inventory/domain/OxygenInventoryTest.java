package ca.ulaval.glo4002.booking.oxygen.inventory.domain;

import ca.ulaval.glo4002.booking.oxygen.domain.OxygenCategories;
import ca.ulaval.glo4002.booking.oxygen.domain.OxygenTank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class OxygenInventoryTest {

	private OxygenInventory oxygenInventory;

	private OxygenTank mockedTankCategoryA;
	private OxygenTank mockedTankCategoryB;
	private OxygenTank mockedTankCategoryE;

	private final Integer CATEGORY_A_QUANTITY = 25;
	private final Integer CATEGORY_B_QUANTITY = 12;
	private final Integer CATEGORY_E_QUANTITY = 5;

	@BeforeEach
	void setupOxygenTankInventory() {
		oxygenInventory = new OxygenInventory();

		mockedTankCategoryA = mock(OxygenTank.class);
		mockedTankCategoryB = mock(OxygenTank.class);
		mockedTankCategoryE = mock(OxygenTank.class);

		oxygenInventory.addTanksToInventory(OxygenCategories.A, Collections.nCopies(CATEGORY_A_QUANTITY, mockedTankCategoryA));
		oxygenInventory.addTanksToInventory(OxygenCategories.B, Collections.nCopies(CATEGORY_B_QUANTITY, mockedTankCategoryB));
		oxygenInventory.addTanksToInventory(OxygenCategories.E, Collections.nCopies(CATEGORY_E_QUANTITY, mockedTankCategoryE));
	}

	@Test
	void constructing_shouldSetNoNotInUseTank() {
		oxygenInventory = new OxygenInventory();

		assertEquals(0, (int) oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.A));
		assertEquals(0, (int) oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.B));
		assertEquals(0, (int) oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.E));
	}

	@Test
	void constructing_shouldSetNoInUseTank() {
		oxygenInventory = new OxygenInventory();

		assertEquals(0, (int) oxygenInventory.getInUseQuantityByCategory(OxygenCategories.A));
		assertEquals(0, (int) oxygenInventory.getInUseQuantityByCategory(OxygenCategories.B));
		assertEquals(0, (int) oxygenInventory.getInUseQuantityByCategory(OxygenCategories.E));
	}

	@Test
	void addTankToInventory_shouldUpdateNumberOfTankInNotInUse() {
		Integer addedQuantity = 5;

		oxygenInventory.addTanksToInventory(OxygenCategories.A, Collections.nCopies(addedQuantity, mockedTankCategoryA));
		Integer currentQuantity = oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.A);

		assertEquals(CATEGORY_A_QUANTITY + addedQuantity, (int) currentQuantity);
	}

	@Test
	void assignTanksByCategory_shouldUpdateQuantityInUse() {
		Integer requestQuantity = 10;

		oxygenInventory.assignTanksByCategory(OxygenCategories.B, OxygenCategories.B, requestQuantity);
		Integer currentQuantity = oxygenInventory.getInUseQuantityByCategory(OxygenCategories.B);

		assertSame(requestQuantity, currentQuantity);
	}

	@Test
	void assignTanksByCategory_whenRequestingCategoryATankWithMaxCategoryB_shouldUseCategoryBAndA(){
		Integer amountToGetFromB = 5;
		Integer requestQuantity = CATEGORY_A_QUANTITY + amountToGetFromB;

		oxygenInventory.assignTanksByCategory(OxygenCategories.A, OxygenCategories.B, requestQuantity);
		Integer currentQuantityA = oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.A);
		Integer currentQuantityB = oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.B);

		assertEquals(0, currentQuantityA);
		assertEquals(CATEGORY_B_QUANTITY - amountToGetFromB, currentQuantityB);
	}

	@Test
	void assignTanksByCategory_shouldUpdateQuantityNotInUse() {
		Integer requestQuantity = 10;

		oxygenInventory.assignTanksByCategory(OxygenCategories.B, OxygenCategories.B, requestQuantity);
		Integer currentQuantity = oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.B);

		assertEquals(CATEGORY_B_QUANTITY - requestQuantity, (int) currentQuantity);
	}

	@Test
	void assignTanksByCategory_shouldReturnNone_whenThereIsEnoughNotInUseTanks() {
		Integer requestedQuantity = 5;

		Integer quantityNeeded = oxygenInventory.assignTanksByCategory(OxygenCategories.E, OxygenCategories.E, requestedQuantity);

		assertEquals(0, (int) quantityNeeded);
	}

	@Test
	void assignTanksByCategory_shouldReturnTheNumberStillNeededToProduce_whenNotEnoughNotInUseTank() {
		Integer requestedQuantity = 100;

		Integer quantity = oxygenInventory.assignTanksByCategory(OxygenCategories.A, OxygenCategories.B, requestedQuantity);

		assertEquals(Math.abs((CATEGORY_A_QUANTITY + CATEGORY_B_QUANTITY) - requestedQuantity), (int) quantity);
	}

	@Test
	void getNotInUseQuantityByCategory_shouldReturnRightNumber() {
		Integer numberA = oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.A);
		Integer numberB = oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.B);
		Integer numberE = oxygenInventory.getNotInUseQuantityByCategory(OxygenCategories.E);

		assertEquals(CATEGORY_A_QUANTITY, numberA);
		assertEquals(CATEGORY_B_QUANTITY, numberB);
		assertEquals(CATEGORY_E_QUANTITY, numberE);
	}

	@Test
	void getInUseQuantityByCategory_shouldReturnRightNumber() {
		Integer numberA = oxygenInventory.getInUseQuantityByCategory(OxygenCategories.A);
		Integer numberB = oxygenInventory.getInUseQuantityByCategory(OxygenCategories.B);
		Integer numberE = oxygenInventory.getInUseQuantityByCategory(OxygenCategories.E);

		assertEquals(0, numberA);
		assertEquals(0, numberB);
		assertEquals(0, numberE);
	}

	@Test
	void getInUseTanksByCategory_shouldReturnRightNumber() {
		List<OxygenTank> listA = oxygenInventory.getInUseTanksByCategory(OxygenCategories.A);
		List<OxygenTank> listB = oxygenInventory.getInUseTanksByCategory(OxygenCategories.B);
		List<OxygenTank> listE = oxygenInventory.getInUseTanksByCategory(OxygenCategories.E);

		assertEquals(0, listA.size());
		assertEquals(0, listB.size());
		assertEquals(0, listE.size());
	}

	@Test
	void getNotInUseTankByCategory_shouldReturnRightNumber() {
		List<OxygenTank> listA = oxygenInventory.getNotInUseTankByCategory(OxygenCategories.A);
		List<OxygenTank> listB = oxygenInventory.getNotInUseTankByCategory(OxygenCategories.B);
		List<OxygenTank> listE = oxygenInventory.getNotInUseTankByCategory(OxygenCategories.E);

		assertEquals(CATEGORY_A_QUANTITY, listA.size());
		assertEquals(CATEGORY_B_QUANTITY, listB.size());
		assertEquals(CATEGORY_E_QUANTITY, listE.size());
	}

	@Test
	void getAllQuantityByCategory_shouldReturnRightNumber() {
		Integer numberA = oxygenInventory.getAllQuantityByCategory(OxygenCategories.A);
		Integer numberB = oxygenInventory.getAllQuantityByCategory(OxygenCategories.B);
		Integer numberE = oxygenInventory.getAllQuantityByCategory(OxygenCategories.E);

		assertEquals(CATEGORY_A_QUANTITY, numberA);
		assertEquals(CATEGORY_B_QUANTITY, numberB);
		assertEquals(CATEGORY_E_QUANTITY, numberE);
	}

	@ParameterizedTest
	@EnumSource(OxygenCategories.class)
	void getAllTanks_shouldReturnNoTankForCategory_whenThereIsNoTank(OxygenCategories category) {
		oxygenInventory = new OxygenInventory();

		List<OxygenTank> allTanks = oxygenInventory.getAllTanks();

		assertTrue(allTanks.isEmpty());
	}

	@Test
	void getAllTanksForCategory_shouldReturnAllTanksForCategoryA_whenThereAreSomeNotInUseCategoryATanks() {
		Integer expectedQuantity = CATEGORY_A_QUANTITY;

		List<OxygenTank> categoryATanksTList = oxygenInventory.getAllTanksForCategory(OxygenCategories.A);

		assertEquals(expectedQuantity, categoryATanksTList.size());
	}

	@Test
	void getAllTanksForCategory_shouldReturnAllTanksForCategoryB_whenThereAreSomeNotInUseCategoryBTanks() {
		Integer expectedQuantity = CATEGORY_B_QUANTITY;

		List<OxygenTank> categoryBTanksTList = oxygenInventory.getAllTanksForCategory(OxygenCategories.B);

		assertEquals(expectedQuantity, categoryBTanksTList.size());
	}

	@Test
	void getAllTanksForCategory_shouldReturnAllTanksForCategoryE_whenThereAreSomeNotInUseCategoryETanks() {
		Integer expectedQuantity = CATEGORY_E_QUANTITY;

		List<OxygenTank> categoryETanksTList = oxygenInventory.getAllTanksForCategory(OxygenCategories.E);

		assertEquals(expectedQuantity, categoryETanksTList.size());
	}

	@Test
	void getAllTanksForCategoryWithCategoryE_shouldReturnAllTanksForCategory_whenThereAreSomeNotInUseAndSomeInUseTanks() {
	    OxygenCategories category = OxygenCategories.E;
		Integer expectedQuantity = 5;

		List<OxygenTank> allTanks = oxygenInventory.getAllTanksForCategory(category);

		assertEquals(expectedQuantity, allTanks.size());
	}

	@Test
	void getAllTanksForCategoryWithCategoryB_shouldReturnAllTanksForCategory_whenThereAreSomeNotInUseAndSomeInUseTanks() {
		OxygenCategories category = OxygenCategories.B;
		Integer expectedQuantity = 12;

		List<OxygenTank> allTanks = oxygenInventory.getAllTanksForCategory(category);

		assertEquals(expectedQuantity, allTanks.size());
	}

	@Test
	void getAllTanksForCategoryWithCategoryA_shouldReturnAllTanksForCategory_whenThereAreSomeNotInUseAndSomeInUseTanks() {
		OxygenCategories category = OxygenCategories.A;
		Integer expectedQuantity = 25;

		List<OxygenTank> allTanks = oxygenInventory.getAllTanksForCategory(category);

		assertEquals(expectedQuantity, allTanks.size());
	}
}