package ca.ulaval.glo4002.booking.oxygen.domain;

import ca.ulaval.glo4002.booking.festival.domain.FestivalConfiguration;
import ca.ulaval.glo4002.booking.passes.domain.PassCategories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OxygenFactoryTest {

    private OxygenFactory factory;
    private FestivalConfiguration festivalConfiguration;

    @BeforeEach
    void setupFactory() {
        factory = new OxygenFactory(festivalConfiguration);
    }

    @BeforeEach
    void setUpConfiguration() {
        festivalConfiguration = mock(FestivalConfiguration.class);

        when(festivalConfiguration.getStartEventDate()).thenReturn(FestivalConfiguration.getDefaultStartEventDate());
        when(festivalConfiguration.getEndEventDate()).thenReturn(FestivalConfiguration.getDefaultEndEventDate());
    }

    @Test
    void create_shouldSetOxygenCategory() {
        OxygenCategories oxygenCategory = OxygenCategories.A;
        OxygenProduction production = mock(OxygenProduction.class);
        when(production.getNumberOfTanksByBundle()).thenReturn(1);
        when(production.getCategory()).thenReturn(oxygenCategory);
        LocalDate aValidRequestDate = festivalConfiguration.getStartEventDate().minusDays(21).getValue();
        Integer aNumberOfTanks = 5;

        List<OxygenTank> createdTanks = factory.createOxygenTank(production, aNumberOfTanks);

        assertEquals(oxygenCategory, createdTanks.get(0).getProduction().getCategory());
    }

    @Test
    void createCategory_shouldReturnCategoryE_whenCategoryIsSupernova() {
        PassCategories passCategory = PassCategories.SUPERNOVA;

        OxygenCategories category = factory.createCategory(passCategory);

        assertEquals(OxygenCategories.E, category);
    }

    @Test
    void createCategory_shouldReturnCategoryB_whenCategoryIsSupergiant() {
        PassCategories passCategory = PassCategories.SUPERGIANT;

        OxygenCategories category = factory.createCategory(passCategory);

        assertEquals(OxygenCategories.B, category);
    }

    @Test
    void createCategory_shouldReturnCategoryA_whenCategoryIsNebula() {
        PassCategories passCategory = PassCategories.NEBULA;

        OxygenCategories category = factory.createCategory(passCategory);

        assertEquals(OxygenCategories.A, category);
    }

    @Test
    void createCategory_shouldReturnCategoryE_whenCategoryIsE() {
        OxygenCategories oxygenCategory = OxygenCategories.E;

        OxygenProduction category = factory.createProduction(oxygenCategory);

        assertEquals(OxygenCategories.E, category.getCategory());
    }

    @Test
    void createCategory_shouldReturnCategoryB_whenCategoryIsB() {
        OxygenCategories oxygenCategory = OxygenCategories.B;

        OxygenProduction category = factory.createProduction(oxygenCategory);

        assertEquals(OxygenCategories.B, category.getCategory());
    }

    @Test
    void createCategory_shouldReturnCategoryA_whenCategoryIsA() {
        OxygenCategories oxygenCategory = OxygenCategories.A;

        OxygenProduction category = factory.createProduction(oxygenCategory);

        assertEquals(OxygenCategories.A, category.getCategory());
    }

    @Test
    void createCategoryForRequestDate_shouldReturnNewCategoryA_whenDateIsInRange() {
        OxygenProduction category = factory.createProductionForRequestDate(LocalDate.of(2050, 6, 27), OxygenCategories.A);

        assertEquals(OxygenCategories.A, category.getCategory());
    }

    @Test
    void createCategoryForRequestDate_shouldReturnNewCategoryB_whenDateIsNotInRangeForAButOkForB() {
        OxygenProduction category = factory.createProductionForRequestDate(LocalDate.of(2050, 7, 7), OxygenCategories.A);

        assertEquals(OxygenCategories.B, category.getCategory());
    }

    @Test
    void createCategoryForRequestDate_shouldReturnNewCategoryE_whenDateIsNotInRangeForAOrBButOkForE() {
        OxygenProduction category = factory.createProductionForRequestDate(LocalDate.of(2050, 7, 17), OxygenCategories.A);

        assertEquals(OxygenCategories.E, category.getCategory());
    }

    @Test
    void createCategoryForRequestDate_shouldReturnNewCategoryB_whenDateIsInRange() {
        OxygenProduction category = factory.createProductionForRequestDate(LocalDate.of(2050, 7, 7), OxygenCategories.B);

        assertEquals(OxygenCategories.B, category.getCategory());
    }

    @Test
    void createCategoryForRequestDate_shouldReturnNewCategoryE_whenDateIsNotInRangeFoBButOkForE() {
        OxygenProduction category = factory.createProductionForRequestDate(LocalDate.of(2050, 7, 17), OxygenCategories.B);

        assertEquals(OxygenCategories.E, category.getCategory());
    }

    @Test
    void createCategoryForRequestDate_shouldReturnNewCategoryE_whenDateIsInRange() {
        OxygenProduction category = factory.createProductionForRequestDate(LocalDate.of(2050, 7, 17), OxygenCategories.E);

        assertEquals(OxygenCategories.E, category.getCategory());
    }
}