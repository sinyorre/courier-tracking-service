package com.one.couriertrackingservice.service;

import com.one.couriertrackingservice.strategy.DistanceStrategyFactory;
import com.one.couriertrackingservice.strategy.StrategyName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeographicalDistanceService {

    private final DistanceStrategyFactory strategyFactory;

    public double findDistanceBetweenTwoPoints(double lat, double lng, double targetLat, double targetLng) {
        return strategyFactory.getStrategy(StrategyName.HaversineStrategy)
                .findDistanceBetweenTwoPoints(lat, lng, targetLat, targetLng);
    }
}
