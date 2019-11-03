package ca.ulaval.glo4002.booking.factories;

import ca.ulaval.glo4002.booking.domain.Number;
import ca.ulaval.glo4002.booking.domain.money.Money;
import ca.ulaval.glo4002.booking.domain.passes.*;
import ca.ulaval.glo4002.booking.domain.passes.pricecalculationstrategy.NebulaPriceCalculationStrategy;
import ca.ulaval.glo4002.booking.domain.passes.pricecalculationstrategy.NoDiscountPriceCalculationStrategy;
import ca.ulaval.glo4002.booking.domain.passes.pricecalculationstrategy.PriceCalculationStrategy;
import ca.ulaval.glo4002.booking.domain.passes.pricecalculationstrategy.SupergiantPriceCalculationStrategy;
import ca.ulaval.glo4002.booking.dto.PassListDto;
import ca.ulaval.glo4002.booking.enums.PassCategories;
import ca.ulaval.glo4002.booking.enums.PassOptions;
import ca.ulaval.glo4002.booking.exceptions.InvalidFormatException;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassListFactoryTest {

    private PassListFactory subject;
    private PassFactory passFactory;

    @BeforeEach
    void setUpSubject() {
        passFactory = mock(PassFactory.class);
        Pass pass = new Pass(
                new Number(1L),
                new EventDate(EventDate.START_DATE),
                new Money(new BigDecimal(100.0))
        );
        List<Pass> passes = Collections.singletonList(pass);
        when(passFactory.buildAll(any(), any())).thenReturn(passes);

        subject = new PassListFactory(passFactory);
    }

    @Test
    void buildWithDto_shouldBuildAPassList() {
        PassListDto passListDto = new PassListDto(PassCategories.SUPERNOVA.toString(), PassOptions.SINGLE_PASS.toString(), new ArrayList<>());

        subject.build(passListDto);

        verify(passFactory).buildAll(any(), any());
    }

    @Test
    void build_shouldBuildCategory_whenCategoryIsSupernova() {
        String aPassCategory = PassCategories.SUPERNOVA.toString();
        String aPassOption = PassOptions.PACKAGE.toString();
        PassListDto passListDto = new PassListDto(aPassCategory, aPassOption, null);

        PassList passList = subject.build(passListDto);

        assertEquals(passList.getCategory().getName(), PassCategories.SUPERNOVA.toString());
    }

    @Test
    void build_shouldBuildCategory_whenCategoryIsSupergiant() {
        String aPassCategory = PassCategories.SUPERGIANT.toString();
        String aPassOption = PassOptions.PACKAGE.toString();
        PassListDto passListDto = new PassListDto(aPassCategory, aPassOption, null);

        PassList passList = subject.build(passListDto);

        assertEquals(passList.getCategory().getName(), PassCategories.SUPERGIANT.toString());
    }

    @Test
    void build_shouldBuildCategory_whenCategoryIsNebula() {
        String aPassCategory = PassCategories.NEBULA.toString();
        String aPassOption = PassOptions.PACKAGE.toString();
        PassListDto passListDto = new PassListDto(aPassCategory, aPassOption, null);

        PassList passList = subject.build(passListDto);

        assertEquals(passList.getCategory().getName(), PassCategories.NEBULA.toString());
    }

    @Test
    void build_shouldBuildOption_whenOptionIsSinglePass() {
        String aPassCategory = PassCategories.SUPERNOVA.toString();
        String aPassOption = PassOptions.SINGLE_PASS.toString();
        List<String> someEventDates = Arrays.asList(EventDate.START_DATE.toString(), EventDate.START_DATE.plusDays(1).toString());
        PassListDto passListDto = new PassListDto(aPassCategory, aPassOption, someEventDates);

        PassList passList = subject.build(passListDto);

        assertEquals(passList.getOption().getName(), PassOptions.SINGLE_PASS.toString());
    }

    @Test
    void build_shouldBuildOption_whenOptionIsPackage() {
        String aPassCategory = PassCategories.SUPERNOVA.toString();
        String aPassOption = PassOptions.PACKAGE.toString();
        PassListDto passListDto = new PassListDto(aPassCategory, aPassOption, null);

        PassList passList = subject.build(passListDto);

        assertEquals(passList.getOption().getName(), PassOptions.PACKAGE.toString());
    }

    @Test
    void build_shouldBuildNoDiscountPriceCalculationStrategy_whenPassOptionIsSinglePassAndPassCategoryIsSupernova() {
        String aPassCategory = PassCategories.SUPERNOVA.toString();
        String aPassOption = PassOptions.SINGLE_PASS.toString();
        List<String> someEventDates = Arrays.asList(EventDate.START_DATE.toString(), EventDate.START_DATE.plusDays(1).toString());
        PassListDto passListDto = new PassListDto(aPassCategory, aPassOption, someEventDates);
        PriceCalculationStrategy priceCalculationStrategy = new NoDiscountPriceCalculationStrategy();

        PassList passList = subject.build(passListDto);
        Money passPrice = passList.getPasses().get(0).getPrice();
        Money expectedPrice = priceCalculationStrategy.calculatePassPrice(someEventDates.size(), passPrice);

        assertEquals(expectedPrice, passList.getPrice());
    }

    @Test
    void build_shouldBuildSupergiantPriceCalculationStrategy_whenPassOptionIsSinglePassAndPassCategoryIsSupergiant() {
        String aPassCategory = PassCategories.SUPERGIANT.toString();
        String aPassOption = PassOptions.SINGLE_PASS.toString();
        List<String> someEventDates = Arrays.asList(EventDate.START_DATE.toString(), EventDate.START_DATE.plusDays(1).toString());
        PassListDto passListDto = new PassListDto(aPassCategory, aPassOption, someEventDates);
        PriceCalculationStrategy priceCalculationStrategy = new SupergiantPriceCalculationStrategy();

        PassList passList = subject.build(passListDto);
        Money passPrice = passList.getPasses().get(0).getPrice();
        Money expectedPrice = priceCalculationStrategy.calculatePassPrice(someEventDates.size(), passPrice);

        assertEquals(expectedPrice, passList.getPrice());
    }

    @Test
    void build_shouldBuildNebulaPriceCalculationStrategy_whenPassOptionIsSinglePassAndPassCategoryIsNebula() {
        String aPassCategory = PassCategories.NEBULA.toString();
        String aPassOption = PassOptions.SINGLE_PASS.toString();
        List<String> someEventDates = Arrays.asList(EventDate.START_DATE.toString(), EventDate.START_DATE.plusDays(1).toString());
        PassListDto passListDto = new PassListDto(aPassCategory, aPassOption, someEventDates);
        PriceCalculationStrategy priceCalculationStrategy = new NebulaPriceCalculationStrategy();

        PassList passList = subject.build(passListDto);
        Money passPrice = passList.getPasses().get(0).getPrice();
        Money expectedPrice = priceCalculationStrategy.calculatePassPrice(someEventDates.size(), passPrice);

        assertEquals(expectedPrice, passList.getPrice());
    }

    @ParameterizedTest
    @EnumSource(PassCategories.class)
    void build_shouldBuildNoDiscountPriceCalculationStrategy_whenPassOptionIsPackage(PassCategories category) {
        String aPassCategory = category.toString();
        String aPassOption = PassOptions.PACKAGE.toString();
        PassListDto passListDto = new PassListDto(aPassCategory, aPassOption, null);
        PriceCalculationStrategy priceCalculationStrategy = new NoDiscountPriceCalculationStrategy();

        PassList passList = subject.build(passListDto);
        Money passPrice = passList.getPasses().get(0).getPrice();
        Money expectedPrice = priceCalculationStrategy.calculatePassPrice(1, passPrice);

        assertEquals(expectedPrice, passList.getPrice());
    }

    // TODO : Refactor the following tests

    /*
    @Test
    void buildWithDto_shouldThrowInvalidFormatException_whenEventDateIsNotNullAndPassOptionIsPackage() {
        String aPassCategory = PassCategories.SUPERNOVA.toString();
        List<String> someEventDates = new ArrayList<>(Arrays.asList(EventDate.START_DATE.toString(), EventDate.START_DATE.plusDays(1).toString()));
        PassListDto passListDto = new PassListDto(aPassCategory, PassOptions.PACKAGE.toString(), someEventDates);
        passList.setOption(mock(PassOption.class));

        assertThrows(InvalidFormatException.class, () -> subject.build(passListDto));
    }

    @Test
    void buildWithDto_shouldThrowSinglePassWithoutEventDateException_whenEventDateIsNullAndPassOptionIsSinglePass() {
        String aPassCategory = PassCategories.SUPERNOVA.toString();
        PassListDto passListDto = new PassListDto(aPassCategory, PassOptions.SINGLE_PASS.toString(), null);
        passList.setOption(mock(PassOption.class));

        assertThrows(InvalidFormatException.class, () -> subject.build(passListDto));
    }

    @Test
    void buildWithDto_shouldThrowInvalidFormatException_whenPassCategoryDoesNotExist() {
        String anInvalidPassOption = "anInvalidPassCategory";
        PassListDto passListDto = new PassListDto(anInvalidPassOption, PassOptions.PACKAGE.toString(), new ArrayList<>());

        assertThrows(InvalidFormatException.class, () -> subject.build(passListDto));
    }

    @Test
    void buildWithDto_shouldThrowInvalidFormatException_whenPassOptionDoesNotExist() {
        String anInvalidPassOption = "anInvalidPassOption";
        PassListDto passListDto = new PassListDto(PassCategories.SUPERNOVA.toString(), anInvalidPassOption, new ArrayList<>());

        assertThrows(InvalidFormatException.class, () -> subject.build(passListDto));
    }

    @Test
    void buildWithDto_shouldThrowInvalidFormatException_whenEventDateIsInvalid() {
        String anInvalidEventDate = "anInvalidDate";
        PassListDto passListDto = new PassListDto(
                PassCategories.SUPERNOVA.toString(),
                PassOptions.SINGLE_PASS.toString(),
                Collections.singletonList(anInvalidEventDate)
        );

        assertThrows(InvalidFormatException.class, () -> subject.build(passListDto));
    }
    */
}