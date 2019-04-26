package com.github.phoswald.sample.microprofile.sample;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SampleViewModel {

    public String greeting = "Hello, World!";
    public String now = ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    public Map<String, String> env = System.getenv();
    public Map<Object, Object> props = System.getProperties();
}
