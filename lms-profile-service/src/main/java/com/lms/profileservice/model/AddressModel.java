package com.lms.profileservice.model;

public record AddressModel(int addressId,
                           String addressLine1,
                           String addressLine2,
                           String locality,
                           int pinCode,
                           String city,
                           String state,
                           String country,
                           String region) {
}
