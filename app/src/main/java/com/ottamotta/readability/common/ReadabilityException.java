package com.ottamotta.readability.common;

import retrofit.RetrofitError;

public class ReadabilityException extends Exception {

    private RetrofitError error;

    public ReadabilityException() {
    }

    public ReadabilityException(String message) {
        super(message);
    }

    public ReadabilityException(RetrofitError error) {
        super(error);
    }

}
