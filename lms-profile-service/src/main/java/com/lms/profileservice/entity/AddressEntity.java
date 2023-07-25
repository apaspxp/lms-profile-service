package com.lms.profileservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ADDRESS")
@ToString
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private int addressId;
    @Column(name="AddressLine1")
    private String addressLine1;
    @Column(name="AddressLine2")
    private String addressLine2;
    @Column(name="Locality")
    private String locality;
    @Column(name="PinCode")
    private int pinCode;
    @Column(name="City")
    private String city;
    @Column(name="State")
    private String state;

    @Column(name="Country")
    private String country;

    @Column(name="Region")
    private String region;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private EmployeeEntity employee;

    public AddressEntity(int addressId, String addressLine1, String addressLine2, String locality, int pinCode, String city, String state, String country, String region) {
        this.addressId = addressId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.locality = locality;
        this.pinCode = pinCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.region = region;
    }
}