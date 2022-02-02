package com.dk.swaggerintegration.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dinesh
 * @version 1.0
 * @since 01/16/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Details about the Employee")
public class Employee {

    @ApiModelProperty(notes = "Id of the employee", example = "001")
    private String id;
    @ApiModelProperty(notes = "Name of the Employee", example = "abc")
    private String name;
    @ApiModelProperty(notes = "Email of the Employee", example = "abc.com")
    private String email;

}
