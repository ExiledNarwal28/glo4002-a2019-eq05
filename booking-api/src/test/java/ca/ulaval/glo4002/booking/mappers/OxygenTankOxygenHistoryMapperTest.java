package ca.ulaval.glo4002.booking.mappers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryItem;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenTank;
import ca.ulaval.glo4002.booking.dto.oxygen.OxygenHistoryItemDto;
import ca.ulaval.glo4002.booking.enums.OxygenCategories;

class OxygenTankOxygenHistoryMapperTest {

	private static final OxygenCategories CATEGORY_A = OxygenCategories.A;
	private static final OxygenCategories CATEGORY_B = OxygenCategories.B;
	private static final OxygenCategories CATEGORY_E = OxygenCategories.E;

	private OxygenHistoryMapper mapper;
	private OxygenHistory mockedOxygenHistory;
	private LocalDate date = LocalDate.of(2050, 7, 1);
	private List<OxygenTank> oxygenTanksList;

	@BeforeEach
	void setUpMapper() {
		mapper = new OxygenHistoryMapper();
	}

	@BeforeEach
	void setUpOxygenHistory() {
		OxygenHistoryItem oxygenHistoryItem = setUpOxygenHistoryItem();
		List<OxygenHistoryItem> historyItems = new ArrayList<>();
		historyItems.add(oxygenHistoryItem);
		mockedOxygenHistory = mock(OxygenHistory.class);
		when(mockedOxygenHistory.returnSortedListByDate()).thenReturn(historyItems);
	}

	@BeforeEach
	void setUpOxygenTanksList() {
		oxygenTanksList = new ArrayList<>();
		OxygenTank tankA = mock(OxygenTank.class);
		when(tankA.getCategory()).thenReturn(CATEGORY_A);
		oxygenTanksList.add(tankA);
		OxygenTank tankB = mock(OxygenTank.class);
		when(tankB.getCategory()).thenReturn(CATEGORY_B);
		oxygenTanksList.add(tankB);
		OxygenTank tankE = mock(OxygenTank.class);
		when(tankE.getCategory()).thenReturn(CATEGORY_E);
		oxygenTanksList.add(tankE);
	}

	private OxygenHistoryItem setUpOxygenHistoryItem() {
		OxygenHistoryItem mockedOxygenHistoryItem = mock(OxygenHistoryItem.class);
		when(mockedOxygenHistoryItem.getDate()).thenReturn(date);
		when(mockedOxygenHistoryItem.getQtyCandlesUsed()).thenReturn(15);
		when(mockedOxygenHistoryItem.getQtyOxygenTankBought()).thenReturn(1);
		when(mockedOxygenHistoryItem.getQtyOxygenTankMade()).thenReturn(9);
		when(mockedOxygenHistoryItem.getQtyWaterUsed()).thenReturn(8);
		return mockedOxygenHistoryItem;
	}

	@Test
	void toDto_shouldBuildWithCorrectOxygenHistoryItemDtoList() {
		List<OxygenHistoryItemDto> dtos = mapper.toDto(mockedOxygenHistory);

		assertEquals(dtos.size(), mockedOxygenHistory.returnSortedListByDate().size());
	}

	// TODO if we delete OxygenHistoryItemMapper, we should test for buildItemDto
}
