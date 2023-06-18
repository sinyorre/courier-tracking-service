package com.one.couriertrackingservice.strategy;

public interface DistanceStrategy {
    double findDistanceBetweenTwoPoints(double lat, double lng, double targetLat, double targetLng);
    StrategyName getStrategyName();
}
