package ca.ulaval.glo4002.booking.services;

import ca.ulaval.glo4002.booking.domainobjects.report.History;

import ca.ulaval.glo4002.booking.services.OxygenTankService;

public class HistoryServiceImpl implements HistoryService {

	private OxygenTankService oxygenTankService;
	
	public HistoryServiceImpl(OxygenTankService oxygenTankService) {
		this.oxygenTankService = oxygenTankService;
	}
	
	@Override
	public History get() {
		return new History(oxygenTankService.findAll());
	}

}
