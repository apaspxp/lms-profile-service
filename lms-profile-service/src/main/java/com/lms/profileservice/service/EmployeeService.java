package com.lms.profileservice.service;

import com.lms.profileservice.entity.AddressEntity;
import com.lms.profileservice.entity.EmployeeEntity;
import com.lms.profileservice.model.AddressModel;
import com.lms.profileservice.model.EmployeeModel;
import com.lms.profileservice.model.ResponseModel;
import com.lms.profileservice.repo.AddressRepo;
import com.lms.profileservice.repo.EmployeeRepo;
import com.lms.profileservice.service.interfaces.IEmployeeService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class EmployeeService implements IEmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private AddressRepo addressRepo;

    @Transactional(transactionManager = "profileServiceTransactionManager")
    public ResponseModel<EmployeeModel> addEmployee(EmployeeModel employeeModel) {
        try {
            log.info("Entered into method addEmployee()");
            var employee = employeeModelToEmployeeEntityConverter.apply(employeeModel);

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            var violations = validator.validate(employee);

            if (violations.isEmpty()) {
                if (employeeRepo.existsByEmailOrMobileNo(employeeModel.email(), employeeModel.mobileNo())) {
                    log.info("Email and Phone number already exist");
                    return new ResponseModel<EmployeeModel>("Employee already exists in the database", employeeModel);
                } else {
                    var employeeEntity = employeeRepo.save(employee);
                    var addresses =
                            employee.getAddress()
                                    .stream()
                                    .map(addressEntity -> {
                                        addressEntity.setEmployee(employee);
                                        return addressEntity;
                                    })
                                    .collect(Collectors.toList());
                    if (!addresses.isEmpty())
                        addressRepo.saveAll(addresses);
                    var persistedEmployeeModel = employeeEntityToEmployeeModelConverter.apply(employeeEntity);
                    log.info("Employee added successfully. " + persistedEmployeeModel);
                    return new ResponseModel<EmployeeModel>("Employee added successfully", persistedEmployeeModel);
                }
            }else {
                violations.stream()
                        .forEach(e -> System.out.println("Validation error: " + e));
                return new ResponseModel<EmployeeModel>("Entity validation failed.", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel<EmployeeModel>("Error adding employee. Please check the log for further details.", null);
        }
    }

    @Transactional(transactionManager = "profileServiceTransactionManager")
    public EmployeeEntity updateEmployee(int employeeId, EmployeeModel employeeModel) {
        try {
            log.info("Entered into method updateEmployee()");
            log.info("Employee details for the employee with id {} will be updated.", employeeId);
            var employeeEntity = employeeRepo.findByEmployeeId(employeeId);
            var addressesFromDb = Objects.nonNull(employeeEntity.getAddress()) ? employeeEntity.getAddress() : Collections.emptyList();
            employeeEntity.setFirstName(stringNullCheckFunction.apply(employeeModel.firstName()));
            employeeEntity.setMiddleName(stringNullCheckFunction.apply(employeeModel.middleName()));
            employeeEntity.setLastName(stringNullCheckFunction.apply(employeeModel.lastName()));
            employeeEntity.setEmail(stringNullCheckFunction.apply(employeeModel.email()));
            employeeEntity.setMobileNo(longNullCheckFunction.apply(employeeModel.mobileNo()));
            employeeEntity.setDesignation(stringNullCheckFunction.apply(employeeModel.designation()));
            employeeEntity.setStatus(stringNullCheckFunction.apply(employeeModel.status()));
            employeeEntity.setStartDate(dateNullCheckFunction.apply(employeeModel.startDate()));
            employeeEntity.setEndDate(dateNullCheckFunction.apply(employeeModel.endDate()));
            employeeEntity.setLastManagerChangeDate(dateNullCheckFunction.apply(employeeModel.lastManagerChangeDate()));
            var addressesToBeUpdated = toAddressEntityList.apply(employeeModel);
//            employeeEntity.setAddress(addressesToBeUpdated);
            employeeRepo.save(employeeEntity);
            if (Objects.nonNull(addressesToBeUpdated) && !addressesToBeUpdated.isEmpty()){
                var addressList =
                IntStream.range(0, addressesFromDb.size())
                        .boxed()
                                .map(i -> {
                                    AddressEntity dbAddress = (AddressEntity) addressesFromDb.get(i);
                                    AddressEntity toBeUpdatedAddress = addressesToBeUpdated.get(i);
                                    dbAddress.setAddressLine1(toBeUpdatedAddress.getAddressLine1());
                                    dbAddress.setAddressLine2(toBeUpdatedAddress.getAddressLine2());
                                    dbAddress.setLocality(toBeUpdatedAddress.getLocality());
                                    dbAddress.setPinCode(toBeUpdatedAddress.getPinCode());
                                    dbAddress.setCity(toBeUpdatedAddress.getCity());
                                    dbAddress.setState(toBeUpdatedAddress.getState());
                                    dbAddress.setCountry(toBeUpdatedAddress.getCountry());
                                    dbAddress.setRegion(toBeUpdatedAddress.getRegion());
                                    dbAddress.setEmployee(employeeEntity);
                                    return dbAddress;
                                })
                                        .collect(Collectors.toList());
                addressRepo.saveAll(addressList);
                employeeEntity.setAddress(addressList);
            }
            return employeeEntity;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Function<String, String> stringNullCheckFunction = inputString -> Objects.nonNull(inputString) ? inputString : " ";

    public Function<Long, Long> longNullCheckFunction = inputLong -> Objects.nonNull(inputLong) ? inputLong : 0L;

    public Function<Date, Date> dateNullCheckFunction = inputDate -> Objects.nonNull(inputDate) ? inputDate : new Date();

    public Function<AddressEntity, AddressModel> addressEntityToAddressModelConverter = addressEntity -> new AddressModel(addressEntity.getAddressId(),
            addressEntity.getAddressLine1(),
            addressEntity.getAddressLine2(),
            addressEntity.getLocality(),
            addressEntity.getPinCode(),
            addressEntity.getCity(),
            addressEntity.getState(),
            addressEntity.getCountry(),
            addressEntity.getRegion()
    );

    public Function<AddressModel, AddressEntity> addressModelToAddressEntityConverter = addressModel -> new AddressEntity(addressModel.addressId(),
            addressModel.addressLine1(),
            addressModel.addressLine2(),
            addressModel.locality(),
            addressModel.pinCode(),
            addressModel.city(),
            addressModel.state(),
            addressModel.country(),
            addressModel.region()
    );

    public Function<EmployeeEntity, List<AddressModel>> toAddressModelList = employeeEntity -> employeeEntity.getAddress()
            .stream()
            .map(addressEntityToAddressModelConverter)
            .collect(Collectors.toList());

    public Function<EmployeeModel, List<AddressEntity>> toAddressEntityList = employeeModel -> Objects.nonNull(employeeModel.address()) ?
            employeeModel.address()
                    .stream()
                    .map(addressModelToAddressEntityConverter)
                    .collect(Collectors.toList())
            :
            Collections.emptyList();
    public Function<EmployeeEntity, EmployeeModel> employeeEntityToEmployeeModelConverter = employeeEntity -> new EmployeeModel(employeeEntity.getEmployeeId(),
            employeeEntity.getFirstName(),
            employeeEntity.getMiddleName(),
            employeeEntity.getLastName(),
            employeeEntity.getEmail(),
            employeeEntity.getMobileNo(),
            employeeEntity.getDesignation(),
            employeeEntity.getManagerId(),
            employeeEntity.getStatus(),
            employeeEntity.getStartDate(),
            employeeEntity.getEndDate(),
            employeeEntity.getLastManagerChangeDate(),
            employeeEntity.getLocationId(),
            toAddressModelList.apply(employeeEntity)
    );

    public Function<EmployeeModel, EmployeeEntity> employeeModelToEmployeeEntityConverter = employeeModel -> new EmployeeEntity(employeeModel.employeeId(),
            employeeModel.firstName(),
            employeeModel.middleName(),
            employeeModel.lastName(),
            employeeModel.email(),
            employeeModel.mobileNo(),
            employeeModel.designation(),
            employeeModel.managerId(),
            employeeModel.status(),
            employeeModel.startDate(),
            employeeModel.endDate(),
            employeeModel.lastManagerChangeDate(),
            employeeModel.locationId(),
            toAddressEntityList.apply(employeeModel)
    );
}


