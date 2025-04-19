package com.musafir.employyee.specification;

import com.musafir.employyee.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {
    public static Specification<Employee> hasFirstName(String firstName){
        return ((root, query, criteriaBuilder) -> {
            if(firstName == null || firstName.isEmpty())
                return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),"%" + firstName.toLowerCase()+"%");
        });
    }
    public static Specification<Employee> hasLastName(String lastName){
        return(root, query, criteriaBuilder) ->
        {
            if(lastName == null || lastName.isEmpty())
                return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),"%" +lastName.toLowerCase() + "%");
        };
    }
    public static Specification<Employee> hasEmail(String email){
        return(root, query, criteriaBuilder) ->
        {
            if(email == null || email.isEmpty())
                return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),"%" +email.toLowerCase() + "%");
        };
    }
}
