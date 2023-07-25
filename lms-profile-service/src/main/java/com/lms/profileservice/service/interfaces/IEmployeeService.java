package com.lms.profileservice.service.interfaces;

import com.lms.profileservice.entity.EmployeeEntity;
import com.lms.profileservice.model.EmployeeModel;
import com.lms.profileservice.model.ResponseModel;

public interface IEmployeeService {
    public ResponseModel<EmployeeModel> addEmployee(EmployeeModel employeeModel);

    public EmployeeEntity updateEmployee(int employeeId, EmployeeModel employeeModel);
}
