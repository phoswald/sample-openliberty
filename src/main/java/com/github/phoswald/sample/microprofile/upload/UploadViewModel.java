package com.github.phoswald.sample.microprofile.upload;

public class UploadViewModel {

    public String messageSuccess;
    public String messageError;

    public UploadViewModel withMessageSuccess(String message) {
        this.messageSuccess = message;
        return this;
    }

    public UploadViewModel withMessageError(String message) {
        this.messageError = message;
        return this;
    }
}
