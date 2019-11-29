package ca.ulaval.glo4002.booking.shuttles.manifest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ShuttleManifestControllerTest {

    private ShuttleManifestController controller;
    private ShuttleManifestService service;

    @BeforeEach
    void setUpController() {
        service = mock(ShuttleManifestService.class);

        controller = new ShuttleManifestController(service);
    }

    @Test
    void getWithADate_shouldReturnOk() {
        String aDate = "aDate";
        when(service.getTripsForDate(aDate)).thenReturn(mock(ShuttleManifestDto.class));

        ResponseEntity<?> response = controller.get(aDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void getWithoutDate_shouldReturnOk() {
        when(service.getTrips()).thenReturn(mock(ShuttleManifestDto.class));

        ResponseEntity<?> response = controller.get(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}