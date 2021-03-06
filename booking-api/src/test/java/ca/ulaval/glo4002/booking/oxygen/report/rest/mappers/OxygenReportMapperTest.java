package ca.ulaval.glo4002.booking.oxygen.report.rest.mappers;

import ca.ulaval.glo4002.booking.oxygen.history.domain.OxygenHistory;
import ca.ulaval.glo4002.booking.oxygen.history.rest.OxygenHistoryItemResponse;
import ca.ulaval.glo4002.booking.oxygen.history.rest.mappers.OxygenHistoryMapper;
import ca.ulaval.glo4002.booking.oxygen.inventory.domain.OxygenInventory;
import ca.ulaval.glo4002.booking.oxygen.inventory.rest.OxygenInventoryItemResponse;
import ca.ulaval.glo4002.booking.oxygen.inventory.rest.mappers.OxygenInventoryMapper;
import ca.ulaval.glo4002.booking.oxygen.report.rest.OxygenReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OxygenReportMapperTest {

    private OxygenReportMapper oxygenReportMapper;
    private OxygenInventoryMapper inventoryMapper;
    private OxygenHistoryMapper historyMapper;

    @BeforeEach
    void setUpMapper() {
        inventoryMapper = mock(OxygenInventoryMapper.class);
        historyMapper = mock(OxygenHistoryMapper.class);

        oxygenReportMapper = new OxygenReportMapper(inventoryMapper, historyMapper);
    }

    @Test
    void toResponse_shouldMapInventory() {
        OxygenInventory inventory = mock(OxygenInventory.class);

        oxygenReportMapper.toResponse(inventory, mock(OxygenHistory.class));

        verify(inventoryMapper).toResponse(eq(inventory));
    }

    @Test
    void toResponse_shouldMapHistory() {
        OxygenHistory history = mock(OxygenHistory.class);

        oxygenReportMapper.toResponse(mock(OxygenInventory.class), history);

        verify(historyMapper).toResponse(eq(history));
    }

    @Test
    void toResponse_shouldReturnReportWithInventory() {
        List<OxygenInventoryItemResponse> expectedInventoryResponse = Collections.singletonList(mock(OxygenInventoryItemResponse.class));
        when(inventoryMapper.toResponse(any())).thenReturn(expectedInventoryResponse);

        OxygenReportResponse response = oxygenReportMapper.toResponse(mock(OxygenInventory.class), mock(OxygenHistory.class));

        assertEquals(expectedInventoryResponse.get(0), response.getInventory().get(0));
    }

    @Test
    void toResponse_shouldReturnReportWithHistory() {
        List<OxygenHistoryItemResponse> expectedHistoryResponse = Collections.singletonList(mock(OxygenHistoryItemResponse.class));
        when(historyMapper.toResponse(any())).thenReturn(expectedHistoryResponse);

        OxygenReportResponse response = oxygenReportMapper.toResponse(mock(OxygenInventory.class), mock(OxygenHistory.class));

        assertEquals(expectedHistoryResponse.get(0), response.getHistory().get(0));
    }
}
