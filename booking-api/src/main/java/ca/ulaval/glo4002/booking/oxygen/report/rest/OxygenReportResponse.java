package ca.ulaval.glo4002.booking.oxygen.report.rest;

import ca.ulaval.glo4002.booking.oxygen.history.rest.OxygenHistoryItemResponse;
import ca.ulaval.glo4002.booking.oxygen.inventory.rest.OxygenInventoryItemResponse;

import java.util.List;

public class OxygenReportResponse {

	private List<OxygenInventoryItemResponse> inventory;
	private List<OxygenHistoryItemResponse> history;

	public OxygenReportResponse(List<OxygenInventoryItemResponse> inventory, List<OxygenHistoryItemResponse> history) {
		this.inventory = inventory;
		this.history = history;
	}

	public List<OxygenInventoryItemResponse> getInventory() {
		return inventory;
	}

	public List<OxygenHistoryItemResponse> getHistory() {
		return history;
	}
}
