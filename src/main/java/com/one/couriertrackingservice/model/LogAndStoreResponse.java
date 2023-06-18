package com.one.couriertrackingservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogAndStoreResponse {
    private String message;
}
