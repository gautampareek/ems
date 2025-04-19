package com.musafir.employyee.repo;

import com.musafir.employyee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmployeRepo extends JpaRepository<Employee,Long>,
        JpaSpecificationExecutor<Employee> {
    List<Employee> findByJobTitle(String title);
}
