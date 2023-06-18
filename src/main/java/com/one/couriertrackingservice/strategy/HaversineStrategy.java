package com.one.couriertrackingservice.strategy;

import org.springframework.stereotype.Component;

@Component
public class HaversineStrategy implements DistanceStrategy {
    public static final double AVERAGE_RADIUS_OF_EARTH = 6371;

    @Override
    public double findDistanceBetweenTwoPoints(double lat, double lng, double targetLat, double targetLng) {
        return calculateDistance(lat, lng, targetLat, targetLng);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.HaversineStrategy;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return AVERAGE_RADIUS_OF_EARTH * c * 1000;
    }
}
