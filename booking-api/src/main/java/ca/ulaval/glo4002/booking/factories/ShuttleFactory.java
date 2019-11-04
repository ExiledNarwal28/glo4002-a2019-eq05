package ca.ulaval.glo4002.booking.factories;

import java.math.BigDecimal;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.NumberGenerator;
import ca.ulaval.glo4002.booking.domain.money.Money;
import ca.ulaval.glo4002.booking.domain.shuttles.Shuttle;
import ca.ulaval.glo4002.booking.enums.PassCategories;
import ca.ulaval.glo4002.booking.enums.ShuttleCategories;

public class ShuttleFactory {
	
	public static final Integer ET_SPACESHIP_MAX_CAPACITY = 1;
	public static final Money ET_SPACESHIP_PRICE = new Money(new BigDecimal(100000));

	public static final Integer MILLENNIUM_FALCON_MAX_CAPACITY = 20;
	public static final Money MILLENNIUM_FALCON_PRICE = new Money(new BigDecimal(65000));
	
	public static final Integer SPACE_X_MAX_CAPACITY = 30;
	public static final Money SPACE_X_PRICE = new Money(new BigDecimal(30000));

	// TODO : TRANS : Pretty sure Shuttles do not need a number
	private final NumberGenerator numberGenerator;
	
	@Inject
	public ShuttleFactory(NumberGenerator numberGenerator) {
		this.numberGenerator = numberGenerator;
	}
	
	public Shuttle build(PassCategories category) {
		switch(category) {
            case SUPERNOVA:
                return new Shuttle(numberGenerator.generate(),
                		ShuttleCategories.ET_SPACESHIP,
                		ET_SPACESHIP_MAX_CAPACITY,
                		ET_SPACESHIP_PRICE);
            case SUPERGIANT:
                return new Shuttle(numberGenerator.generate(),
                		ShuttleCategories.MILLENNIUM_FALCON,
                		MILLENNIUM_FALCON_MAX_CAPACITY,
                		MILLENNIUM_FALCON_PRICE);
			default:
            case NEBULA:
                return new Shuttle(numberGenerator.generate(),
                		ShuttleCategories.SPACE_X,
                		SPACE_X_MAX_CAPACITY,
                		SPACE_X_PRICE);
		}
	}
}
