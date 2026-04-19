package com.pragma.userservice.infrastructure.output.externalservice.client;

import com.pragma.userservice.infrastructure.output.externalservice.dto.EmployeeRestaurantAssignmentRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "food-court-service-client",
        url = "${food-court-service.url:http://localhost:8080}"
)
public interface RestaurantFeignClient {

    @PostMapping("/restaurants/employee-assignment")
    void assignEmployeeToOwnerRestaurant(
            @RequestBody EmployeeRestaurantAssignmentRequestDTO request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    );
}

