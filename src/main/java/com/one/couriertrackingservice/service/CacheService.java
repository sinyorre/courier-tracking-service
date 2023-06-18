package com.one.couriertrackingservice.service;

import com.one.couriertrackingservice.model.CourierLocation;
import com.one.couriertrackingservice.model.CourierTotalDistance;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheService {
    private final HashMap<String, List<CourierLocation>> courierLocationMap = new HashMap<>();

    private final HashMap<String, CourierTotalDistance> courierTotalDistanceMap = new HashMap<>();

    public Map<String, List<CourierLocation>> getCourierLocationMap() {
        return courierLocationMap;
    }

    public Map<String, CourierTotalDistance> getCourierTotalDistanceMap() {
        return courierTotalDistanceMap;
    }

    public void putCourierLocationMap(String courier, List<CourierLocation> locations) {
        courierLocationMap.put(courier, locations);
    }

    public void putCourierTotalDistanceMap(String courier, CourierTotalDistance courierTotalDistance) {
        courierTotalDistanceMap.put(courier, courierTotalDistance);
    }
}
