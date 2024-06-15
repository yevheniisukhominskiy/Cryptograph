package com.project.view;

import com.project.entity.Result;

public interface View {
    String[] getParameters();
    void printResult(Result result);
}
