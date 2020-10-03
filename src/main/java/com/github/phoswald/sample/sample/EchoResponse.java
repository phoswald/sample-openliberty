package com.github.phoswald.sample.sample;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EchoResponse")
public class EchoResponse {

    private String ouput;

    public String getOuput() {
        return ouput;
    }

    public void setOuput(String ouput) {
        this.ouput = ouput;
    }
}
