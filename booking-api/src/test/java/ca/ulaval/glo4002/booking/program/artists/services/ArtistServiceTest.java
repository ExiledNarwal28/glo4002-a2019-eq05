package ca.ulaval.glo4002.booking.program.artists.services;

import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.profits.domain.Money;
import ca.ulaval.glo4002.booking.program.artists.domain.Artist;
import ca.ulaval.glo4002.booking.program.artists.domain.ArtistId;
import ca.ulaval.glo4002.booking.program.artists.domain.ArtistOrderings;
import ca.ulaval.glo4002.booking.program.artists.domain.ArtistRepository;
import ca.ulaval.glo4002.booking.program.artists.rest.ArtistListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArtistServiceTest {

    private ArtistService service;
    private ArtistRepository artistRepository;

    private Artist firstPopularAndThirdCostArtist = buildArtist("firstPopularAndThirdCostArtist", 200, 1);
    private Artist secondPopularAndFirstCostArtist = buildArtist("secondPopularAndFirstCostArtist", 500, 2);
    private Artist thirdPopularAndEqualFourthCostArtist = buildArtist("thirdPopularAndEqualFourthCostArtist", 100, 3);
    private Artist fourthPopularAndSecondCostArtist = buildArtist("fourthPopularAndSecondCostArtist", 300, 4);
    private Artist fifthPopularAndEqualFourthCostArtist = buildArtist("fifthPopularAndEqualFourthCostArtist", 100, 5);

    @BeforeEach
    void setUpService() {
        service = new ArtistService(artistRepository);
    }

    @BeforeEach
    void setUpRepository() {
        artistRepository = mock(ArtistRepository.class);

        when(artistRepository.findAll()).thenReturn(Arrays.asList(
                secondPopularAndFirstCostArtist,
                firstPopularAndThirdCostArtist,
                thirdPopularAndEqualFourthCostArtist,
                fourthPopularAndSecondCostArtist,
                fifthPopularAndEqualFourthCostArtist
        ));
    }

    @Test
    void getAllUnordered_shouldReturnAllArtistNames_whenOrderByIsNull() {
        ArtistListResponse artistListResponse = service.getAllUnordered();

        assertFalse(artistListResponse.getArtists().isEmpty());
    }

    @Test
    void getAllUnordered_shouldReturnAllArtistNamesUnordered_whenOrderByIsNull() {
        ArtistListResponse artistListResponse = service.getAllUnordered();
        
        assertEquals(secondPopularAndFirstCostArtist.getName(), artistListResponse.getArtists().get(0));
        assertEquals(firstPopularAndThirdCostArtist.getName(), artistListResponse.getArtists().get(1));
        assertEquals(thirdPopularAndEqualFourthCostArtist.getName(), artistListResponse.getArtists().get(2));
        assertEquals(fourthPopularAndSecondCostArtist.getName(), artistListResponse.getArtists().get(3));
        assertEquals(fifthPopularAndEqualFourthCostArtist.getName(), artistListResponse.getArtists().get(4));
    }

    @Test
    void getAllOrdered_shouldReturnAllArtistNamesOrderedByPopularity_whenOrderByIsByMostPopular() {
        ArtistListResponse artistListResponse = service.getAllOrdered(ArtistOrderings.MOST_POPULAR.toString());

        assertEquals(firstPopularAndThirdCostArtist.getName(), artistListResponse.getArtists().get(0));
        assertEquals(secondPopularAndFirstCostArtist.getName(), artistListResponse.getArtists().get(1));
        assertEquals(thirdPopularAndEqualFourthCostArtist.getName(), artistListResponse.getArtists().get(2));
        assertEquals(fourthPopularAndSecondCostArtist.getName(), artistListResponse.getArtists().get(3));
        assertEquals(fifthPopularAndEqualFourthCostArtist.getName(), artistListResponse.getArtists().get(4));
    }

    @Test
    void getAllOrdered_shouldReturnAllArtistNamesOrderedByCostAndByPopularity_whenOrderByIsLowCosts() {
        ArtistListResponse artistListResponse = service.getAllOrdered(ArtistOrderings.LOW_COSTS.toString());

        assertEquals(secondPopularAndFirstCostArtist.getName(), artistListResponse.getArtists().get(0));
        assertEquals(fourthPopularAndSecondCostArtist.getName(), artistListResponse.getArtists().get(1));
        assertEquals(firstPopularAndThirdCostArtist.getName(), artistListResponse.getArtists().get(2));
        assertEquals(fifthPopularAndEqualFourthCostArtist.getName(), artistListResponse.getArtists().get(3));
        assertEquals(thirdPopularAndEqualFourthCostArtist.getName(), artistListResponse.getArtists().get(4));
    }

    @Test
    void getAllOrdered_shouldThrowInvalidFormatException_whenArtistOrderingIsInvalid() {
        String invalidOrdering = "invalidOrdering";

        assertThrows(InvalidFormatException.class, () -> service.getAllOrdered(invalidOrdering));
    }

    private Artist buildArtist(String name, Integer price, Integer popularityRank) {
        ArtistId id = new ArtistId(1);
        Money cost = new Money(new BigDecimal(price));
        int aNumberOfPeople = 1;

        return new Artist(id, name, cost, aNumberOfPeople, popularityRank);
    }
} 