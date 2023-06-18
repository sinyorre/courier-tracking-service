package com.one.couriertrackingservice.service;

import com.one.couriertrackingservice.model.CourierLocation;
import com.one.couriertrackingservice.model.Store;
import com.one.couriertrackingservice.service.abstraction.CourierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierServiceImpl implements CourierService {

    private final GeographicalDistanceService geographicalDistanceService;
    private final StoreService storeService;

    private final CacheService cacheService;

    private final TotalDistanceService totalDistanceService;

    @Override
    public void logAndStoreCourier(CourierLocation courierLocation) {
        Map<String, List<CourierLocation>> courierLocationMap = cacheService.getCourierLocationMap();
        String courier = courierLocation.getCourier();
        List<Store> stores = storeService.getStores();
        stores.forEach(store -> {
            double distance = geographicalDistanceService.findDistanceBetweenTwoPoints(
                    courierLocation.getLat(),
                    courierLocation.getLng(),
                    store.getLat(),
                    store.getLng()
            );
            if (Double.compare(distance, 100) <= 0) {
                if (courierLocationMap.containsKey(courier)) {
                    List<CourierLocation> courierLocations = courierLocationMap.get(courier);
                    CourierLocation lastCourierLocation = courierLocations.get(courierLocations.size() - 1);
                    Duration diffTimes = Duration.between(lastCourierLocation.getTime(), courierLocation.getTime());
                    long diffSeconds = diffTimes.toSeconds();
                    if (diffSeconds >= 60) {
                        courierLocations.add(courierLocation);
                        courierLocationMap.put(courier, courierLocations);
                        log.info("courier location information logged and stored");
                    } else {
                        log.error("Reentries to the same store's circumference over 1 minute does not count as 'entrance'");
                    }
                } else {
                    cacheService.putCourierLocationMap(courier, Stream.of(courierLocation).collect(Collectors.toList()));
                    log.info("courier location information logged and stored");
                }
            }
        });
    }

    @Override
    public double getTotalTravelDistance(String courier) {
        return totalDistanceService.calculate(courier);
    }
}
