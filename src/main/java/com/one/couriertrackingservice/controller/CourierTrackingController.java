package com.one.couriertrackingservice.controller;

import com.one.couriertrackingservice.model.CourierLocation;
import com.one.couriertrackingservice.model.LogAndStoreResponse;
import com.one.couriertrackingservice.service.abstraction.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracking/couriers")
public class CourierTrackingController {
    private final CourierService courierService;

    @PostMapping
    public ResponseEntity<LogAndStoreResponse> logAndStoreCourier(@Valid @RequestBody CourierLocation courierLocation) {
        courierService.logAndStoreCourier(courierLocation);
        LogAndStoreResponse response = LogAndStoreResponse.builder()
                .message("courier location info processed")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{courier}/distance")
    public ResponseEntity<Double> getTotalTravelDistance(@PathVariable String courier) {
        double totalTravelDistance = courierService.getTotalTravelDistance(courier);
        return new ResponseEntity<>(totalTravelDistance, HttpStatus.OK);
    }
}
