package ca.ulaval.glo4002.booking.festival.rest;

import ca.ulaval.glo4002.booking.festival.services.FestivalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ConfigurationResourceTest {

    private ConfigurationResource resource;

    @BeforeEach
    void setUpResource() {
        FestivalService festivalService = mock(FestivalService.class);

        resource = new ConfigurationResource(festivalService);
    }

    @Test
    void addProgram_shouldReturnOk() {
        Response response = resource.setConfiguration(mock(EventDatesRequest.class));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}