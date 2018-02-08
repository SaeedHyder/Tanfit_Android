package com.ingic.tanfit.entities;

public class ResponseWrapper<T> {

    private String Message;
    private String Response;
    private T Result;
    private boolean IsServerError;
    private boolean IsSuccess;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    public boolean isServerError() {
        return IsServerError;
    }

    public void setServerError(boolean serverError) {
        IsServerError = serverError;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }
}
