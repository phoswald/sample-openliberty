package com.github.phoswald.sample.microprofile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EchoRequest")
public class EchoRequest {

    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
