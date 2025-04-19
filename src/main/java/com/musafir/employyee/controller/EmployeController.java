package com.musafir.employyee.controller;

import com.musafir.employyee.dto.EmployeDto;
import com.musafir.employyee.service.EmployeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("api/employee")
@AllArgsConstructor
public class EmployeController {
    private EmployeService employeService;

    @DeleteMapping("deleteEmpty")
    public ResponseEntity<String> deleteSomeData(){
        return ResponseEntity.ok(employeService.deleteFaultyData());
    }

    @GetMapping("search")
    public ResponseEntity<List<EmployeDto>> searchEmployeeOnCondtion(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email
    ){
        return ResponseEntity.ok(employeService.getEmployeBasedUponSearch(firstName,lastName,email));
    }

    @GetMapping("/getEmployee")
    public ResponseEntity<List<EmployeDto>> getEmployeePage(@RequestParam(name = "page",defaultValue = "0") int pageNo,
                                                      @RequestParam(name = "size",defaultValue = "4") int pageSize){
        return ResponseEntity.ok(employeService.getAllEmployeePerPage(pageNo,pageSize));
    }
    @PostMapping("/add")
    public ResponseEntity<EmployeDto> addEmployee(@RequestBody @Valid EmployeDto employeDto){
        return new ResponseEntity<>(employeService.saveEmploye(employeDto), HttpStatus.CREATED);
    }
    @PostMapping("/saveAll")
    public ResponseEntity<List<EmployeDto>> saveAllEmployees(@RequestBody List<EmployeDto> employeDtoList){
        List<EmployeDto> employeDtos = employeService.saveMultipleEmployees(employeDtoList);
        return ResponseEntity.ok(employeDtos);
    }
    @GetMapping("/all")
    public ResponseEntity<List<EmployeDto>> getAllEmployees(){
        return ResponseEntity.ok(employeService.getAllEmployee());
    }
    @GetMapping("/get")
    public ResponseEntity<EmployeDto> getEmployeeById(@RequestParam("id") Long id){
        ResponseEntity<EmployeDto> ok = ResponseEntity.ok(employeService.getEmployeeById(id));
        return ok;
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id){
        employeService.deleteEmployeeByID(id);
        return ResponseEntity.ok("Employee Deleted Successfully");
    }
    @PutMapping("update/{id}")
    public ResponseEntity<EmployeDto> updateEmployee(@RequestBody @Valid EmployeDto employeDto, @PathVariable Long id){
   return new ResponseEntity<>(employeService.updateEmploye(employeDto,id),HttpStatus.OK);
    }
}
