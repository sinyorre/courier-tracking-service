package com.one.couriertrackingservice.strategy;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Set;

@Component
public class DistanceStrategyFactory {
    EnumMap<StrategyName, DistanceStrategy> strategies = new EnumMap<>(StrategyName.class);

    public DistanceStrategyFactory(Set<DistanceStrategy> strategySet) {
        createStrategy(strategySet);
    }

    public DistanceStrategy getStrategy(StrategyName strategyName) {
        return strategies.get(strategyName);
    }

    private void createStrategy(Set<DistanceStrategy> strategySet) {
        strategySet.forEach(strategy -> strategies.put(strategy.getStrategyName(), strategy));
    }
}
