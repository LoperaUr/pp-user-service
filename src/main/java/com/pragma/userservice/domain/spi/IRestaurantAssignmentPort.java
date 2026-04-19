package com.pragma.userservice.domain.spi;

public interface IRestaurantAssignmentPort {
    void assignEmployeeToOwnerRestaurant(Long ownerId, Long employeeId);
}

