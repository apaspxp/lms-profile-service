package com.lms.profileservice.controller;

import com.lms.profileservice.entity.EmployeeEntity;
import com.lms.profileservice.model.EmployeeModel;
import com.lms.profileservice.model.ResponseModel;
import com.lms.profileservice.repo.EmployeeRepo;
import com.lms.profileservice.service.interfaces.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    EmployeeRepo employeeRepo;

//    @Autowired
//    EmailUtility emailUtility;

    @RequestMapping(value="getAllEmployees", method = RequestMethod.GET)
    public List<EmployeeEntity> getAllEmployee(){
        return employeeRepo.findAll();
    }

//    @RequestMapping(value="sendMail", method = RequestMethod.GET)
//    public String sendMail(@RequestParam("body") String body){
//        emailUtility.sendEmail("qsarthak@gmail.com", "qsarthak@gmail.com", "Test", body);
//        return "mail sent";
//    }
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel<EmployeeModel>> addEmployee(@RequestBody EmployeeModel employeeModel){
        return new ResponseEntity<>(employeeService.addEmployee(employeeModel), HttpStatus.OK);
    }
    @RequestMapping(value = "/updateEmployee/{employeeId}", method = RequestMethod.PUT)
    public ResponseEntity<EmployeeEntity> updateEmployeeById(@PathVariable int employeeId, @RequestBody EmployeeModel employeeModel){
        return new ResponseEntity<>(employeeService.updateEmployee(employeeId,employeeModel), HttpStatus.OK);
    }
}





