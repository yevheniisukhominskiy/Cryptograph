package com.project;

import com.project.app.Application;
import com.project.controller.MainController;
import com.project.entity.Result;
import com.project.view.ConsoleView;
import com.project.view.View;

public class EntryPoint {
    public static void main(String[] args) {
        View view = new ConsoleView();
        MainController mainController = new MainController(view);
        Application application = new Application(mainController);

        Result result = application.run();
        application.printResult(result);
    }
}