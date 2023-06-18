package com.one.couriertrackingservice.service;

import com.one.couriertrackingservice.state.CalculationTypeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TotalDistanceService {

    private final CalculationTypeFactory calculationTypeFactory;

    public double calculate(String courier) {
        return calculationTypeFactory.getCalculationType().getTotalTravelDistance(courier);
    }
}
