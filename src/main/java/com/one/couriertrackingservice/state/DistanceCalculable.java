package com.one.couriertrackingservice.state;

public interface DistanceCalculable {
    double getTotalTravelDistance(String courier);

    CalculationType getCalculationType();
}
