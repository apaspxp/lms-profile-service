package com.lms.profileservice.controller;

import com.lms.profileservice.entity.EmployeeEntity;
import com.lms.profileservice.model.EmployeeModel;
import com.lms.profileservice.model.ResponseModel;
import com.lms.profileservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController = new EmployeeController();
    @Mock
    private EmployeeService employeeService;
    @BeforeEach
    void setUp() {
    }

    @Test
    void addEmployee() {
        EmployeeModel employeeModel = new EmployeeModel(
                1, "John", "Doe", "Smith", "johndoe@example.com", 1234567890L,
                "Developer", 2, "Active",
                new Date(), null, null,
                1, null);
        ResponseModel<EmployeeModel> responseModel = new ResponseModel<>();
        Mockito.when(employeeService.addEmployee(employeeModel)).thenReturn(responseModel);
        ResponseEntity<ResponseModel<EmployeeModel>> responseEntity = employeeController.addEmployee(employeeModel);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseModel, responseEntity.getBody());
    }

    @Test
    void updateEmployeeById() {
        EmployeeModel employeeModel = new EmployeeModel(
                1, "John", "Doe", "Smith", "johndoe@example.com", 1234567890L,
                "Developer", 2, "Active",
                new Date(), null, null,
                1, null);


        EmployeeEntity employeeEntity = new EmployeeEntity(
                1, "John", "Doe", "Smith", "johndoe@example.com", 1234567890L,
                "Developer", 2, "Active",
                new Date(), null, null,
                1, null);


        Mockito.when(employeeService.updateEmployee(anyInt(), any(EmployeeModel.class))).thenReturn(employeeEntity);


        ResponseEntity<EmployeeEntity> responseEntity = employeeController.updateEmployeeById(1, employeeModel);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employeeEntity, responseEntity.getBody());
    }
}