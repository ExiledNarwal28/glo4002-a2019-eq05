package ca.ulaval.glo4002.booking.oxygen;

import ca.ulaval.glo4002.booking.profits.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OxygenTankTest {

    private OxygenTank oxygenTank;
    private OxygenDate requestDate;

    @BeforeEach
    void setUpRequestDate() {
        requestDate = new OxygenDate(LocalDate.of(2050, 7, 1));
    }

    @Test
    void getCategoryOxygenCategoryA_shouldReturnCategoryA() {
        oxygenTank = new OxygenTank(OxygenCategories.A, requestDate);

        assertEquals(OxygenCategories.A, oxygenTank.getCategory());
    }

    @Test
    void getCategoryOxygenCategoryB_shouldReturnCategoryB() {
        oxygenTank = new OxygenTank(OxygenCategories.B, requestDate);

        assertEquals(OxygenCategories.B, oxygenTank.getCategory());
    }

    @Test
    void getCategoryOxygenCategoryE_shouldReturnCategoryE() {
        oxygenTank = new OxygenTank(OxygenCategories.E, requestDate);

        assertEquals(OxygenCategories.E, oxygenTank.getCategory());
    }

    @Test
    void getReadyDateOxygenCategoryA_shouldReturn20DaysLaterDate() {
        OxygenDate expectedDate = new OxygenDate(requestDate.getValue());
        expectedDate.addDays(20);

        oxygenTank = new OxygenTank(OxygenCategories.A, requestDate);

        assertEquals(expectedDate.toString(), oxygenTank.getReadyDate().toString());
    }

    @Test
    void getReadyDateOxygenCategoryB_shouldReturn10DaysLater() {
        OxygenDate expectedDate = new OxygenDate(requestDate.getValue());
        expectedDate.addDays(10);

        oxygenTank = new OxygenTank(OxygenCategories.B, requestDate);

        assertEquals(expectedDate.toString(), oxygenTank.getReadyDate().toString());
    }

    @Test
    void getReadyDateOxygenCategoryE_shouldReturnSameDay() {
        oxygenTank = new OxygenTank(OxygenCategories.E, requestDate);

        assertEquals(requestDate.toString(), oxygenTank.getReadyDate().toString());
    }

    @Test
    void getMoneyOfOxygenCategoryA_shouldReturnCorrectPrice() {
        BigDecimal tankPrice = new BigDecimal((OxygenTank.CATEGORY_A_NUMBER_OF_RESOURCES_NEEDED * OxygenTank.CATEGORY_A_RESOURCE_PRICE) / OxygenTank.CATEGORY_A_NUMBER_OF_TANKS_CREATED);
        Money expectedPrice = new Money(tankPrice);

        oxygenTank = new OxygenTank(OxygenCategories.A, requestDate);

        assertEquals(expectedPrice, oxygenTank.getPrice());
    }

    @Test
    void getMoneyOfOxygenCategoryB_shouldReturnCorrectPrice() {
        BigDecimal tankPrice = new BigDecimal((OxygenTank.CATEGORY_B_NUMBER_OF_RESOURCES_NEEDED * OxygenTank.CATEGORY_B_RESOURCE_PRICE) / OxygenTank.CATEGORY_B_NUMBER_OF_TANKS_CREATED);
        Money expectedPrice = new Money(tankPrice);

        oxygenTank = new OxygenTank(OxygenCategories.B, requestDate);

        assertEquals(expectedPrice, oxygenTank.getPrice());
    }

    @Test
    void getMoneyOfOxygenCategoryE_shouldReturnCorrectPrice() {
        BigDecimal tankPrice = new BigDecimal((OxygenTank.CATEGORY_E_NUMBER_OF_RESOURCES_NEEDED * OxygenTank.CATEGORY_E_RESOURCE_PRICE) / OxygenTank.CATEGORY_E_NUMBER_OF_TANKS_CREATED);
        Money expectedPrice = new Money(tankPrice);

        OxygenTank oxygenE = new OxygenTank(OxygenCategories.E, requestDate);

        assertEquals(expectedPrice, oxygenE.getPrice());
    }

}