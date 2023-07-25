package com.lms.profileservice.repo;

import com.lms.profileservice.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<AddressEntity, Integer> {

//    public List<AddressEntity> findByEmployeeId(int employeeId);
}
