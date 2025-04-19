package com.musafir.employyee.service;

import com.musafir.employyee.dto.EmployeDto;
import com.musafir.employyee.entity.Employee;

import java.util.List;

public interface EmployeService {
    EmployeDto saveEmploye(EmployeDto employeDto);
    EmployeDto getEmployeeById(Long id);
    List<EmployeDto> getAllEmployee();
    void deleteEmployeeByID(Long id);
    EmployeDto updateEmploye(EmployeDto employeDto,Long id);
    List<EmployeDto> saveMultipleEmployees(List<EmployeDto> employees);
    List<EmployeDto> getAllEmployeePerPage(int pageNo, int pageSize);
    List<EmployeDto> getEmployeBasedUponSearch(String firstName,String lastName,String email);
    String deleteFaultyData();
}
