package com.dk.handson.soapws.service;

import java.util.List;

import com.dk.handson.soapws.loaneligibility.Acknowledgement;
import com.dk.handson.soapws.loaneligibility.CustomerRequest;
import org.springframework.stereotype.Service;

/**
 * @author Dinesh
 * @version 1.0
 * @since 07/19/2022
 */
@Service
public class LoanEligibilityService {

    /**
     * @param request
     * @return the eligibility status
     */
    public Acknowledgement checkLoanEligibility(CustomerRequest request) {
        Acknowledgement acknowledgement = new Acknowledgement();
        List<String> mismatchCriteriaList = acknowledgement.getCriteriaMismatch();

        if (!(request.getAge() > 30 && request.getAge() <= 60)) {
            mismatchCriteriaList.add("Person age should in between 30 to 60");
        }
        if (!(request.getYearlyIncome() > 200000)) {
            mismatchCriteriaList.add("minimum income should be more than 200000");
        }
        if (!(request.getCibilScore() > 500)) {
            mismatchCriteriaList.add("Low CIBIL Score please try after 6 month");
        }

        if (mismatchCriteriaList.size() > 0) {
            acknowledgement.setApprovedAmount(0);
            acknowledgement.setIsEligible(false);
        } else {
            acknowledgement.setApprovedAmount(500000);
            acknowledgement.setIsEligible(true);
            mismatchCriteriaList.clear();
        }
        return acknowledgement;

    }

}
