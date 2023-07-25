package com.lms.profileservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPLOYEE")

public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;
    @NotBlank(message = "First Name is Mandatory")
    @Column(name = "FirstName")
    private String firstName;
    @NotBlank(message = "Middle Name is Mandatory")
    @Column(name = "MiddleName")
    private String middleName;
    @NotBlank(message = "Last Name is Mandatory")
    @Column(name = "lastName")
    private String lastName;
    @NotBlank(message = "Email is is Mandatory")
    @Email(message = "Email is Not Valid")
    @Column(name = "email")
    private String email;
    @NotNull
    @Column(name = "MobileNo")
    private long mobileNo;
    @NotBlank
    @Column(name = "Designation")
    private String designation;
    @NotNull
    @Column(name = "ManagerId")
    private int managerId;
    @NotBlank
    @Column(name = "Status")
    private String status;
    @NotNull
    @Column(name = "StartDate")
    private Date startDate;
    @NotNull
    @Column(name = "EndDate")
    private Date endDate;
    @NotNull
    @Column(name = "LastManagerChangeDate")
    private Date lastManagerChangeDate;

    @Column(name = "LocationId")
    private int locationId;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private List<AddressEntity> address;


}
