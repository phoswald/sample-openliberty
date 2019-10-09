package com.github.phoswald.sample.microprofile;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

@EnabledIfSystemProperty(named = "integrationTests", matches = "true")
class JerseyClientTest {

    private final Client client = ClientBuilder.newClient().register(MultiPartFeature.class);
    private final WebTarget target = client.target("http://localhost:9080");

    @Test
    void getPlainText() {
        // act
        Response response = target.path("/rest/sample/time").request().get();
        String responseString = response.readEntity(String.class);

        // assert
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getMediaType(), MediaType.TEXT_PLAIN_TYPE);
        assertThat(responseString, matchesPattern("The current time is .*\\[.+\\]"));
    }

    @Test
    void postMultipartFormData() {
        // arrange
        InputStream stream = new ByteArrayInputStream("Hello, World!".getBytes(StandardCharsets.UTF_8));
        MultiPart multiPart = new MultiPart();
        multiPart.bodyPart(new StreamDataBodyPart("myfile", stream, "attachment.txt", MediaType.TEXT_PLAIN_TYPE));
        Entity<?> requestEntity = Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA_TYPE);

        // act
        Response response = target.path("/rest/pages/upload-form-single").request().post(requestEntity);
        String responseString = response.readEntity(String.class);

        // assert
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getMediaType(), MediaType.TEXT_HTML_TYPE);
        assertThat(responseString, containsString("CT=multipart/form-data"));
        assertThat(responseString, containsString("Content-Type=[text/plain]"));
        assertThat(responseString, containsString("Content-Disposition=[form-data; filename=&quot;attachment.txt&quot;; name=&quot;myfile&quot;]"));
        assertThat(responseString, containsString("SHA256=dffd6021bb2bd5b0af676290809ec3a53191dd81c7f70a4b28688a362182986f"));
    }
}
