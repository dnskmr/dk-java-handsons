package com.dk.swaggerintegration;

import com.dk.swaggerintegration.model.Employee;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dinesh
 * @version 1.0
 * @since 01/13/2022
 *
 * <p>
 * This class is used to showcase the Swagger Integration
 * </p>
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    /**
     * @return the Employee List
     */
    @GetMapping("/getEmp")
    @ApiOperation(value = "Get All Employees", notes = "Get all employees from the database"
            , response = Employee.class)
    public List<Employee> getEmployee() {
        return Arrays.asList(new Employee("001", "Dinesh", "dinesh.com")
                , new Employee("002", "Koki", "koki.com")
                , new Employee("003", "Senthil", "senthil.com")
                , new Employee("004", "Aish", "aish.com"));
    }

    /**
     *
     */
    @PostMapping("/saveEmp")
    public void saveEmployee() {
        throw new UnsupportedOperationException();

    }

    /**
     *
     */
    @PutMapping("/updateEmp")
    public void updateEmployee() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     */
    @DeleteMapping("/deleteEmp")
    public void deleteEmployee() {
        throw new UnsupportedOperationException();
    }

}
