package com.pragma.userservice.infrastructure.output.externalservice.adapter;

import com.pragma.userservice.domain.constants.DomainConstants;
import com.pragma.userservice.domain.exception.DomainException;
import com.pragma.userservice.domain.spi.IRestaurantAssignmentPort;
import com.pragma.userservice.infrastructure.output.externalservice.client.RestaurantFeignClient;
import com.pragma.userservice.infrastructure.output.externalservice.dto.EmployeeRestaurantAssignmentRequestDTO;
import com.pragma.userservice.infrastructure.output.security.helper.TokenRelayService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantAssignmentAdapter implements IRestaurantAssignmentPort {

    private final RestaurantFeignClient restaurantFeignClient;
    private final TokenRelayService tokenRelayService;

    @Override
    public void assignEmployeeToOwnerRestaurant(Long ownerId, Long employeeId) {
        String authorization = tokenRelayService
                .resolveAuthorizationHeader()
                .orElseThrow(() -> new DomainException(DomainConstants.MSG_AUTHENTICATED_USER_NOT_FOUND, HttpStatus.UNAUTHORIZED));

        try {
            restaurantFeignClient.assignEmployeeToOwnerRestaurant(
                    new EmployeeRestaurantAssignmentRequestDTO(ownerId, employeeId),
                    authorization
            );
        } catch (FeignException.Unauthorized | FeignException.Forbidden ex) {
            log.error("Unauthorized access when assigning employee {} to owner {} restaurant: {}", employeeId, ownerId, ex.getMessage());
            throw new DomainException(DomainConstants.MSG_EMPLOYEE_RESTAURANT_ASSIGNMENT_FAILED, HttpStatus.UNAUTHORIZED);
        } catch (FeignException.BadRequest | FeignException.NotFound | FeignException.Conflict ex) {
            log.error("Failed to assign employee {} to owner {} restaurant: {}", employeeId, ownerId, ex.getMessage());
            throw new DomainException(DomainConstants.MSG_EMPLOYEE_RESTAURANT_ASSIGNMENT_FAILED, HttpStatus.BAD_REQUEST);
        } catch (FeignException ex) {
            log.error("Communication error when assigning employee {} to owner {} restaurant: {}", employeeId, ownerId, ex.getMessage());
            throw new DomainException(DomainConstants.MSG_EMPLOYEE_RESTAURANT_ASSIGNMENT_FAILED, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}

