package com.project.services;

import com.project.entity.Result;
import com.project.exception.ApplicationException;

import static com.project.repository.ResultCode.ERROR;

public class UnsupportedFunction implements Function{

    @Override
    public Result execute(String[] parameters) {
        String message = "Unsupported function: " + (parameters.length > 0 ? parameters[0] : "unknown");
        return new Result(ERROR, new ApplicationException(message));
    }
}
