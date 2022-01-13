package com.dk.swaggerintegration;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getEmp")
    public String getEmployee() {
        return "Dinesh";
    }

    @PostMapping("/saveEmp")
    public void saveEmployee() {
        throw new UnsupportedOperationException();

    }

    @PutMapping("/updateEmp")
    public void updateEmployee() {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/deleteEmp")
    public void deleteEmployee() {
        throw new UnsupportedOperationException();
    }

}
