package com.revolut.banktransferapi.account.view.response;

public class DataResponse {

    private String message;
    private String error;

    private DataResponse() {
    }

    public static DataResponse success(String message) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.message = message;
        return dataResponse;
    }

    public static DataResponse error(String error) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.error = error;
        return dataResponse;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}