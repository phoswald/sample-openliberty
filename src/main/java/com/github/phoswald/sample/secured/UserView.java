package com.github.phoswald.sample.secured;

import com.github.phoswald.sample.AbstractView;

public class UserView extends AbstractView<UserViewModel> {

    public UserView() {
        super("user", "model");
    }
}
