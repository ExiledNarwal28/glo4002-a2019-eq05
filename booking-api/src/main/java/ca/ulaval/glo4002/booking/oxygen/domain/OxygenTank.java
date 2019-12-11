package ca.ulaval.glo4002.booking.oxygen.domain;

import ca.ulaval.glo4002.booking.profits.domain.Money;
import ca.ulaval.glo4002.booking.profits.domain.ProfitReport;

import java.time.LocalDate;

public class OxygenTank {

    // TODO : Those numbers should not be here...
    public static final int CATEGORY_A_NUMBER_OF_TANKS_CREATED = 5;
    public static final int CATEGORY_A_NUMBER_OF_RESOURCES_NEEDED = 15;
    public static final int CATEGORY_A_RESOURCE_PRICE = 650;
    public static final int CATEGORY_B_NUMBER_OF_TANKS_CREATED = 3;
    public static final int CATEGORY_B_NUMBER_OF_RESOURCES_NEEDED = 8;
    public static final int CATEGORY_B_RESOURCE_PRICE = 600;
    public static final int CATEGORY_E_NUMBER_OF_TANKS_CREATED = 1;
    public static final int CATEGORY_E_NUMBER_OF_RESOURCES_NEEDED = 1;
    public static final int CATEGORY_E_RESOURCE_PRICE = 5000;

    private OxygenProduction category;
    private LocalDate requestDate;
    private LocalDate readyDate;
    private Money price;

    public OxygenTank(OxygenProduction category, LocalDate requestDate) {
        this.category = category;
        this.requestDate = requestDate;

        price = category.calculatePriceForCategory();
        readyDate = category.calculateReadyDateForCategory(requestDate);
    }

    public OxygenProduction getCategory() {
        return category;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalDate getReadyDate() {
        return readyDate;
    }

    public Money getPrice() {
        return price;
    }

    public void updateProfit(ProfitReport profitReport) {
        profitReport.addExpense(price);
    }
}