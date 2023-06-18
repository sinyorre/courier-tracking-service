package com.one.couriertrackingservice.state;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

@Component
public class CalculationTypeFactory {

    private final EnumMap<CalculationType, DistanceCalculable> calculationTypesMap = new EnumMap<>(CalculationType.class);

    private CalculationType calculationType = CalculationType.Memoized;

    public CalculationTypeFactory(List<DistanceCalculable> calculationTypes) {
        calculationTypes.forEach(k -> calculationTypesMap.put(k.getCalculationType(), k));
    }

    public DistanceCalculable getCalculationType() {
        return calculationTypesMap.get(calculationType);
    }

    public void changeCalculationType(CalculationType calculationType) {
        this.calculationType = calculationType;
    }
}