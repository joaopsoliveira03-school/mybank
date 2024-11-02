package com.mybank.handlers;

import com.mybank.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(RiskAnalysisServiceCustomerMissingDataException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public String handleProposalNotFoundException(RiskAnalysisServiceCustomerMissingDataException e) {
        return e.getMessage();
    }

    @ExceptionHandler(DuplicatedProposalException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleDuplicatedProposalException(DuplicatedProposalException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProposalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleProposalNotFoundException(ProposalNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RiskAnalysisServiceConnectionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleCustomerConnectionException(RiskAnalysisServiceConnectionException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RiskAnalysisServiceUnexistingCustomerException.class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    @ResponseBody
    public String handleUnexistingCustomerException(RiskAnalysisServiceUnexistingCustomerException e) {
        return e.getMessage();
    }

    @ExceptionHandler(HighEffortRateException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public String handleHighEffortRateException(HighEffortRateException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProposalExpiredException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public String handleProposalExpiredException(ProposalExpiredException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InconsistentDataException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public String handleInconsistentDataException(InconsistentDataException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProposalNotPendingException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public String handleProposalNotPendingException(ProposalNotPendingException e) {
        return e.getMessage();
    }
}

