package com.project.app;

import com.project.controller.MainController;
import com.project.entity.Result;
import com.project.repository.FunctionCode;
import com.project.services.Function;

import static com.project.repository.FunctionCode.*;

public class Application {
    private final MainController mainController;

    public Application(MainController mainController) {
        this.mainController = mainController;
    }

    public Result run() {
        String[] parameters = mainController.getView().getParameters();
        String mode = parameters[0];
        Function function = getFunction(mode);
        return function.execute(parameters);
    }

    private Function getFunction(String mode) {
        return switch (mode) {
            case "e" -> FunctionCode.valueOf(String.valueOf(ENCODE)).getFunction();
            case "d" -> FunctionCode.valueOf(String.valueOf(DECODE)).getFunction();
            case "b" -> FunctionCode.valueOf(String.valueOf(BRUTE_FORCE)).getFunction();
            default -> FunctionCode.valueOf(String.valueOf(UNSUPPORTED_FUNCTION)).getFunction();
        };
    }

    public void printResult(Result result) {
        mainController.getView().printResult(result);
    }

}
