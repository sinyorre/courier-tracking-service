package com.one.couriertrackingservice.state;

import com.one.couriertrackingservice.exception.CourierNotFoundException;
import com.one.couriertrackingservice.model.CourierLocation;
import com.one.couriertrackingservice.service.CacheService;
import com.one.couriertrackingservice.service.GeographicalDistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component("LinearCalculation")
@RequiredArgsConstructor
public class LinearCalculation implements DistanceCalculable {
    private final CacheService cacheService;
    private final GeographicalDistanceService geographicalDistanceService;

    @Override
    public double getTotalTravelDistance(String courier) {
        double totalDistance = 0;
        Map<String, List<CourierLocation>> courierLocationMap = cacheService.getCourierLocationMap();
        if (courierLocationMap.containsKey(courier)) {
            List<CourierLocation> courierLocations = courierLocationMap.get(courier);
            courierLocations.sort(Comparator.comparing(CourierLocation::getTime));
            int startIndex = 1;
            for (int i = startIndex; i < courierLocations.size(); i++) {
                CourierLocation previousCourierLocation = courierLocations.get(i - 1);
                CourierLocation currentCourierLocation = courierLocations.get(i);
                double distance = geographicalDistanceService.findDistanceBetweenTwoPoints(
                        previousCourierLocation.getLat(),
                        previousCourierLocation.getLng(),
                        currentCourierLocation.getLat(),
                        currentCourierLocation.getLng()
                );
                totalDistance += distance;
            }
        } else {
            throw new CourierNotFoundException("Courier ".concat(courier).concat(" not found"));
        }
        return totalDistance;
    }

    @Override
    public CalculationType getCalculationType() {
        return CalculationType.Linear;
    }
}
