package com.lms.profileservice.repo;

import com.lms.profileservice.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Integer> {

    public EmployeeEntity findByEmployeeId(@Param("employeeId") Integer employeeId);

    public boolean existsByEmailOrMobileNo(@Param("email") String email, @Param("mobileNo") long mobileNo);
}
