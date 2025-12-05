package org.castle.djames.zephyr.customerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.castle.djames.zephyr.customerservice.validator.groups.AddCustomerGroup;
import org.castle.djames.zephyr.customerservice.validator.groups.UpdateCustomerGroup;

public record CustomerRequest(@NotBlank(groups = {AddCustomerGroup.class}) String firstName,
                              @NotBlank(groups = {AddCustomerGroup.class}) String lastName,
                              @NotBlank(groups = {AddCustomerGroup.class}) String nationalId,
                              @NotNull(groups = {AddCustomerGroup.class}) LocalDate birthDate,
                              @NotBlank(groups = {AddCustomerGroup.class}) @Email(groups = {
                                  AddCustomerGroup.class, UpdateCustomerGroup.class}) String email,
                              @NotBlank(groups = {AddCustomerGroup.class}) String phone,
                              @NotBlank(groups = {AddCustomerGroup.class}) String address) {
}
