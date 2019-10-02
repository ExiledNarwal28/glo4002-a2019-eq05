package ca.ulaval.glo4002.booking.domainobjects.passes.categories;

import ca.ulaval.glo4002.booking.constants.PassConstants;
import ca.ulaval.glo4002.booking.domainobjects.passes.options.PassOption;
import ca.ulaval.glo4002.booking.domainobjects.qualities.SupernovaQuality;

import java.util.Map;

public class SupernovaPassCategory extends PassCategory {

	public SupernovaPassCategory(Map<PassOption, Double> pricePerOption) {
	    this.id = PassConstants.Categories.SUPERNOVA_ID;
	    this.name = PassConstants.Categories.SUPERNOVA_NAME;
		this.quality = new SupernovaQuality();
		this.pricePerOption = pricePerOption;
    }
}