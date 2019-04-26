package com.github.phoswald.sample.microprofile.sample;

import com.github.phoswald.sample.microprofile.AbstractView;

public class SampleView extends AbstractView<SampleViewModel> {

    public SampleView() {
        super("sample", "model");
    }
}
