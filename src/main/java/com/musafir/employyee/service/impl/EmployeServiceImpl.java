package com.musafir.employyee.service.impl;

import com.musafir.employyee.dto.EmployeDto;
import com.musafir.employyee.entity.Employee;
import com.musafir.employyee.exception.ResourceNotFoundException;
import com.musafir.employyee.repo.EmployeRepo;
import com.musafir.employyee.service.EmployeService;
import com.musafir.employyee.specification.EmployeeSpecification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeServiceImpl implements EmployeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeServiceImpl.class);

    private EmployeRepo employeRepo;
    private ModelMapper modelMapper;

    @Override
    public EmployeDto saveEmploye(EmployeDto employeDto) {
        logger.info("saving employee",employeDto);
        Employee savedEmployee = employeRepo.save(modelMapper.map(employeDto, Employee.class));
        return modelMapper.map(savedEmployee,EmployeDto.class);
    }

    @Override
    public EmployeDto getEmployeeById(Long id) {
        logger.info("fetching employee with id {}",id);
        Employee employee = employeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee doesn't exist with Employee id "+id));
        return modelMapper.map(employee,EmployeDto.class);
    }

    @Override
    public List<EmployeDto> getAllEmployee() {
        return employeRepo.findAll().stream().map(employee -> modelMapper.map(employee,EmployeDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeByID(Long id) {
        logger.info("deleting employee with id {}",id);
        if(!employeRepo.existsById(id))
            throw new ResourceNotFoundException("Employee doesn't exist with Employee id "+id);
    employeRepo.deleteById(id);
    }

    @Override
    public EmployeDto updateEmploye(EmployeDto employeDto, Long id) {
        Employee employee = modelMapper.map(employeDto,Employee.class);
        Employee employe1 = employeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee doesn't exist with Employee id "+id));
        employe1.setEmail(employee.getEmail());
        employe1.setFirstName(employee.getFirstName());
        employe1.setLastName(employee.getLastName());
        employe1.setJobTitle(employee.getJobTitle());
        employe1.setHireDate(employee.getHireDate());
       return modelMapper.map(employeRepo.save(employe1),EmployeDto.class);

    }

    @Override
    public List<EmployeDto> saveMultipleEmployees(List<EmployeDto> employees) {
        List<Employee> saveEmployeeList = employeRepo.saveAll(employees.stream()
                .map(employeDto -> modelMapper.map(employeDto,Employee.class))
                .toList()
        );
        return saveEmployeeList.stream().map(employee -> modelMapper.map(employee,EmployeDto.class)).toList();
    }

    @Override
    public List<EmployeDto> getAllEmployeePerPage(int pageNo, int pageSize) {
        logger.info("fetching employees -Page :{},size:{}",pageNo,pageSize);
        PageRequest pageRequest = PageRequest.of(pageNo+1, pageSize);
        Page<Employee> page = employeRepo.findAll(pageRequest);
        if(page.isEmpty())
            throw new ResourceNotFoundException("No Employees found for the page no :"+pageNo+" with page size: "+pageSize);
       return page.get().map(employee -> modelMapper.map(employee,EmployeDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<EmployeDto> getEmployeBasedUponSearch(String firstName, String lastName, String email) {
        Specification<Employee> spec = Specification.where(null);
        if(firstName != null && !firstName.isEmpty()){
            spec = spec.and(EmployeeSpecification.hasFirstName(firstName));
        } else if (lastName != null && !lastName.isEmpty()) {
            spec = spec.and(EmployeeSpecification.hasLastName(lastName));
        } else if (email != null && !email.isEmpty()) {
            spec = spec.and(EmployeeSpecification.hasEmail(email));
        }
         return employeRepo.findAll(spec).stream().map(employee -> modelMapper.map(employee,EmployeDto.class)).toList();
    }

    @Override
    public String deleteFaultyData() {
        List<Employee> employeeList = employeRepo.findByJobTitle(null);
        if(!employeeList.isEmpty()){
        employeRepo.deleteAll(employeeList);
        return "Deleted successfully";
        }
        else
            return "Some issue in the findBy method";
    }
}
