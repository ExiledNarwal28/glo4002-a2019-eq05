package ca.ulaval.glo4002.booking.artists;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.ArrayList;

import ca.ulaval.glo4002.booking.artists.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import ca.ulaval.glo4002.booking.profits.Money;

class ArtistServiceTest {

    // TODO : ArtistService test should mock ArtistClient

    private ArtistService service;
    private ArtistRepository repository;
    private BookingArtist firstPopularAndThirdCostArtist = buildArtist("firstPopularAndThirdCostArtist", 200, 1);
    private BookingArtist secondPopularAndFirstCostArtist = buildArtist("secondPopularAndFirstCostArtist", 500, 2);
    private BookingArtist thirdPopularAndEqualFourthCostArtist = buildArtist("thirdPopularAndEqualFourthCostArtist", 100, 3);
    private BookingArtist fourthPopularAndSecondCostArtist = buildArtist("fourthPopularAndSecondCostArtist", 300, 4);
    private BookingArtist fifthPopularAndEqualFourthCostArtist = buildArtist("fifthPopularAndEqualFourthCostArtist", 100, 5);
    private static WireMockServer wiremockServer;
    private static String response = "[ {\n" + 
			"  \"id\" : 2,\n" + 
			"  \"name\" : \"secondPopularAndFirstCostArtist\",\n" + 
			"  \"nbPeople\" : 4,\n" + 
			"  \"musicStyle\" : \"pop\",\n" + 
			"  \"price\" : 500,\n" + 
			"  \"popularityRank\" : 2,\n" + 
			"  \"availabilities\" : [ ]\n" + 
			"}, {\n" + 
			"  \"id\" : 3,\n" + 
			"  \"name\" : \"firstPopularAndThirdCostArtist\",\n" + 
			"  \"nbPeople\" : 1,\n" + 
			"  \"musicStyle\" : \"pop\",\n" + 
			"  \"price\" : 200,\n" + 
			"  \"popularityRank\" : 1,\n" + 
			"  \"availabilities\" : [ ]\n" + 
			"}, {\n" + 
			"  \"id\" : 4,\n" + 
			"  \"name\" : \"thirdPopularAndEqualFourthCostArtist\",\n" + 
			"  \"nbPeople\" : 4,\n" + 
			"  \"musicStyle\" : \"folk\",\n" + 
			"  \"price\" : 100,\n" + 
			"  \"popularityRank\" : 3,\n" + 
			"  \"availabilities\" : [ ]\n" + 
			"}, {\n" + 
			"  \"id\" : 5,\n" + 
			"  \"name\" : \"fourthPopularAndSecondCostArtist\",\n" + 
			"  \"nbPeople\" : 1,\n" + 
			"  \"musicStyle\" : \"pop\",\n" + 
			"  \"price\" : 300,\n" + 
			"  \"popularityRank\" : 4,\n" + 
			"  \"availabilities\" : [ ]\n" + 
			"}, {\n" + 
			"  \"id\" : 6,\n" + 
			"  \"name\" : \"fifthPopularAndEqualFourthCostArtist\",\n" + 
			"  \"nbPeople\" : 1,\n" + 
			"  \"musicStyle\" : \"pop\",\n" + 
			"  \"price\" : 100,\n" + 
			"  \"popularityRank\" : 5,\n" + 
			"  \"availabilities\" : [ ]\n" + 
			"} ]";
	
	
	@BeforeAll
	public static void setUpServer() {
		wiremockServer = new WireMockServer(8080);
		wiremockServer.start();
	}

    @BeforeEach
    void setUpService() {
		stubFor(get(urlEqualTo("/artists")).
				willReturn(WireMock.aResponse().withHeader("Content-Type", "application/json").
						withBody(response)));
		
    	repository = new InMemoryArtistRepository();
    	
        service = new ArtistService(repository);

    }
    
    @AfterAll
    public static void stopServer() {
    	wiremockServer.stop();
    }

    @Test
    void getAll_shouldReturnAllArtistNames_whenOrderByIsNull() {
        ArtistListDto artistListDto = service.getAllUnordered();

        assertFalse(artistListDto.getArtists().isEmpty());
    }

    @Test
    void getAll_shouldReturnAllArtistNamesUnordered_whenOrderByIsNull() {
        ArtistListDto artistListDto = service.getAllUnordered();
        
        assertEquals(secondPopularAndFirstCostArtist.getName(), artistListDto.getArtists().get(0));
        assertEquals(firstPopularAndThirdCostArtist.getName(), artistListDto.getArtists().get(1));
        assertEquals(thirdPopularAndEqualFourthCostArtist.getName(), artistListDto.getArtists().get(2));
        assertEquals(fourthPopularAndSecondCostArtist.getName(), artistListDto.getArtists().get(3));
        assertEquals(fifthPopularAndEqualFourthCostArtist.getName(), artistListDto.getArtists().get(4));
    }

    @Test
    void getAll_shouldReturnAllArtistNamesOrderedByPopularity_whenOrderByIsByMostPopular() {
        ArtistListDto artistListDto = service.getAllOrdered(ArtistOrderings.MOST_POPULAR.toString());

        assertEquals(firstPopularAndThirdCostArtist.getName(), artistListDto.getArtists().get(0));
        assertEquals(secondPopularAndFirstCostArtist.getName(), artistListDto.getArtists().get(1));
        assertEquals(thirdPopularAndEqualFourthCostArtist.getName(), artistListDto.getArtists().get(2));
        assertEquals(fourthPopularAndSecondCostArtist.getName(), artistListDto.getArtists().get(3));
        assertEquals(fifthPopularAndEqualFourthCostArtist.getName(), artistListDto.getArtists().get(4));
    }

    @Test
    void getAll_shouldReturnAllArtistNamesOrderedByCostAndByPopularity_whenOrderByIsLowCosts() {
        ArtistListDto artistListDto = service.getAllOrdered(ArtistOrderings.LOW_COSTS.toString());

        assertEquals(thirdPopularAndEqualFourthCostArtist.getName(), artistListDto.getArtists().get(0));
        assertEquals(fifthPopularAndEqualFourthCostArtist.getName(), artistListDto.getArtists().get(1));
        assertEquals(firstPopularAndThirdCostArtist.getName(), artistListDto.getArtists().get(2));
        assertEquals(fourthPopularAndSecondCostArtist.getName(), artistListDto.getArtists().get(3));
        assertEquals(secondPopularAndFirstCostArtist.getName(), artistListDto.getArtists().get(4));
    }


    private BookingArtist buildArtist(String name, Integer price, Integer popularityRank) {
        Money cost = new Money(new BigDecimal(price));
        Integer aNumberOfPeople = 1;
        String aMusicStyle = "aMusicStyle";

        return new BookingArtist(1, name, cost, aNumberOfPeople, aMusicStyle, popularityRank, new ArrayList<>());
    }
} 