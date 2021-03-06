package ca.ulaval.glo4002.booking.shuttles.domain;

public enum ShuttleCategories {
	
	ET_SPACESHIP("ETSpaceship"),
	MILLENNIUM_FALCON("Millennium Falcon"),
	SPACE_X("Space X");
	
	private String category;

	ShuttleCategories(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return category;
	}
}
