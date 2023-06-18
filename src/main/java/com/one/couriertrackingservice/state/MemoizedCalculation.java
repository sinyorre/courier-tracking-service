package com.one.couriertrackingservice.state;

import com.one.couriertrackingservice.exception.CourierNotFoundException;
import com.one.couriertrackingservice.model.CourierLocation;
import com.one.couriertrackingservice.model.CourierTotalDistance;
import com.one.couriertrackingservice.service.CacheService;
import com.one.couriertrackingservice.service.GeographicalDistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component("MemoizedCalculation")
@RequiredArgsConstructor
public class MemoizedCalculation implements DistanceCalculable {

    private final CacheService cacheService;
    private final GeographicalDistanceService geographicalDistanceService;

    @Override
    public double getTotalTravelDistance(String courier) {
        double totalDistance = 0;
        Map<String, List<CourierLocation>> courierLocationMap = cacheService.getCourierLocationMap();
        Map<String, CourierTotalDistance> courierTotalDistanceMap = cacheService.getCourierTotalDistanceMap();
        if (courierLocationMap.containsKey(courier)) {
            int startIndex = 1;
            List<CourierLocation> courierLocations = courierLocationMap.get(courier);
            int size = courierLocations.size();
            if (courierTotalDistanceMap.containsKey(courier)) {
                CourierTotalDistance courierTotalDistance = courierTotalDistanceMap.get(courier);
                int lastIndex = courierTotalDistance.getLastIndex();
                Double lastTotalDistance = courierTotalDistance.getTotalDistance();
                startIndex = lastIndex;
                totalDistance = lastTotalDistance;
                if (startIndex == size - 1) return totalDistance;
                else ++startIndex;
            }
            courierLocations.sort(Comparator.comparing(CourierLocation::getTime));
            for (int i = startIndex; i < size; i++) {
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
            cacheService.putCourierTotalDistanceMap(
                    courier,
                    CourierTotalDistance.builder()
                            .lastIndex(size - 1)
                            .totalDistance(totalDistance)
                            .build()
            );
        } else {
            throw new CourierNotFoundException("Courier ".concat(courier).concat(" not found"));
        }
        return totalDistance;
    }

    @Override
    public CalculationType getCalculationType() {
        return CalculationType.Memoized;
    }
}
