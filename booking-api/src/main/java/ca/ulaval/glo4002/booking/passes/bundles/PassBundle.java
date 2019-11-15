package ca.ulaval.glo4002.booking.passes.bundles;

import java.math.BigDecimal;
import java.util.List;

import ca.ulaval.glo4002.booking.passes.Pass;
import ca.ulaval.glo4002.booking.passes.PassCategories;
import ca.ulaval.glo4002.booking.passes.PassCategory;
import ca.ulaval.glo4002.booking.passes.PassOptions;
import ca.ulaval.glo4002.booking.profits.Money;
import ca.ulaval.glo4002.booking.profits.Profit;

public class PassBundle {

	private List<Pass> passes;
	private PassCategory category;
	private PassOptions option;
	private Money price;

	public PassBundle(List<Pass> passes, PassCategory category, PassOptions option) {
		this.passes = passes;
		this.category = category;
		this.option = option;
		BigDecimal passesSize = new BigDecimal(passes.size());
		Money passesPrice = passes.get(0).getPrice();
		this.price = passesPrice.multiply(passesSize);
	}

	public List<Pass> getPasses() {
		return passes;
	}

	public PassCategories getCategory() {
		return category.getCategory();
	}

	public PassOptions getOption() {
		return option;
	}

	public Money getPrice() {
		return price;
	}

	public Money getPricePerOption(PassOptions option) {
		return category.getPricePerOption(option);
	}

	public void updateProfit(Profit profit) {
		profit.addRevenue(price);
	}
}