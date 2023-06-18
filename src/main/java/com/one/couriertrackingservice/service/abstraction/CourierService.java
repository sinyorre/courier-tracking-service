package com.one.couriertrackingservice.service.abstraction;

import com.one.couriertrackingservice.model.CourierLocation;

public interface CourierService {
    void logAndStoreCourier(CourierLocation courierLocation);
    double getTotalTravelDistance(String courier);
}
