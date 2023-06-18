package com.one.couriertrackingservice.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class CourierLocation {
    @NotNull
    private LocalDateTime time;

    @NotNull
    @NotBlank
    private String courier;

    @NotNull
    private double lat;

    @NotNull
    private double lng;
}
