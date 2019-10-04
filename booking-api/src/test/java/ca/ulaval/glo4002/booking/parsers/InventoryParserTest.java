package ca.ulaval.glo4002.booking.parsers;

import ca.ulaval.glo4002.booking.constants.OxygenConstants;
import ca.ulaval.glo4002.booking.domainobjects.report.Inventory;
import ca.ulaval.glo4002.booking.dto.InventoryItemDto;
import ca.ulaval.glo4002.booking.entities.InventoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryParserTest {

	private InventoryParser subject;
	private Inventory inventory;
	private Inventory inventoryOneEmptyCategory;
	private Inventory anEmptyInventory;
	private static final Long A_VALID_NUMBER_OF_TANK_CATEGORY_A_STORED = 1L;
	private static final Long A_VALID_NUMBER_OF_TANK_CATEGORY_B_STORED = 2L;
	private static final Long A_VALID_NUMBER_OF_TANK_CATEGORY_E_STORED = 3L;

	@BeforeEach
	void setup() {
		Map<Long, Long> inUseTanks = new HashMap<>();
		Map<Long, Long> notInUseTanks = new HashMap<>();
		Map<Long, Long> notInUseOneEmptyCategory = new HashMap<>();
		Map<Long, Long> inUseOneEmptyCategory = new HashMap<>();

		inUseTanks.put(OxygenConstants.Categories.A_ID, A_VALID_NUMBER_OF_TANK_CATEGORY_A_STORED);
		inUseTanks.put(OxygenConstants.Categories.B_ID, A_VALID_NUMBER_OF_TANK_CATEGORY_B_STORED);
		inUseTanks.put(OxygenConstants.Categories.E_ID, A_VALID_NUMBER_OF_TANK_CATEGORY_E_STORED);

		notInUseTanks.put(OxygenConstants.Categories.A_ID, A_VALID_NUMBER_OF_TANK_CATEGORY_A_STORED);
		notInUseTanks.put(OxygenConstants.Categories.B_ID, A_VALID_NUMBER_OF_TANK_CATEGORY_B_STORED);
		notInUseTanks.put(OxygenConstants.Categories.E_ID, A_VALID_NUMBER_OF_TANK_CATEGORY_E_STORED);

		notInUseOneEmptyCategory.put(OxygenConstants.Categories.A_ID, 0L);
		notInUseOneEmptyCategory.put(OxygenConstants.Categories.B_ID, 0L);
		notInUseOneEmptyCategory.put(OxygenConstants.Categories.E_ID, 0L);

        inUseOneEmptyCategory.put(OxygenConstants.Categories.A_ID, A_VALID_NUMBER_OF_TANK_CATEGORY_A_STORED);
        inUseOneEmptyCategory.put(OxygenConstants.Categories.B_ID, A_VALID_NUMBER_OF_TANK_CATEGORY_B_STORED);
        inUseOneEmptyCategory.put(OxygenConstants.Categories.E_ID, 0L);

		anEmptyInventory = new Inventory();
		inventory = new Inventory(notInUseTanks, inUseTanks);
		inventoryOneEmptyCategory = new Inventory(notInUseOneEmptyCategory, inUseOneEmptyCategory);
		subject = new InventoryParser();
	}

	@Test
	void whenParsingEntity_InventoryShouldBeValid() {
		InventoryEntity entity = subject.toEntity(inventory);

		Inventory parsedInventory = subject.parseEntity(entity);

		assertEquals(entity.getInUseTanks().get(0).getQuantity(), parsedInventory.getInUseTanks().get(OxygenConstants.Categories.E_ID));
		assertEquals(entity.getInUseTanks().get(1).getQuantity(), parsedInventory.getInUseTanks().get(OxygenConstants.Categories.B_ID));
		assertEquals(entity.getInUseTanks().get(2).getQuantity(), parsedInventory.getInUseTanks().get(OxygenConstants.Categories.A_ID));

		assertEquals(entity.getNotInUseTanks().get(0).getQuantity(), parsedInventory.getNotInUseTanks().get(OxygenConstants.Categories.E_ID));
		assertEquals(entity.getInUseTanks().get(1).getQuantity(), parsedInventory.getNotInUseTanks().get(OxygenConstants.Categories.B_ID));
		assertEquals(entity.getInUseTanks().get(2).getQuantity(), parsedInventory.getNotInUseTanks().get(OxygenConstants.Categories.A_ID));
	}

	@Test
	void whenParsingToEntity_entityShouldBeValid() {
		InventoryEntity entity = subject.toEntity(inventory);

		assertEquals(A_VALID_NUMBER_OF_TANK_CATEGORY_E_STORED, entity.getInUseTanks().get(0).getQuantity());
		assertEquals(A_VALID_NUMBER_OF_TANK_CATEGORY_B_STORED, entity.getInUseTanks().get(1).getQuantity());
		assertEquals(A_VALID_NUMBER_OF_TANK_CATEGORY_A_STORED, entity.getInUseTanks().get(2).getQuantity());
	}

	@Test
	void whenParsingToDto_dtoShouldBeValid() {
		List<InventoryItemDto> dtos = subject.toDto(inventory);
		Long totalTankCategoryA = A_VALID_NUMBER_OF_TANK_CATEGORY_A_STORED * 2;
		Long totalTankCategoryB = A_VALID_NUMBER_OF_TANK_CATEGORY_B_STORED * 2;
		Long totalTankCategoryE = A_VALID_NUMBER_OF_TANK_CATEGORY_E_STORED * 2;

		assertEquals("E", dtos.get(0).gradeTankOxygen);
		assertEquals(totalTankCategoryE, dtos.get(0).quantity);
		assertEquals("B", dtos.get(1).gradeTankOxygen);
		assertEquals(totalTankCategoryB, dtos.get(1).quantity);
		assertEquals("A", dtos.get(2).gradeTankOxygen);
		assertEquals(totalTankCategoryA, dtos.get(2).quantity);
	}

	@Test
	void whenParsingToDto_andAllCategoryInInventoryIsEmpty_thenNothingShouldBeAddedToTheDto() {
		List<InventoryItemDto> dtos = subject.toDto(anEmptyInventory);

		assertEquals(0, dtos.size());
	}

	@Test
	void whenParsingToDto_andOneCategoryInTheInventoryIsEmpty_thenItShouldNotBeAddedToTheDto() {
		List<InventoryItemDto> dtos = subject.toDto(inventoryOneEmptyCategory);

		assertEquals(2, dtos.size());
	}
}
