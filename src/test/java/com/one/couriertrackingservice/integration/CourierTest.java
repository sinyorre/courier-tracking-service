package com.one.couriertrackingservice.integration;

import com.one.couriertrackingservice.model.CourierLocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@IT
@ExtendWith(OutputCaptureExtension.class)
class CourierTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldLogAndStoredCourierLocation(CapturedOutput output) {
        // Given
        CourierLocation courierLocation = CourierLocation.builder()
                .courier("1")
                .lat(40.99239856979836)
                .lng(29.124478104294173)
                .time(LocalDateTime.now())
                .build();
        // When
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/tracking/couriers",
                courierLocation,
                String.class
        );
        String body = response.getBody();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(body);
        assertEquals("courier location info logged and stored", body);
        assertTrue(output.getAll().contains("courier location information logged and stored"));
    }

    @Test
    void shouldNotLogAndStoredSecondCourierLocation(CapturedOutput output) {
        // Given
        LocalDateTime now = LocalDateTime.now();
        CourierLocation courierLocation = CourierLocation.builder()
                .courier("1")
                .lat(40.99239856979836)
                .lng(29.124478104294173)
                .time(now)
                .build();

        // When
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/tracking/couriers",
                courierLocation,
                String.class
        );
        String body = response.getBody();

        courierLocation.setTime(courierLocation.getTime().plusSeconds(30));
        ResponseEntity<String> responseTwo = restTemplate.postForEntity(
                "/tracking/couriers",
                courierLocation,
                String.class
        );
        String bodyTwo = responseTwo.getBody();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(body);
        assertNotNull(bodyTwo);
        assertTrue(output.getAll().contains("courier location information logged and stored"));
        assertTrue(output.getAll().contains("Reentries to the same store's circumference over 1 minute does not count as 'entrance"));
    }
}
