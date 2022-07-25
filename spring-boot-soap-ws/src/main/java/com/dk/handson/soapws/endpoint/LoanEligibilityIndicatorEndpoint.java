package com.dk.handson.soapws.endpoint;

import com.dk.handson.soapws.loaneligibility.Acknowledgement;
import com.dk.handson.soapws.loaneligibility.CustomerRequest;
import com.dk.handson.soapws.service.LoanEligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * @author Dinesh
 * @version 1.0
 * @since 07/19/2022
 */
@Endpoint
public class LoanEligibilityIndicatorEndpoint {

    /**
     * NameSpace
     */
    private static final String NAMESPACE = "http://www.dk.com/handson/soapws/loanEligibility";

    @Autowired
    private LoanEligibilityService service;

    /**
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE, localPart = "CustomerRequest")
    @ResponsePayload
    public Acknowledgement getLoanStatus(@RequestPayload CustomerRequest request) {
        return service.checkLoanEligibility(request);
    }

}
