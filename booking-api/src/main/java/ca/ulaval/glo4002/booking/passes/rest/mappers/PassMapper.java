package ca.ulaval.glo4002.booking.passes.rest.mappers;

import ca.ulaval.glo4002.booking.festival.domain.FestivalConfiguration;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.passes.domain.PassCategories;
import ca.ulaval.glo4002.booking.passes.domain.PassOptions;
import ca.ulaval.glo4002.booking.passes.domain.Pass;
import ca.ulaval.glo4002.booking.passes.domain.pricediscountstrategy.NebulaPriceDiscountStrategy;
import ca.ulaval.glo4002.booking.passes.domain.pricediscountstrategy.NoPriceDiscountStrategy;
import ca.ulaval.glo4002.booking.passes.domain.pricediscountstrategy.PriceDiscountStrategy;
import ca.ulaval.glo4002.booking.passes.domain.pricediscountstrategy.SupergiantPriceDiscountStrategy;
import ca.ulaval.glo4002.booking.passes.rest.PassRequest;
import ca.ulaval.glo4002.booking.passes.rest.PassResponse;
import ca.ulaval.glo4002.booking.profits.domain.Money;
import ca.ulaval.glo4002.booking.program.events.domain.EventDate;
import ca.ulaval.glo4002.booking.program.events.rest.mappers.EventDateMapper;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PassMapper {

    private final FestivalConfiguration festivalConfiguration;
    private final EventDateMapper eventDateMapper;

    @Inject
    public PassMapper(FestivalConfiguration festivalConfiguration, EventDateMapper eventDateMapper) {
        this.festivalConfiguration = festivalConfiguration;
        this.eventDateMapper = eventDateMapper;
    }

    public List<Pass> fromRequest(PassRequest request) {
        PassCategories category = parsePassCategory(request.getPassCategory());
        PassOptions option = parsePassOption(request.getPassOption());

        List<EventDate> eventDates = buildEventDates(request.getEventDates(), option);
        Money price = calculatePrice(request.getEventDates(), category, option);

        return buildPasses(eventDates, category, option, price);
    }

    public List<PassResponse> toResponse(List<Pass> passes) {
        List<PassResponse> passResponses = new ArrayList<>();

        passes.forEach(pass -> {
            String passCategory = pass.getCategory().toString();
            String passOption = pass.getOption().toString();

            switch(pass.getOption()) {
                case PACKAGE:
                    passResponses.add(new PassResponse(
                            pass.getNumber().getValue(),
                            passCategory,
                            passOption
                    ));

                    break;
                default:
                case SINGLE_PASS:
                    passResponses.add(
                        new PassResponse(
                                pass.getNumber().getValue(),
                                passCategory,
                                passOption,
                                pass.getEventDates().get(0).toString()
                        ));

                    break;
            }
        });

        return passResponses;
    }

    private PassCategories parsePassCategory(String category) {
        return PassCategories.get(category);
    }

    private PassOptions parsePassOption(String option) {
        return PassOptions.get(option);
    }

    private Money calculatePrice(List<String> eventDates, PassCategories category, PassOptions option) {
        switch (option) {
            case PACKAGE:
                switch (category) {
                    case SUPERNOVA:
                        return new Money(BigDecimal.valueOf(700000));
                    case SUPERGIANT:
                        return new Money(BigDecimal.valueOf(500000));
                    default:
                    case NEBULA:
                        return new Money(BigDecimal.valueOf(250000));
                }

            default:
            case SINGLE_PASS:
                PriceDiscountStrategy priceDiscountStrategy;
                Money singlePassPrice;

                switch (category) {
                    case SUPERNOVA:
                        priceDiscountStrategy = new NoPriceDiscountStrategy();
                        singlePassPrice = new Money(BigDecimal.valueOf(150000));
                        break;
                    case SUPERGIANT:
                        priceDiscountStrategy = new SupergiantPriceDiscountStrategy();
                        singlePassPrice = new Money(BigDecimal.valueOf(100000));
                        break;
                    default:
                    case NEBULA:
                        priceDiscountStrategy = new NebulaPriceDiscountStrategy();
                        singlePassPrice = new Money(BigDecimal.valueOf(50000));
                        break;
                }

                return priceDiscountStrategy.calculateDiscount(eventDates.size(), singlePassPrice);
        }
    }

    private List<EventDate> buildEventDates(List<String> eventDates, PassOptions option) {
        List<EventDate> builtEventDates = new ArrayList<>();

        switch (option) {
            case PACKAGE:
                if (eventDates != null) {
                    throw new InvalidFormatException();
                }

                builtEventDates = festivalConfiguration.getAllEventDates();

                break;
            default:
            case SINGLE_PASS:
                if (eventDates == null) {
                    throw new InvalidFormatException();
                }

                for (String eventDate : eventDates) {
                    EventDate builtEventDate = eventDateMapper.fromString(eventDate);
                    builtEventDates.add(builtEventDate);
                }

                break;
        }

        return builtEventDates;
    }

    private List<Pass> buildPasses(List<EventDate> eventDates, PassCategories category, PassOptions option, Money price) {
        List<Pass> passes = new ArrayList<>();

        switch (option) {
            case PACKAGE:
                passes.add(new Pass(eventDates, category, option, price));

                break;
            default:
            case SINGLE_PASS:
                eventDates.forEach(eventDate -> {
                    List<EventDate> singleEventDate = Collections.singletonList(eventDate);
                    passes.add(new Pass(singleEventDate, category, option, price));
                });

                break;
        }

        return passes;
    }
}
