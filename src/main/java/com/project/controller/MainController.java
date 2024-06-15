package com.project.controller;

import com.project.view.View;

public class MainController {
    private View view;

    public MainController(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
