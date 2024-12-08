package com.perfume.store.exceptions;

public class PerfumeException extends RuntimeException implements ErrorCode{
    String errorCode;
    String errorMessage;

    public PerfumeException() {}

    public PerfumeException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getDetailedErrorMessage() {
        return getErrorMessage();
    }
}
