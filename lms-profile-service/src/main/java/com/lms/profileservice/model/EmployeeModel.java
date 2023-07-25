package com.lms.profileservice.model;

import java.util.Date;
import java.util.List;

public record EmployeeModel(int employeeId,
                            String firstName,
                            String middleName,
                            String lastName,
                            String email,
                            long mobileNo,
                            String designation,
                            int managerId,
                            String status,
                            Date startDate,
                            Date endDate,
                            Date lastManagerChangeDate,
                            int locationId,
                            List<AddressModel> address) {
}