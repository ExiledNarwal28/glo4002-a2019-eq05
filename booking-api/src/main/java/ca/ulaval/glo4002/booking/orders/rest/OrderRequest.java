package ca.ulaval.glo4002.booking.orders.rest;

import ca.ulaval.glo4002.booking.passes.rest.PassRefactoredRequest;

public class OrderRequest {

	private String orderDate;
	private String vendorCode;
	private PassRefactoredRequest pass;

	public OrderRequest() {
		// Empty constructor for parsing
	}

	public OrderRequest(String orderDate, String vendorCode, PassRefactoredRequest pass) {
		this.orderDate = orderDate;
		this.vendorCode = vendorCode;
		this.pass = pass;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public PassRefactoredRequest getPass() {
		return pass;
	}
}
